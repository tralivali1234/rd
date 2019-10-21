package com.jetbrains.rd.gradle.tasks

import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.create
import org.gradle.process.BaseExecSpec

@Suppress("UsePropertyAccessSyntax", "LeakingThis")
open class DotnetRunTask : Exec(), MarkedExecTask {
    override val commandLineWithArgs: String
        get() = ((this as BaseExecSpec).getCommandLine() + tmpFile.absolutePath).joinToString(separator = " ")

    companion object {
        private const val netCoreAppVersion = 2.0
    }

    init {
        dependsOn(project.tasks.create<DotnetBuildTask>("${name}Build"))
        executable = "dotnet"
        workingDir = workingDir.resolve("CrossTest")

        setArgs(listOf("run", "--framework=netcoreapp$netCoreAppVersion", name))
    }
}