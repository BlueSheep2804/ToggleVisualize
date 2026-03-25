plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    gradlePluginPortal()

    maven("https://maven.fabricmc.net/")
}

dependencies {
    implementation(libs.stonecutter)
    implementation(libs.loom)
    implementation(libs.loom.remap)
}

gradlePlugin {
    // Define the plugin
    val fabric by plugins.creating {
        id = "togglevisualize.fabric"
        implementationClass = "togglevisualize.FabricPlugin"
    }
}
