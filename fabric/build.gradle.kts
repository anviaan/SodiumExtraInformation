plugins {
    id("java")
    id("idea")
    id("fabric-loom") version ("1.16.1")
}

val MINECRAFT_VERSION = rootProject.extra["MINECRAFT_VERSION"] as String
val PARCHMENT_VERSION = rootProject.extra["PARCHMENT_VERSION"] as String?
val FABRIC_LOADER_VERSION = rootProject.extra["FABRIC_LOADER_VERSION"] as String
val FABRIC_API_VERSION = rootProject.extra["FABRIC_API_VERSION"] as String
val MOD_VERSION = rootProject.extra["MOD_VERSION"] as String

val SODIUM_VERSION = rootProject.extra["SODIUM_VERSION"] as String
val SODIUM_EXTRA_VERSION = rootProject.extra["SODIUM_EXTRA_VERSION"] as String
val MODMENU_VERSION = rootProject.extra["MODMENU_VERSION"] as String
val ARCHIVE_NAME = rootProject.extra["ARCHIVE_NAME"] as String

val ANVIANS_LIB = rootProject.extra["ANVIANS_LIB"] as String

base {
    archivesName.set("$ARCHIVE_NAME-fabric")
}

repositories {
    maven("https://maven.terraformersmc.com/")
}

dependencies {
    minecraft("com.mojang:minecraft:${MINECRAFT_VERSION}")
    add("mappings", loom.layered {
        officialMojangMappings()
        if (PARCHMENT_VERSION != null) {
            parchment("org.parchmentmc.data:parchment-${MINECRAFT_VERSION}:${PARCHMENT_VERSION}@zip")
        }
    })
    modImplementation("net.fabricmc:fabric-loader:$FABRIC_LOADER_VERSION")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${FABRIC_API_VERSION}")

    compileOnly(project(":common"))
    add("modImplementation", "net.caffeinemc:sodium-fabric:$SODIUM_VERSION")
    add("modImplementation", "maven.modrinth:sodium-extra:$SODIUM_EXTRA_VERSION+fabric")
    add("modLocalRuntime", "com.terraformersmc:modmenu:$MODMENU_VERSION")
    add("modImplementation", "net.anvian.anvianslib:anvianslib-fabric-1.21:$ANVIANS_LIB")
}

tasks.test {
    failOnNoDiscoveredTests = false
}

loom {
    accessWidenerPath.set(project(":common").file("src/main/resources/${rootProject.name}.accesswidener"))

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
