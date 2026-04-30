plugins {
    id("java")
    id("fabric-loom") version ("1.11-SNAPSHOT") apply (false)
}

val MINECRAFT_VERSION by extra { "1.21.10" }
val NEOFORGE_VERSION by extra { "21.9.11-beta" }
val FABRIC_LOADER_VERSION by extra { "0.17.2" }
val FABRIC_API_VERSION by extra { "0.134.0+1.21.9" }

// This value can be set to null to disable Parchment.
val PARCHMENT_VERSION by extra { null }

// https://semver.org/
val MAVEN_GROUP by extra { "me.flashyreese.mods" }
val ARCHIVE_NAME by extra { "sodium-extra" }
val MOD_VERSION by extra { "2.7.0" }
val SODIUM_VERSION by extra { "mc1.21.9-0.7.0" }
val SODIUM_EXTRA_VERSION by extra { "mc1.21.9-0.7.0" }
val MODMENU_VERSION by extra { "16.0.0-rc.1" }

val ANVIANS_LIB by extra { "1.4" }

val COMPATIBLE_VERSIONS by extra { "[1.21.9, 1.21.10]" }

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
        maven("https://repo.repsy.io/mvn/anvian/anvians-lib")
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
