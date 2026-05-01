plugins {
    id("java")
    id("net.fabricmc.fabric-loom") version ("1.15.4") apply (false)
}

val MINECRAFT_VERSION by extra { "26.1.1" }
val NEOFORGE_VERSION by extra { "26.1.1.2-beta" }
val FABRIC_LOADER_VERSION by extra { "0.18.6" }
val FABRIC_API_VERSION by extra { "0.145.3+26.1.1" }

// https://semver.org/
val MAVEN_GROUP by extra { "net.anvian.sodiumextrainformation" }
val ARCHIVE_NAME by extra { "SodiumExtraInformation" }
val MOD_VERSION by extra { "2.9.0" }
val SODIUM_VERSION by extra { "0.8.9+mc26.1.1" }
val SODIUM_EXTRA_VERSION by extra { "mc26.1.1-0.8.7" }
val MODMENU_VERSION by extra { "18.0.0-alpha.8" }

val ANVIANS_LIB by extra {"1.4"}
val COMPATIBLE_VERSIONS by extra { "[26.1, 27)" }

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
        maven("https://maven.caffeinemc.net/releases")
        maven("https://maven.caffeinemc.net/snapshots")
        maven("https://api.modrinth.com/maven")
        maven("https://libraries.minecraft.net")
        maven("https://repo.repsy.io/mvn/anvian/anvians-lib")
    }

    base {
        archivesName = "$ARCHIVE_NAME-${project.name}"
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(25)

    tasks.processResources {
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to { MOD_VERSION }))
        }
    }

    version = MOD_VERSION
    group = MAVEN_GROUP

    extensions.configure<PublishingExtension>("publishing") {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifactId = "${project.name}-${MINECRAFT_VERSION}"
            }
        }

        repositories {
            maven {
                name = "Reposilite"
                url = uri(
                    if (version.toString().endsWith("SNAPSHOT")) {
                        "https://maven.anvian.net/snapshots"
                    } else {
                        "https://maven.anvian.net/releases"
                    }
                )
                credentials {
                    username = (project.findProperty("reposilite.user") as String?)
                        ?: System.getenv("REPOSILITE_USER")
                    password = (project.findProperty("reposilite.token") as String?)
                        ?: System.getenv("REPOSILITE_TOKEN")
                }
            }
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(25)
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
