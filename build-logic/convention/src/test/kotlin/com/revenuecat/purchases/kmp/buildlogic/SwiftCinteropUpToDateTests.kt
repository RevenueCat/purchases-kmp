package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageBuilder
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals


/**
 * Tests that verify cinterop reruns when Swift code changes.
 */
@EnabledOnOs(OS.MAC)
class SwiftCinteropUpToDateTests {

    @Test
    fun `cinterop re-runs when Swift file content is modified`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass(relativePath = "TestClass.swift")
        }
        runBuild(pkg.cinteropTaskName)

        // Act
        val original = pkg.target.readSourceFile("TestClass.swift")
        // A comment does not modify the header.
        pkg.target.writeSourceFile("TestClass.swift", "$original\n// comment\n")
        val result = runBuild(pkg.cinteropTaskName)

        // Assert
        assertEquals(TaskOutcome.SUCCESS, result.task(pkg.cinteropTaskName)?.outcome)
    }

    @Test
    fun `cinterop re-runs when implementation code is modified`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeSourceFile(
                "TestClass.swift", """
                import Foundation
                @objc public class TestClass: NSObject {
                    @objc public func greet() -> String { return "hello" }
                }
            """.trimIndent()
            )
        }
        runBuild(pkg.cinteropTaskName)

        // Act
        val original = pkg.target.readSourceFile("TestClass.swift")
        // An implementation change does not modify the header.
        pkg.target.writeSourceFile(
            "TestClass.swift",
            original.replace("return \"hello\"", "return \"hello world\"")
        )
        val result = runBuild(pkg.cinteropTaskName)

        // Assert
        assertEquals(TaskOutcome.SUCCESS, result.task(pkg.cinteropTaskName)?.outcome)
    }

    @Test
    fun `cinterop is UP-TO-DATE when source is unchanged`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass(relativePath = "TestClass.swift")
        }

        // Act
        runBuild(pkg.cinteropTaskName)
        val result = runBuild(pkg.cinteropTaskName)

        // Assert
        assertEquals(TaskOutcome.UP_TO_DATE, result.task(pkg.cinteropTaskName)?.outcome)
    }

    @Test
    fun `cinterop re-runs when new file is added`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass(relativePath = "TestClass.swift")
        }
        runBuild(pkg.cinteropTaskName)

        // Act
        pkg.target.writeSourceFile(
            "NewClass.swift", """
            import Foundation
            @objc public class NewClass: NSObject {}
        """.trimIndent()
        )
        val result = runBuild(pkg.cinteropTaskName)

        // Assert
        assertEquals(TaskOutcome.SUCCESS, result.task(pkg.cinteropTaskName)?.outcome)
    }

    @Test
    fun `cinterop re-runs when file is deleted`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass(relativePath = "TestClass.swift")
            writeSourceFile(
                "SecondClass.swift", """
                import Foundation
                @objc public class SecondClass: NSObject {}
            """.trimIndent()
            )
        }
        runBuild(pkg.cinteropTaskName)

        // Act
        pkg.target.deleteSourceFile("SecondClass.swift")
        val result = runBuild(pkg.cinteropTaskName)

        // Assert
        assertEquals(TaskOutcome.SUCCESS, result.task(pkg.cinteropTaskName)?.outcome)
    }

    private fun SwiftPackageBuilder.writeTestClass(relativePath: String) =
        writeSourceFile(
            relativePath = relativePath,
            contents = """
                import Foundation
                @objc public class TestClass: NSObject {}
            """.trimIndent()
        )

}
