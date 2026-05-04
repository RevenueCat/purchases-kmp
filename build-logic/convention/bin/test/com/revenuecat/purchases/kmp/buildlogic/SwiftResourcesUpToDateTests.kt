package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageBuilder
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals


/**
 * Tests that verify ProcessSwiftResourcesTask reruns when resource files change.
 */
@EnabledOnOs(OS.MAC)
class SwiftResourcesUpToDateTests {

    @Test
    fun `processSwiftResources re-runs when resource file is added`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
                writeTestClass("TestClass.swift")
                writeTestResource("data.json")
            }
            runBuild(pkg.processResourcesTaskName)

            // Act
            pkg.target.resources.writeResourceFile("config.txt", "some config")
            val result = runBuild(pkg.processResourcesTaskName)

            // Assert
            assertEquals(TaskOutcome.SUCCESS, result.task(pkg.processResourcesTaskName)?.outcome)
        }

    @Test
    fun `processSwiftResources re-runs when resource file is removed`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
                writeTestClass("TestClass.swift")
                writeTestResource("data.json")
                writeResourceFile("config.txt", "some config")
            }
            runBuild(pkg.processResourcesTaskName)

            // Act
            pkg.target.resources.deleteResourceFile("config.txt")
            val result = runBuild(pkg.processResourcesTaskName)

            // Assert
            assertEquals(TaskOutcome.SUCCESS, result.task(pkg.processResourcesTaskName)?.outcome)
        }

    @Test
    fun `processSwiftResources re-runs when resource file is renamed`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
                writeTestClass("TestClass.swift")
                writeTestResource("old-name.json")
            }
            runBuild(pkg.processResourcesTaskName)

            // Act
            pkg.target.resources.renameResourceFile("old-name.json", "new-name.json")
            val result = runBuild(pkg.processResourcesTaskName)

            // Assert
            assertEquals(TaskOutcome.SUCCESS, result.task(pkg.processResourcesTaskName)?.outcome)
        }

    @Test
    fun `processSwiftResources re-runs when resource file content is modified`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
                writeTestClass("TestClass.swift")
                writeTestResource("data.json")
            }
            runBuild(pkg.processResourcesTaskName)

            // Act
            pkg.target.resources.writeResourceFile("data.json", """{"key": "modified value"}""")
            val result = runBuild(pkg.processResourcesTaskName)

            // Assert
            assertEquals(TaskOutcome.SUCCESS, result.task(pkg.processResourcesTaskName)?.outcome)
        }

    @Test
    fun `processSwiftResources is UP-TO-DATE when resources are unchanged`() =
        revenueCatLibraryPluginTest {
            // Arrange
            val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
                writeTestClass("TestClass.swift")
                writeTestResource("data.json")
            }

            // Act
            runBuild(pkg.processResourcesTaskName)
            val result = runBuild(pkg.processResourcesTaskName)

            // Assert
            assertEquals(TaskOutcome.UP_TO_DATE, result.task(pkg.processResourcesTaskName)?.outcome)
        }

    private fun SwiftPackageBuilder.writeTestClass(relativePath: String) =
        writeSourceFile(
            relativePath = relativePath,
            contents = """
                import Foundation
                @objc public class TestClass: NSObject {}
            """.trimIndent()
        )
    
    private fun SwiftPackageBuilder.writeTestResource(relativePath: String) =
        writeResourceFile(
            relativePath = relativePath,
            contents = """{"key": "value"}"""
        )
}
