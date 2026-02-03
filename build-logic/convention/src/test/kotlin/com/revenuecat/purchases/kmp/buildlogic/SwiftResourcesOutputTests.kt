package com.revenuecat.purchases.kmp.buildlogic

import com.revenuecat.purchases.kmp.buildlogic.test.SwiftPackageBuilder
import com.revenuecat.purchases.kmp.buildlogic.test.revenueCatLibraryPluginTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * Tests that verify the actual output of ProcessSwiftResourcesTask.
 */
@EnabledOnOs(OS.MAC)
class SwiftResourcesOutputTests {

    @Test
    fun `copied resources preserve original content`() = revenueCatLibraryPluginTest {
        // Arrange
        // language=json
        val originalContent = """{"key": "value", "nested": {"foo": "bar"}}"""
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass("TestClass.swift")
            writeResourceFile("config.json", originalContent)
        }

        // Act
        runBuild(pkg.processResourcesTaskName)

        // Assert
        val outputDir = pkg.getResourceOutputDir(projectDir)
        val outputFile = outputDir.resolve("config.json")
        assertTrue(outputFile.exists())
        assertEquals(originalContent, outputFile.readText())
    }

    @Test
    fun `localized strings are placed in lproj directories`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass("TestClass.swift")
            writeLocalizedStrings("en", """"OK" = "OK";""")
            writeLocalizedStrings("de", """"OK" = "OK";""")
            writeLocalizedStrings("fr", """"OK" = "D'accord";""")
        }

        // Act
        runBuild(pkg.processResourcesTaskName)

        // Assert
        val outputDir = pkg.getResourceOutputDir(projectDir)
        assertTrue(outputDir.resolve("en.lproj/Localizable.strings").exists())
        assertTrue(outputDir.resolve("de.lproj/Localizable.strings").exists())
        assertTrue(outputDir.resolve("fr.lproj/Localizable.strings").exists())
    }

    @Test
    fun `xcassets are compiled`() = revenueCatLibraryPluginTest {
        // Arrange
        val pkg = addSwiftPackage(kotlinSourceSet = "iosMain") {
            writeTestClass("TestClass.swift")
            writeAssetCatalog("icons.xcassets") {
                addImageSet(
                    name = "tick",
                    svgContent = """
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/>
                        </svg>
                    """.trimIndent()
                )
            }
        }

        // Act
        runBuild(pkg.processResourcesTaskName)

        // Assert
        val outputFiles = pkg.getResourceOutputDir(projectDir).listFiles() ?: emptyArray()
        val hasCompiledAssets = outputFiles.any { it.name.endsWith(".car") }
        assertTrue(hasCompiledAssets)
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
