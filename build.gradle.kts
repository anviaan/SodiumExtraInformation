plugins {
    id("java")
    id("fabric-loom") version ("1.16.1") apply (false)
}

extra.set("MINECRAFT_VERSION", "1.21.1")
extra.set("NEOFORGE_VERSION", "21.1.230")
extra.set("FABRIC_LOADER_VERSION", "0.19.2")
extra.set("FABRIC_API_VERSION", "0.116.12+1.21.1")

// This value can be set to null to disable Parchment.
extra.set("PARCHMENT_VERSION", null)

// https://semver.org/
extra.set("MAVEN_GROUP", "net.anvian.sodiumextrainformation")
extra.set("ARCHIVE_NAME", "SodiumExtraInformation")
extra.set("MOD_VERSION", "2.6.0")
extra.set("SODIUM_VERSION", "0.8.12+mc1.21.1")
extra.set("SODIUM_EXTRA_VERSION", "mc1.21.1-0.9.1")
extra.set("MODMENU_VERSION", "11.0.3")

extra.set("ANVIANS_LIB", "1.4.1")

extra.set("COMPATIBLE_VERSIONS", "[1.21.1, 1.21.2)")

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    group = rootProject.extra["MAVEN_GROUP"] as String
    version = rootProject.extra["MOD_VERSION"] as String
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

subprojects {
    apply(plugin = "maven-publish")

    val modVersion = rootProject.extra["MOD_VERSION"] as String
    val archiveName = rootProject.extra["ARCHIVE_NAME"] as String
    val mavenGroup = rootProject.extra["MAVEN_GROUP"] as String
    val minecraftVersion = rootProject.extra["MINECRAFT_VERSION"] as String
    val compatibleVersions = rootProject.extra["COMPATIBLE_VERSIONS"] as String

    repositories {
        maven("https://maven.parchmentmc.org/")
        maven("https://maven.caffeinemc.net/releases")
        maven("https://maven.caffeinemc.net/snapshots")
        maven("https://api.modrinth.com/maven")
        maven("https://libraries.minecraft.net")
        maven("https://repo.repsy.io/mvn/anvian/anvians-lib")
    }

    base {
        archivesName = "$archiveName-${project.name}"
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)

    tasks.processResources {
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to { modVersion }))
        }
    }

    version = modVersion
    group = mavenGroup

    extensions.configure<PublishingExtension>("publishing") {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifactId = "${project.name}-$minecraftVersion"
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
            envFile.appendText("MOD_VERSION=$modVersion\n")
            envFile.appendText("RELEASE_NAME=$archiveName-$modVersion\n")
            envFile.appendText("GAME_VERSIONS=$compatibleVersions\n")
        }
    }
}
