pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev/")
		maven("https://maven.minecraftforge.net/")
		maven("https://maven.neoforged.net/releases/")
		mavenCentral()
		gradlePluginPortal()
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.5.1"
}

stonecutter {
	kotlinController = true
	centralScript = "build.gradle.kts"

	shared {
		fun mc(loader: String, vararg versions: String) {
			for (version in versions) vers("$version-$loader", version)
		}
		mc("fabric", "1.20.1", "1.20.4", "1.20.6", "1.21.1", "1.21.3", "1.21.6")
	}
	create(rootProject)
}

rootProject.name = "ToggleVisualize"
