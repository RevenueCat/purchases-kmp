package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageBuilder
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Tests that verify Swift packages declared from a test source set are only bound into the test
 * compilation, so they never end up in the published klib.
 */
@EnabledOnOs(OS.MAC)
class SwiftTestSourceSetTests {

    @Test
    fun `cinterop is created for the test compilation`() = revenueCatLibraryPluginTest {
        // Arrange
        addSwiftPackage(kotlinSourceSet = "iosTest") {
            writeTestClass(relativePath = "TestClass.swift")
        }

        // Act
        val result = runBuild(TEST_CINTEROP_TASK_NAME)

        // Assert
        assertEquals(TaskOutcome.SUCCESS, result.task(TEST_CINTEROP_TASK_NAME)?.outcome)
    }

    @Test
    fun `main compilation does not depend on the Swift package`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosTest") {
            writeTestClass(relativePath = "TestClass.swift")
        }

        // Act
        val result = runBuild(":compileKotlinIosSimulatorArm64")

        // Assert
        val swiftTaskPaths = listOf(pkg.compileSwiftTaskName, TEST_CINTEROP_TASK_NAME)
        assertFalse(
            result.tasks.any { it.path in swiftTaskPaths },
            "Expected no Swift tasks in the main compilation's task graph. Actual: ${result.tasks.map { it.path }}",
        )
    }

    private fun SwiftPackageBuilder.writeTestClass(relativePath: String) =
        writeSourceFile(
            relativePath = relativePath,
            contents = """
                import Foundation
                @objc public class TestClass: NSObject {}
            """.trimIndent()
        )

    private companion object {
        const val TEST_CINTEROP_TASK_NAME = ":cinteropTestTestModuleIosSimulatorArm64"
    }
}
