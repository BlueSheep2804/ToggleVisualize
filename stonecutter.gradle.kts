plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.6-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast("-"), "fabric")
}

for (version in stonecutter.versions.map { it.project }.distinct()) tasks.register("publish$version") {
    group = "publishing"
    dependsOn(stonecutter.tasks.named("publishMods") { metadata.version == version })
}
