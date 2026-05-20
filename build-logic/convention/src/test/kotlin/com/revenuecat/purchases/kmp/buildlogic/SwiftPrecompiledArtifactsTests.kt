package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.RevenueCatLibraryPluginTestContext
import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageHandle
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@EnabledOnOs(OS.MAC)
class SwiftPrecompiledArtifactsTests {

    @Test
    fun `compileSwiftIosArtifacts dry-run includes compileSwift tasks from all subprojects`() =
        revenueCatLibraryPluginTest {
            val (dep, consumer) = setUpTwoSubprojectScenario()

            val result = runGradle("--dry-run", "compileSwiftIosArtifacts")

            assertTrue(
                result.output.contains(dep.compileSwiftTaskName),
                "Expected dry-run output to include ${dep.compileSwiftTaskName}. Output:\n${result.output}",
            )
            assertTrue(
                result.output.contains(consumer.compileSwiftTaskName),
                "Expected dry-run output to include ${consumer.compileSwiftTaskName}. Output:\n${result.output}",
            )
        }

    @Test
    fun `compileSwiftIosArtifacts is registered on consecutive Gradle invocations`() =
        revenueCatLibraryPluginTest {
            setUpTwoSubprojectScenario()

            runGradle("--dry-run", "compileSwiftIosArtifacts")
            val result = runGradle("--dry-run", "compileSwiftIosArtifacts")

            assertTrue(
                result.output.contains("compileSwiftIosArtifacts"),
                "Expected aggregate task on second invocation. Output:\n${result.output}",
            )
        }

    @Test
    fun `skipSwiftBuild fails when precompiled artifacts are missing`() =
        revenueCatLibraryPluginTest {
            val dep = setUpSingleSubprojectScenario()

            val result = runGradleAndFail(
                "-PskipSwiftBuild=true",
                dep.compileSwiftTaskName,
            )

            assertTrue(
                result.output.contains("Missing precompiled Swift library at"),
                "Expected missing-artifact failure. Output:\n${result.output}",
            )
        }

    @Test
    fun `skipSwiftBuild succeeds when precompiled artifacts exist`() =
        revenueCatLibraryPluginTest {
            val dep = setUpSingleSubprojectScenario()
            val outputDir = dep.getSwiftOutputDir(projectDir)
            outputDir.mkdirs()
            outputDir.resolve("lib${TARGET}.a").writeText("")
            outputDir.resolve("$TARGET-Swift.h").writeText("")
            outputDir.resolve("module.modulemap").writeText("module $TARGET { export * }")

            val result = runGradle("-PskipSwiftBuild=true", dep.compileSwiftTaskName)

            assertEquals(
                org.gradle.testkit.runner.TaskOutcome.SUCCESS,
                result.task(dep.compileSwiftTaskName)?.outcome,
            )
        }

    private fun RevenueCatLibraryPluginTestContext.setUpTwoSubprojectScenario(): Pair<SwiftPackageHandle, SwiftPackageHandle> {
        val pkg = addMultiTargetSwiftPackage {
            target(DEP_TARGET) {
                writeSourceFile(
                    "Foo.swift",
                    """
                    import Foundation
                    
                    @objc public class Foo: NSObject {}
                    """.trimIndent(),
                )
            }
            target(CONSUMER_TARGET, dependencies = listOf(DEP_TARGET)) {
                writeSourceFile(
                    "Bar.swift",
                    """
                    import Foundation
                    import $DEP_TARGET
                    
                    @objc public class Bar: NSObject {}
                    """.trimIndent(),
                )
            }
        }

        val dep = addSubproject("dep").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = DEP_TARGET,
        )
        val consumer = addSubproject("consumer").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = CONSUMER_TARGET,
        )
        return dep to consumer
    }

    private fun RevenueCatLibraryPluginTestContext.setUpSingleSubprojectScenario(): SwiftPackageHandle {
        val pkg = addMultiTargetSwiftPackage {
            target(TARGET) {
                writeSourceFile(
                    "Foo.swift",
                    """
                    import Foundation
                    
                    @objc public class Foo: NSObject {}
                    """.trimIndent(),
                )
            }
        }
        return addSubproject("module").useSwiftPackage(
            kotlinSourceSet = "iosMain",
            packageDir = pkg.packageDir,
            targetName = TARGET,
        )
    }

    private companion object {
        const val DEP_TARGET = "DepTarget"
        const val CONSUMER_TARGET = "ConsumerTarget"
        const val TARGET = "TestTarget"
    }
}
