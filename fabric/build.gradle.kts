plugins {
    id("java")
    id("idea")
    id("fabric-loom") version ("1.11-SNAPSHOT")
}

val MINECRAFT_VERSION: String by rootProject.extra
val PARCHMENT_VERSION: String? by rootProject.extra
val FABRIC_LOADER_VERSION: String by rootProject.extra
val FABRIC_API_VERSION: String by rootProject.extra
val MOD_VERSION: String by rootProject.extra

val SODIUM_VERSION: String by rootProject.extra
val SODIUM_EXTRA_VERSION: String by rootProject.extra
val MODMENU_VERSION: String by rootProject.extra
val ARCHIVE_NAME: String by rootProject.extra

val ANVIANS_LIB: String by rootProject.extra

base {
    archivesName.set("$ARCHIVE_NAME-fabric")
}

repositories {
    maven("https://maven.terraformersmc.com/")
}

dependencies {
    minecraft("com.mojang:minecraft:${MINECRAFT_VERSION}")
    mappings(loom.layered {
        officialMojangMappings()
        if (PARCHMENT_VERSION != null) {
            parchment("org.parchmentmc.data:parchment-${MINECRAFT_VERSION}:${PARCHMENT_VERSION}@zip")
        }
    })
    modImplementation("net.fabricmc:fabric-loader:$FABRIC_LOADER_VERSION")

//    fun addEmbeddedFabricModule(name: String) {
//        val module = fabricApi.module(name, FABRIC_API_VERSION)
//        modImplementation(module)
//    }
//
//    // Fabric API modules
//    addEmbeddedFabricModule("fabric-api-base")
//    addEmbeddedFabricModule("fabric-block-view-api-v2")
//    addEmbeddedFabricModule("fabric-renderer-api-v1")
//    addEmbeddedFabricModule("fabric-attachment-api-v1")
//    addEmbeddedFabricModule("fabric-rendering-fluids-v1")
//    addEmbeddedFabricModule("fabric-resource-loader-v0")

    modImplementation("net.fabricmc.fabric-api:fabric-api:$FABRIC_API_VERSION")

    compileOnly(project(":common"))
    modImplementation("maven.modrinth:sodium:$SODIUM_VERSION-fabric")
    modRuntimeOnly("maven.modrinth:reeses-sodium-options:mc1.21.9-1.8.5+fabric")
    modImplementation("maven.modrinth:sodium-extra:$SODIUM_EXTRA_VERSION+fabric")
    modLocalRuntime("com.terraformersmc:modmenu:$MODMENU_VERSION")

    modImplementation("net.anvian.anvianslib:anvianslib-fabric-1.21.10:$ANVIANS_LIB")
}

tasks.test {
    failOnNoDiscoveredTests = false
}

loom {
    //accessWidenerPath.set(project(":common").file("src/main/resources/${rootProject.name}.accesswidener"))

    @Suppress("UnstableApiUsage")
    mixin { defaultRefmapName.set("${rootProject.name}.refmap.json") }

    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks {
    withType<JavaCompile> {
        source(project(":common").sourceSets.main.get().allSource)
    }

    javadoc { source(project(":common").sourceSets.main.get().allJava) }

    processResources {
        from(project(":common").sourceSets.main.get().resources)

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    jar {
        from(rootDir.resolve("LICENSE.txt"))
    }
}
