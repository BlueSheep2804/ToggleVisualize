import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.modpublishplugin)
    alias(libs.plugins.moddevgradle.legacy)
}

val mcVersion = stonecutter.current.version
val projectJavaVersion = when {
    sc.current.parsed >= "26.1" -> 25
    sc.current.parsed >= "1.20.5" -> 21
    else -> 17
}
val loader = "forge"

stonecutter {
    replacements.string(current.parsed >= "1.21.11") {
        replace("ResourceLocation", "Identifier")
        replace("GuiGraphics", "GuiGraphicsExtractor")
    }
}

val modVersion = project.property("modVersion")
version = "$modVersion+$mcVersion-$loader"
group = project.property("mavenGroup") as String

base {
    archivesName.set(project.property("archivesBaseName") as String)
}

legacyForge {
    version = "$mcVersion-${project.property("forgeVersion")}"

    validateAccessTransformers.set(true)

    parchment {
        mappingsVersion = project.property("parchmentMappings") as String
        minecraftVersion = mcVersion
    }
    runs {
        configureEach {
            gameDirectory = file("../../run-forge/")
            jvmArgument("-XX:+AllowEnhancedClassRedefinition")
        }
        register("client") {
            client()
        }
    }

    mods {
        register("togglevisualize") {
            sourceSet(sourceSets["main"])
        }
    }
}

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
    }
}

dependencies {
    implementation("thedarkcolour:kotlinforforge:${project.property("kotlinForForgeVersion")}")
    modImplementation("dev.isxander:yet-another-config-lib:${project.property("yaclVersion")}")
}

kotlin {
    val jvm = JvmTarget.valueOf("JVM_${projectJavaVersion}")
    compilerOptions {
        jvmTarget.set(jvm)
    }
}

java {
    val java = JavaVersion.valueOf("VERSION_$projectJavaVersion")

    sourceCompatibility = java
    targetCompatibility = java
}

tasks.withType<JavaExec>().configureEach {
    javaLauncher = javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(projectJavaVersion))
        vendor.set(JvmVendorSpec.JETBRAINS)
    }
}

val buildAndCollect = tasks.register<Copy>("buildAndCollect") {
    group = "versioned"
    description = "Must run thorough 'chiseledBuild'"
    from(project.tasks.getByName("reobfJar", Jar::class).archiveFile)
    into(rootProject.layout.buildDirectory.dir("libs/$modVersion/$loader"))
    dependsOn("build")
}

if (stonecutter.current.isActive) {
    rootProject.tasks.register("buildActive") {
        group = "project"
        dependsOn(buildAndCollect)
    }

    rootProject.tasks.register("runActive") {
        group = "project"
        dependsOn(tasks.named("runClient"))
    }
}

tasks.named<ProcessResources>("processResources") {
    inputs.property("minecraft", mcVersion)

    exclude("fabric.mod.json", "META-INF/neoforge.mods.toml")
    filesMatching("META-INF/mods.toml") {
        expand(mapOf(
            "version" to version,
            "loaderVersionRange" to project.property("loaderVersionRange"),
            "forgeVersionRange" to project.property("forgeVersionRange"),
            "minecraftVersionRange" to project.property("minecraftVersionRange")
        ))
    }
    filesMatching("pack.mcmeta") {
        expand(mapOf("packFormat" to project.property("packFormat")))
    }
}

publishMods {
    val mcVersions = when(mcVersion) {
        "1.21.3" -> listOf("1.21.3", "1.21.4", "1.21.5")
        "1.21.6" -> listOf("1.21.6", "1.21.7", "1.21.8")
        else -> listOf(mcVersion)
    }

    displayName = "$modVersion for $loader $mcVersion"
    file = project.tasks.getByName("reobfJar", Jar::class).archiveFile
    type = STABLE
    modLoaders.add(loader)
    changelog = rootProject.file("changelog.md").readText()

//	dryRun = true

    curseforge {
        accessToken = providers.environmentVariable("CURSEFORGE_API_KEY").get()
        projectId = "1195905"
        minecraftVersions = mcVersions

        clientRequired = true
        projectSlug = "toggle-visualize"

        requires("yacl", "kotlin-for-forge")
    }

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN").get()
        projectId = "brJIdf61"
        minecraftVersions = mcVersions

        requires("yacl", "kotlin-for-forge")
    }
}
