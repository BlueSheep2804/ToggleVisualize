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
	id("dev.kikugie.stonecutter") version "0.7.10"
}

stonecutter {
	shared {
		fun mc(loader: String, vararg versions: String) {
			for (version in versions) version("$version-$loader", version).buildscript.set("build.$loader.gradle.kts")
		}
		mc("fabric", "1.20.1", "1.20.4", "1.20.6", "1.21.1", "1.21.3", "1.21.6")
		vcsVersion = "1.21.6-fabric"
	}
	create(rootProject)
}

rootProject.name = "ToggleVisualize"
