import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("net.neoforged.moddev.legacyforge") version "2.0.107"
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

val mcVersion = stonecutter.current.version
val javaVersion = if (stonecutter.eval(mcVersion, ">=1.20.5")) 21 else 17
val loader = "forge"

val modVersion = project.property("modVersion")
version = "$modVersion+$mcVersion"
group = project.property("mavenGroup") as String

base {
    archivesName.set(project.property("archivesBaseName") as String + "-$loader")
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
    val jvm = if (javaVersion == 21)
        JvmTarget.JVM_21 else JvmTarget.JVM_17
    compilerOptions {
        jvmTarget.set(jvm)
    }
}

java {
    val java = if (javaVersion == 21)
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17

    sourceCompatibility = java
    targetCompatibility = java
}

val buildAndCollect = tasks.register<Copy>("buildAndCollect") {
    group = "versioned"
    description = "Must run thorough 'chiseledBuild'"
    from(tasks.jar.get().archiveFile)
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

    exclude("fabric.mod.json")
    filesMatching("META-INF/mods.toml") {
        expand(mapOf(
            "version" to version,
            "loaderVersionRange" to project.property("loaderVersionRange"),
            "forgeVersionRange" to project.property("forgeVersionRange"),
            "minecraftVersionRange" to project.property("minecraftVersionRange")
        ))
    }
    filesMatching("pack.mcmeta") {
        expand("packFormat" to project.property("packFormat"))
    }
}

publishMods {
    val mcVersions = when(mcVersion) {
        "1.21.3" -> listOf("1.21.3", "1.21.4", "1.21.5")
        "1.21.6" -> listOf("1.21.6", "1.21.7", "1.21.8")
        else -> listOf(mcVersion)
    }

    displayName = "$modVersion for $loader $mcVersion"
    file = project.tasks.jar.get().archiveFile
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
