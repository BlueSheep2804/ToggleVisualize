import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("fabric-loom") version "1.9-SNAPSHOT"
	id("maven-publish")
	id("org.jetbrains.kotlin.jvm") version "2.1.0"
	id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

val modVersion = project.property("modVersion")
version = "$modVersion+${stonecutter.current.project}"
group = project.property("mavenGroup") as String

base {
	archivesName.set(project.property("archivesBaseName") as String)
}

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
	maven {
		name = "ParchmentMC"
		url = uri("https://maven.parchmentmc.org")
	}
	maven {
		name = "Xander Maven"
		url = uri("https://maven.isxander.dev/releases")
	}
	maven {
		name = "Terraformers"
		url = uri("https://maven.terraformersmc.com/")
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	val loaderVersion: String by project
	val fabricVersion: String by project
	val fabricKotlinVersion: String by project
	val yaclVersion: String by project
	val modmenuVersion: String by project
	val parchmentMappings: String by project

	minecraft("com.mojang:minecraft:${stonecutter.current.project}")
	mappings(loom.layered {
		officialMojangMappings()
		parchment("org.parchmentmc.data:parchment-${stonecutter.current.project}:${parchmentMappings}@zip")
	})
	modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${fabricKotlinVersion}")

	modImplementation("dev.isxander:yet-another-config-lib:${yaclVersion}")
	modImplementation("com.terraformersmc:modmenu:${modmenuVersion}")
}

tasks {
	compileJava {
		options.release.set(21)
	}

	processResources {
		inputs.property("minecraft", stonecutter.current.project)

		filesMatching("fabric.mod.json") {
			expand(mapOf(
				"version" to version,
				"minecraftVersion" to stonecutter.current.project,
				"yaclVersion" to project.property("yaclVersion")
			))
		}
	}

	jar {
//		archiveBaseName = "${project.base.archivesName}-${stonecutter.current.version}-${stonecutter.current.project}"

		from("LICENSE") {
			rename { "${it}_${project.base.archivesName}" }
		}
	}
}

kotlin {
	compilerOptions {
		jvmTarget.set(JvmTarget.JVM_21)
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

loom {
	runConfigs.all {
		ideConfigGenerated(true)
		runDir = "../../run"
	}
}

publishMods {
	val mcVersions = when(stonecutter.current.project) {
		"1.21.3" -> listOf("1.21.3", "1.21.4")
		else -> listOf(stonecutter.current.project)
	}

	file.set(tasks.remapJar.get().archiveFile)
	type = STABLE
	modLoaders.add("fabric")
	changelog = "Please check the [Github repository](https://github.com/BlueSheep2804/ToggleVisualize) for the update history."

//	dryRun = true

	curseforge {
		accessToken = providers.environmentVariable("CURSEFORGE_API_KEY").get()
		projectId = "1195905"
		minecraftVersions = mcVersions

		clientRequired = true
		projectSlug = "toggle-visualize"

		requires("yacl", "fabric-api")
		optional("modmenu")
	}

	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_TOKEN").get()
		projectId = "brJIdf61"
		minecraftVersions = mcVersions

		requires("yacl", "fabric-api")
		optional("modmenu")
	}
}
