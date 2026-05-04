package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.RevenueCatLibraryPluginTestContext
import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageHandle
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Tests that exercise the cross-project Swift target dependency path: a single shared
 * `Package.swift` declares two targets (one depending on the other), and each target is
 * registered as a `swiftPackage()` in a different Gradle subproject.
 *
 * Without per-target scratch directories and dependency header inclusion in the modulemap,
 * cinterop fails for the consumer target because `@class` forward declarations from the
 * dependency target can't be resolved. These tests verify that:
 *
 * - cinterop runs end-to-end and the dep's `compileSwift` task is wired as a transitive dep,
 * - the dep's `-Swift.h` is copied next to the consumer's header and listed in the consumer's
 *   `module.modulemap`,
 * - each target's scratch directory is scoped to its owning subproject's build dir.
 */
@EnabledOnOs(OS.MAC)
class SwiftCrossProjectDependencyTests {

    @Test
    fun `cinterop succeeds when consumer references a type from a cross-project Swift dependency`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val (dep, consumer) = setUpCrossProjectScenario()

            // Act
            val result = runBuild(consumer.cinteropTaskName)

            // Assert: consumer cinterop succeeds and the dep's compileSwift task ran transitively.
            assertEquals(TaskOutcome.SUCCESS, result.task(consumer.cinteropTaskName)?.outcome)
            assertEquals(TaskOutcome.SUCCESS, result.task(dep.compileSwiftTaskName)?.outcome)
        }

    @Test
    fun `consumer modulemap lists the dependency header before the target header`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val (_, consumer) = setUpCrossProjectScenario()

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
            val (_, consumer) = setUpCrossProjectScenario()

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
    fun `each target uses its own scratch directory under its subproject build dir`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val (dep, consumer) = setUpCrossProjectScenario()

            // Act
            runBuild(consumer.compileSwiftTaskName)

            // Assert: scratch dirs are per-target and scoped to their own subproject.
            val depScratch = dep.getScratchDir(projectDir)
            val consumerScratch = consumer.getScratchDir(projectDir)
            assertTrue(depScratch.exists(), "Expected scratch dir for dep target to exist")
            assertTrue(consumerScratch.exists(), "Expected scratch dir for consumer target to exist")
            assertNotEquals(depScratch, consumerScratch)
            assertTrue(
                depScratch.startsWith(projectDir.resolve("dep/build")),
                "Expected dep scratch dir under dep/build, got: $depScratch",
            )
            assertTrue(
                consumerScratch.startsWith(projectDir.resolve("consumer/build")),
                "Expected consumer scratch dir under consumer/build, got: $consumerScratch",
            )
        }

    @Test
    fun `registering the same target name in two subprojects throws`() =
        revenueCatLibraryPluginTest {
            val pkg = addMultiTargetSwiftPackage {
                target(DEP_TARGET) {
                    writeSourceFile("Foo.swift", "import Foundation\n@objc public class Foo: NSObject {}")
                }
            }

            addSubproject("first").useSwiftPackage(
                kotlinSourceSet = "iosMain",
                packageDir = pkg.packageDir,
                targetName = DEP_TARGET,
            )
            val error = assertFailsWith<IllegalArgumentException> {
                addSubproject("second").useSwiftPackage(
                    kotlinSourceSet = "iosMain",
                    packageDir = pkg.packageDir,
                    targetName = DEP_TARGET,
                )
            }
            assertTrue(
                error.message!!.contains("already registered in subproject 'first'"),
                "Expected message about duplicate registration, got: ${error.message}",
            )
        }

    /**
     * Sets up a Package.swift with two targets where [CONSUMER_TARGET] depends on [DEP_TARGET]
     * and exposes a [DEP_TARGET] type in its public Obj-C API. Each target is registered in its
     * own Gradle subproject. Returns both handles as a [Pair] (dep, consumer).
     *
     * The signature `func makeFoo() -> Foo` forces `${CONSUMER_TARGET}-Swift.h` to contain a
     * `@class` forward declaration for the dep type, which is the failure mode these tests guard.
     */
    private fun RevenueCatLibraryPluginTestContext.setUpCrossProjectScenario(): Pair<SwiftPackageHandle, SwiftPackageHandle> {
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

        val dep = addSubproject("dep").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = DEP_TARGET,
            kotlinPackageName = "test.swift.dep",
        )
        val consumer = addSubproject("consumer").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = CONSUMER_TARGET,
            kotlinPackageName = "test.swift.consumer",
        )
        return dep to consumer
    }

    private companion object {
        const val DEP_TARGET = "DepTarget"
        const val CONSUMER_TARGET = "ConsumerTarget"
    }
}
