import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.modpublishplugin)
    alias(libs.plugins.moddevgradle)
}

val mcVersion = stonecutter.current.version
val projectJavaVersion = when {
    stonecutter.eval(mcVersion, ">=1.20.5") -> 21
    else -> 17
}
val loader = "neoforge"

val modVersion = project.property("modVersion")
version = "$modVersion+$mcVersion-$loader"
group = project.property("mavenGroup") as String

base {
    archivesName.set(project.property("archivesBaseName") as String)
}

neoForge {
    version = "${project.property("forgeVersion")}"

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
    implementation("thedarkcolour:kotlinforforge-neoforge:${project.property("kotlinForForgeVersion")}")
    implementation("dev.isxander:yet-another-config-lib:${project.property("yaclVersion")}")
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

    exclude("fabric.mod.json", "META-INF/mods.toml")
    filesMatching("META-INF/neoforge.mods.toml") {
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
    displayName = "$modVersion for $loader $mcVersion"
    file = project.tasks.jar.get().archiveFile
    type = STABLE
    modLoaders.add(loader)
    changelog = rootProject.file("changelog.md").readText()

//	dryRun = true

    curseforge {
        accessToken = providers.environmentVariable("CURSEFORGE_API_KEY").get()
        projectId = "1195905"
        minecraftVersions = listOf(mcVersion)

        clientRequired = true
        projectSlug = "toggle-visualize"

        requires("yacl", "kotlin-for-forge")
    }

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN").get()
        projectId = "brJIdf61"
        minecraftVersions = listOf(mcVersion)

        requires("yacl", "kotlin-for-forge")
    }
}
