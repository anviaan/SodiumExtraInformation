plugins {
    id("java")
    id("idea")
    id("net.fabricmc.fabric-loom") version ("1.15.4")
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
    compileOnly("net.fabricmc:fabric-loader:$FABRIC_LOADER_VERSION")

    implementation("net.fabricmc.fabric-api:fabric-api:$FABRIC_API_VERSION")

    compileOnly(project(":common"))
    implementation("net.caffeinemc:sodium-fabric:$SODIUM_VERSION")
    implementation("maven.modrinth:sodium-extra:$SODIUM_EXTRA_VERSION+fabric")
    localRuntime("com.terraformersmc:modmenu:$MODMENU_VERSION")

    compileOnly("net.anvian.anvianslib:anvianslib-fabric-26.1:$ANVIANS_LIB")
    localRuntime("net.anvian.anvianslib:anvianslib-fabric-26.1:$ANVIANS_LIB")
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
    named<JavaCompile>("compileJava") {
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

tasks.named("validateAccessWidener").configure {
    dependsOn(":common:genSourcesWithVineflower")
}
