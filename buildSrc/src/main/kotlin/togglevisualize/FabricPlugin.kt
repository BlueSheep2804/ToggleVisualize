package togglevisualize

import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import net.fabricmc.loom.util.Constants
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.gradle.kotlin.dsl.the

open class FabricPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val current = the<StonecutterBuildExtension>().current.parsed
        if (current > "26.0") setupNewLoomFacade() else setupOldLoomFacade()
        extensions.create<FabricExtensions>("fabric", this, current > "26.0")
    }

    private fun Project.setupNewLoomFacade() {
        plugins.apply("net.fabricmc.fabric-loom")

        val names = listOf(
            JavaPlugin.API_CONFIGURATION_NAME,
            JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
            JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
            JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
            Constants.Configurations.LOCAL_RUNTIME
        )

        for (it in names) {
            val modConfiguration: Provider<Configuration> = configurations.register("mod" + it.uppercaseFirstChar())
            configurations.named(it) { extendsFrom(modConfiguration.get()) }
        }

        configurations.register("mappings") {
            isCanBeResolved = false
            isCanBeConsumed = false
        }
    }

    private fun Project.setupOldLoomFacade() {
        plugins.apply("net.fabricmc.fabric-loom-remap")
    }


    open class FabricExtensions(val project: Project, val isNew: Boolean) {
        val modJar: TaskProvider<Jar> by lazy {
            val candidate = if (isNew) project.tasks.named("jar")
            else project.tasks.named("remapJar")
            candidate as TaskProvider<Jar>
        }

        val modSourcesJar: TaskProvider<Jar> by lazy {
            val candidate = if (isNew) project.tasks.named("sourcesJar")
            else project.tasks.named("remapSourcesJar")
            candidate as TaskProvider<Jar>
        }
    }
}