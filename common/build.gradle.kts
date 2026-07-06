import net.fabricmc.loom.task.AbstractRemapJarTask

plugins {
    id("java")
    id("idea")
    id("fabric-loom") version "1.16.1"
}

val MINECRAFT_VERSION = rootProject.extra["MINECRAFT_VERSION"] as String
val PARCHMENT_VERSION = rootProject.extra["PARCHMENT_VERSION"] as String?
val FABRIC_LOADER_VERSION = rootProject.extra["FABRIC_LOADER_VERSION"] as String
val FABRIC_API_VERSION = rootProject.extra["FABRIC_API_VERSION"] as String

val SODIUM_VERSION = rootProject.extra["SODIUM_VERSION"] as String
val SODIUM_EXTRA_VERSION = rootProject.extra["SODIUM_EXTRA_VERSION"] as String
val ANVIANS_LIB = rootProject.extra["ANVIANS_LIB"] as String

// This trick hides common tasks in the IDEA list.
tasks.configureEach {
    group = null
}

dependencies {
    minecraft(group = "com.mojang", name = "minecraft", version = MINECRAFT_VERSION)
    add("mappings", loom.layered {
        officialMojangMappings()
        if (PARCHMENT_VERSION != null) {
            parchment("org.parchmentmc.data:parchment-${MINECRAFT_VERSION}:${PARCHMENT_VERSION}@zip")
        }
    })
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")
    compileOnly("net.fabricmc:sponge-mixin:0.13.2+mixin.0.8.5")
    compileOnly("net.fabricmc:fabric-loader:$FABRIC_LOADER_VERSION")

    fun addDependentFabricModule(name: String) {
        val module = fabricApi.module(name, FABRIC_API_VERSION)
        add("modCompileOnly", module)
    }

    addDependentFabricModule("fabric-api-base")
    addDependentFabricModule("fabric-block-view-api-v2")
    addDependentFabricModule("fabric-renderer-api-v1")
    addDependentFabricModule("fabric-rendering-data-attachment-v1")

    add("modImplementation", "net.caffeinemc:sodium-fabric:$SODIUM_VERSION")
    add("modImplementation", "maven.modrinth:sodium-extra:$SODIUM_EXTRA_VERSION+fabric")
    add("modImplementation", "net.anvian.anvianslib:anvianslib-common-1.21:$ANVIANS_LIB")
}

tasks.withType<AbstractRemapJarTask>().forEach {
    it.targetNamespace = "named"
}

loom {
    mixin {
        useLegacyMixinAp = false
    }

    accessWidenerPath = file("src/main/resources/${rootProject.name}.accesswidener")
}
