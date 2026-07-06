plugins {
    id("idea")
    id("net.neoforged.moddev") version "2.0.141"
    id("java-library")
}

val MINECRAFT_VERSION = rootProject.extra["MINECRAFT_VERSION"] as String
val PARCHMENT_VERSION = rootProject.extra["PARCHMENT_VERSION"] as String?
val NEOFORGE_VERSION = rootProject.extra["NEOFORGE_VERSION"] as String
val MOD_VERSION = rootProject.extra["MOD_VERSION"] as String

val SODIUM_VERSION = rootProject.extra["SODIUM_VERSION"] as String
val SODIUM_EXTRA_VERSION = rootProject.extra["SODIUM_EXTRA_VERSION"] as String
val ARCHIVE_NAME = rootProject.extra["ARCHIVE_NAME"] as String

val ANVIANS_LIB = rootProject.extra["ANVIANS_LIB"] as String

base {
    archivesName = "$ARCHIVE_NAME-neoforge"
}

repositories {
    maven("https://maven.su5ed.dev/releases")
    maven("https://maven.neoforged.net/releases/")
    maven("https://maven.caffeinemc.net/releases")
    maven("https://maven.caffeinemc.net/snapshots")

    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = uri("https://api.modrinth.com/maven")
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

tasks.jar {
    from(rootDir.resolve("LICENSE.txt"))

    filesMatching("neoforge.mods.toml") {
        expand(mapOf("version" to MOD_VERSION))
    }
}

neoForge {
    // Specify the version of NeoForge to use.
    version = NEOFORGE_VERSION

    /*parchment {
        mappingsVersion = PARCHMENT_VERSION
        minecraftVersion = MINECRAFT_VERSION
    }*/

    runs {
        create("client") {
            client()
            ideName = "NeoForge/Client"
            val sodiumRuntime =
                project.dependencies.create("net.caffeinemc:sodium-neoforge:$SODIUM_VERSION") as ExternalModuleDependency
            sodiumRuntime.isTransitive = false
            getAdditionalRuntimeClasspathConfiguration().dependencies.add(sodiumRuntime)
        }
    }

    mods {
        create(project.name) {
            sourceSet(sourceSets.main.get())
        }
    }
}

fun includeDep(dependency: String, closure: Action<ExternalModuleDependency>) {
    dependencies.implementation(dependency, closure)
    dependencies.jarJar(dependency, closure)
}

fun includeDep(dependency: String) {
    dependencies.implementation(dependency)
    dependencies.jarJar(dependency)
}

tasks.named("compileTestJava").configure {
    enabled = false
}

dependencies {
    compileOnly(project(":common"))
    implementation("net.caffeinemc:sodium-neoforge-mod:$SODIUM_VERSION")
    compileOnly("net.caffeinemc:sodium-neoforge-api:${SODIUM_VERSION}")
    implementation("maven.modrinth:sodium-extra:$SODIUM_EXTRA_VERSION+neoforge")

    implementation("net.anvian.anvianslib:anvianslib-neoforge-1.21:${ANVIANS_LIB}")
}

// NeoGradle compiles the game, but we don't want to add our common code to the game's code
val notNeoTask: (Task) -> Boolean = { it: Task ->
    !it.name.startsWith("neo") && !it.name.startsWith("compileService")
}

tasks.withType<JavaCompile>().matching(notNeoTask).configureEach {
    source(project(":common").sourceSets.main.get().allSource)
}

tasks.withType<Javadoc>().matching(notNeoTask).configureEach {
    source(project(":common").sourceSets.main.get().allJava)
}

tasks.withType<ProcessResources>().matching(notNeoTask).configureEach {
    from(project(":common").sourceSets.main.get().resources)
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)
