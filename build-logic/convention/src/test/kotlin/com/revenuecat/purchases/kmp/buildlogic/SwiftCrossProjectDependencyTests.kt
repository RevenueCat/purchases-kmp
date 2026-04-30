package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.RevenueCatLibraryPluginTestContext
import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageHandle
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


/**
 * Tests that exercise the cross-project Swift target dependency path: a single shared
 * `Package.swift` declares two targets (one depending on the other), and each target is
 * registered as a `swiftPackage()` in a different Gradle subproject.
 *
 * Without this PR's fix, cinterop fails for the consumer target because `@class` forward
 * declarations from the dependency target can't be resolved. These tests verify that:
 *
 * - cinterop runs end-to-end and the dep's `compileSwift` task is wired as a transitive dep,
 * - the dep's `-Swift.h` is copied next to the consumer's header and listed in the consumer's
 *   `module.modulemap`,
 * - each target uses its own scratch directory (no shared scratch at the root level).
 */
@EnabledOnOs(OS.MAC)
class SwiftCrossProjectDependencyTests {

    @Test
    fun `cinterop succeeds when consumer references a type from a cross-project Swift dependency`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val consumer = setUpCrossProjectScenario()

            // Act
            val result = runBuild(consumer.cinteropTaskName)

            // Assert: consumer cinterop succeeds and the dep's compileSwift task ran transitively.
            assertEquals(TaskOutcome.SUCCESS, result.task(consumer.cinteropTaskName)?.outcome)
            assertEquals(
                TaskOutcome.SUCCESS,
                result.task(":dep:compileSwift${DEP_TARGET}IosSimulatorArm64")?.outcome,
            )
        }

    @Test
    fun `consumer modulemap lists the dependency header before the target header`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val consumer = setUpCrossProjectScenario()

            // Act
            runBuild(consumer.compileSwiftTaskName)

            // Assert
            val modulemap = consumer.getSwiftOutputDir(projectDir).resolve("module.modulemap")
            assertTrue(modulemap.exists(), "Expected module.modulemap at ${modulemap.absolutePath}")
            val contents = modulemap.readText()
            assertTrue(
                contents.contains("""header "${DEP_TARGET}-Swift.h""""),
                "Expected modulemap to include dep header. Actual:\n$contents",
            )
            assertTrue(
                contents.contains("""header "${CONSUMER_TARGET}-Swift.h""""),
                "Expected modulemap to include consumer header. Actual:\n$contents",
            )
            // Dep header must appear before the consumer header so the @class forward
            // declarations in the consumer header are resolved against the full @interface
            // definitions in the dep header.
            val depIndex = contents.indexOf("""header "${DEP_TARGET}-Swift.h"""")
            val consumerIndex = contents.indexOf("""header "${CONSUMER_TARGET}-Swift.h"""")
            assertTrue(
                depIndex in 0 until consumerIndex,
                "Expected dep header to come before consumer header in modulemap. Actual:\n$contents",
            )
        }

    @Test
    fun `dependency header is copied into consumer outputDir`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val consumer = setUpCrossProjectScenario()

            // Act
            runBuild(consumer.compileSwiftTaskName)

            // Assert
            val depHeaderCopy = consumer.getSwiftOutputDir(projectDir).resolve("${DEP_TARGET}-Swift.h")
            assertTrue(
                depHeaderCopy.exists(),
                "Expected dep header copy at ${depHeaderCopy.absolutePath}",
            )
        }

    @Test
    fun `each target uses its own scratch directory`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val consumer = setUpCrossProjectScenario()

            // Act
            runBuild(consumer.compileSwiftTaskName)

            // Assert: per-target scratch dirs exist, and there's no shared scratch dir at the
            // root project level (which used to be the case before the Xcode 26 fix).
            assertTrue(
                projectDir.resolve("dep/build/swift-packages/${DEP_TARGET}/.build").exists(),
                "Expected per-target scratch dir for dep target",
            )
            assertTrue(
                consumer.getScratchDir(projectDir).exists(),
                "Expected per-target scratch dir for consumer target",
            )
            assertFalse(
                projectDir.resolve("build/swift-packages/.build").exists(),
                "Expected no shared scratch dir at the root project level",
            )
        }

    /**
     * Sets up a Package.swift with two targets where [CONSUMER_TARGET] depends on [DEP_TARGET]
     * and exposes a [DEP_TARGET] type in its public Obj-C API. Each target is registered in its
     * own Gradle subproject. Returns the consumer's [SwiftPackageHandle].
     *
     * The signature `func makeFoo() -> Foo` forces `${CONSUMER_TARGET}-Swift.h` to contain a
     * `@class` forward declaration for the dep type, which is the failure mode this PR fixes.
     */
    private fun RevenueCatLibraryPluginTestContext.setUpCrossProjectScenario(): SwiftPackageHandle {
        val pkg = addMultiTargetSwiftPackage {
            target(DEP_TARGET) {
                writeSourceFile(
                    "Foo.swift",
                    """
                    import Foundation
                    
                    @objc public class Foo: NSObject {
                        @objc public override init() { super.init() }
                        @objc public func describe() -> String { return "Foo" }
                    }
                    """.trimIndent()
                )
            }
            target(CONSUMER_TARGET, dependencies = listOf(DEP_TARGET)) {
                writeSourceFile(
                    "Bar.swift",
                    """
                    import Foundation
                    import $DEP_TARGET
                    
                    @objc public class Bar: NSObject {
                        @objc public func makeFoo() -> Foo { return Foo() }
                    }
                    """.trimIndent()
                )
            }
        }

        addSubproject("dep").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = DEP_TARGET,
            kotlinPackageName = "test.swift.dep",
        )
        // The cross-project wiring runs in :consumer's `afterEvaluate` and looks up the dep
        // target in the global registry. That registration only happens during :dep's
        // evaluation, so we must force :dep to be evaluated first.
        return addSubproject("consumer").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = CONSUMER_TARGET,
            kotlinPackageName = "test.swift.consumer",
            dependsOnSubprojects = listOf("dep"),
        )
    }

    private companion object {
        const val DEP_TARGET = "DepTarget"
        const val CONSUMER_TARGET = "ConsumerTarget"
    }
}
