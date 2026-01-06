import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("org.jetbrains.kotlin.jvm")
	id("fabric-loom") version "1.11-SNAPSHOT"
	id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

val mcVersion = stonecutter.current.version
val projectJavaVersion = when {
	stonecutter.eval(mcVersion, ">=1.20.5") -> 21
	else -> 17
}
val loader = "fabric"

val modVersion = project.property("modVersion")
version = "$modVersion+$mcVersion-$loader"
group = project.property("mavenGroup") as String

base {
	archivesName.set(project.property("archivesBaseName") as String + "-$loader")
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
	val fabricVersion: String by project
	val fabricApiVersion: String by project
	val fabricKotlinVersion: String by project
	val yaclVersion: String by project
	val modmenuVersion: String by project
	val parchmentMappings: String by project

	minecraft("com.mojang:minecraft:$mcVersion")
	mappings(loom.layered {
		officialMojangMappings()
		if (parchmentMappings != "none") {
			parchment("org.parchmentmc.data:parchment-$mcVersion:$parchmentMappings@zip")
		}
	})

	modImplementation("net.fabricmc:fabric-loader:${fabricVersion}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${fabricKotlinVersion}")
	if (modmenuVersion != "none") {
		modImplementation("com.terraformersmc:modmenu:${modmenuVersion}")
	}

	modImplementation("dev.isxander:yet-another-config-lib:${yaclVersion}")
}

loom {
	runConfigs.all {
		ideConfigGenerated(true)
		runDir = "../../run"
		vmArg("-XX:+AllowEnhancedClassRedefinition")
	}
}

kotlin {
	val jvm = JvmTarget.valueOf("JVM_${projectJavaVersion}")
	compilerOptions {
		jvmTarget.set(jvm)
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

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
	from(tasks.remapJar.get().archiveFile)
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

tasks.processResources {
	inputs.property("minecraft", mcVersion)

	exclude("META-INF/mods.toml", "META-INF/neoforge.mods.toml")
	filesMatching("fabric.mod.json") {
		expand(mapOf(
			"version" to version,
			"javaVersion" to projectJavaVersion,
			"minecraftVersion" to mcVersion,
			"yaclVersion" to project.property("yaclVersion")
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
		"1.21.9" -> listOf("1.21.9", "1.21.10")
		else -> listOf(mcVersion)
	}

	displayName = "$modVersion for $loader $mcVersion"
	file = project.tasks.remapJar.get().archiveFile
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

		requires("yacl", "fabric-api", "fabric-language-kotlin")
		optional("modmenu")
	}

	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_TOKEN").get()
		projectId = "brJIdf61"
		minecraftVersions = mcVersions

		requires("yacl", "fabric-api", "fabric-language-kotlin")
		optional("modmenu")
	}
}
