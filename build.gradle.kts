plugins {
    id("java")
    id("fabric-loom") version ("1.8.9") apply (false)
}

val MINECRAFT_VERSION by extra { "1.21.1" }
val NEOFORGE_VERSION by extra { "21.1.83" }
val FABRIC_LOADER_VERSION by extra { "0.16.9" }
val FABRIC_API_VERSION by extra { "0.110.0+1.21.1" }

// This value can be set to null to disable Parchment.
val PARCHMENT_VERSION by extra { null }

// https://semver.org/
val MAVEN_GROUP by extra { "net.anvian.sodiumextrainformation" }
val ARCHIVE_NAME by extra { "SodiumExtraInformation" }
val MOD_VERSION by extra { "2.3.2" }
val SODIUM_VERSION by extra { "mc1.21-0.6.0-beta.2" }
val SODIUM_EXTRA_VERSION by extra { "mc1.21.1-0.6.0-beta.3" }
val MODMENU_VERSION by extra { "11.0.3" }

val COMPATIBLE_VERSIONS by extra { "[1.21, 1.21.2)" }

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    group = MAVEN_GROUP
    version = MOD_VERSION
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

subprojects {
    apply(plugin = "maven-publish")

    repositories {
        maven("https://maven.parchmentmc.org/")
        maven("https://api.modrinth.com/maven")
        maven("https://libraries.minecraft.net")
    }

    base {
        archivesName = "$ARCHIVE_NAME-${project.name}"
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)

    tasks.processResources {
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to { MOD_VERSION }))
        }
    }

    version = MOD_VERSION
    group = MAVEN_GROUP

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    tasks.withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }

    tasks.register("printEnv") {
        doLast {
            val envFile = File(System.getenv("GITHUB_ENV"))
            envFile.appendText("MOD_VERSION=$MOD_VERSION\n")
            envFile.appendText("RELEASE_NAME=$ARCHIVE_NAME-$MOD_VERSION\n")
            envFile.appendText("GAME_VERSIONS=$COMPATIBLE_VERSIONS\n")
        }
    }
}