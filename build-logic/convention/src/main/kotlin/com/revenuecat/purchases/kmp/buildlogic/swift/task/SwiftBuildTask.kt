package com.revenuecat.purchases.kmp.buildlogic.swift.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/**
 * Gradle task that compiles a Swift package target and produces:
 * - A static library (.a file)
 * - An Objective-C header (-Swift.h)
 * - A module.modulemap for cinterop
 */
abstract class SwiftBuildTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    /** The Swift target to build (e.g. "RevenueCat") */
    @get:Input
    abstract val swiftTarget: Property<String>

    /** The Swift SDK to use (e.g. "iphonesimulator", "iphoneos") */
    @get:Input
    abstract val sdk: Property<String>

    /** The Swift triple to use (e.g. "arm64-apple-ios-simulator") */
    @get:Input
    abstract val triple: Property<String>

    /** The name of the generated header file (e.g. "RevenueCat-Swift.h") */
    @get:Input
    abstract val headerName: Property<String>

    /** The name of the static library (e.g. "libRevenueCat.a") */
    @get:Input
    abstract val libraryName: Property<String>

    /** The module name for the modulemap (e.g. "RevenueCat") */
    @get:Input
    abstract val moduleName: Property<String>

    /** The Swift build configuration ("debug" or "release") */
    @get:Input
    abstract val configuration: Property<String>

    /** The Swift package directory */
    @get:Internal
    abstract val packageDir: DirectoryProperty

    /** The Swift target's source directory (for tracking incremental builds) */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val targetSourceDir: DirectoryProperty

    /** The output directory for built artifacts */
    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    /** The scratch directory for Swift */
    @get:Internal
    abstract val scratchDir: DirectoryProperty

    @TaskAction
    fun build() {
        val targetOutputDir = outputDir.get().asFile
        targetOutputDir.mkdirs()

        val scratchPath = scratchDir.get().asFile
        val sdkPath = getSdkPath(sdk.get())
        val tripleValue = triple.get()
        val targetName = swiftTarget.get()
        val configValue = configuration.get()

        execOperations.exec {
            workingDir = packageDir.get().asFile
            // Avoids trying to use the iOS SDK to parse the Package.swift when building from Xcode.
            // Swift compilation still uses the correct SDK because of the -Xswiftc -sdk arguments.
            // This environment change is only scoped to this subprocess.
            environment("SDKROOT", "")
            commandLine(
                "xcrun", "swift", "build",
                "--target", targetName,
                "--configuration", configValue,
                "--triple", tripleValue,
                "--scratch-path", scratchPath.absolutePath,
                "-Xswiftc", "-sdk",
                "-Xswiftc", sdkPath,
                "-Xcc", "-isysroot",
                "-Xcc", sdkPath,
                "-Xswiftc", "-emit-objc-header",
                "-Xswiftc", "-emit-objc-header-path",
                "-Xswiftc", targetOutputDir.resolve(headerName.get()).absolutePath
            )
        }

        // Collect all .o files and create a static library with libtool
        val buildPath = scratchPath.resolve("$tripleValue/$configValue")
        val objectFiles = buildPath.resolve("$targetName.build")
            .walkTopDown()
            .filter { it.extension == "o" }
            .toList()

        if (objectFiles.isEmpty()) {
            error("No object files found in ${buildPath.resolve("$targetName.build")}")
        }

        execOperations.exec {
            commandLine(
                listOf(
                    "libtool", "-static", "-o",
                    targetOutputDir.resolve(libraryName.get()).absolutePath
                ) + objectFiles.map { it.absolutePath }
            )
        }

        // Create module.modulemap to wrap the Swift header as a Clang module
        val modulemapContent = """
            module ${moduleName.get()} {
                header "${headerName.get()}"
                export *
            }
        """.trimIndent()
        targetOutputDir.resolve("module.modulemap").writeText(modulemapContent)
    }

    private fun getSdkPath(sdk: String): String {
        val output = ByteArrayOutputStream()
        execOperations.exec {
            commandLine("xcrun", "--sdk", sdk, "--show-sdk-path")
            standardOutput = output
        }
        return output.toString().trim()
    }
}
