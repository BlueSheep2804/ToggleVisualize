plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.9-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast("-"), "fabric", "forge", "neoforge")
    constants["modmenu"] = (node.project.property("modmenuVersion") as String) != "none"
}

tasks.register("buildAndCollectAll") {
    group = "project"
    dependsOn(stonecutter.tasks.named("buildAndCollect"))
}

stonecutter.tasks {
    order("publishCurseforge")
    order("publishModrinth")
}

for (version in stonecutter.versions.map { it.project }.distinct()) tasks.register("publish$version") {
    group = "publishing"
    dependsOn(stonecutter.tasks.named("publishMods") { metadata.version == version })
}
