import net.fabricmc.loom.task.AbstractRemapJarTask

plugins {
    id("java")
    id("idea")
    id("net.fabricmc.fabric-loom") version "1.15.4"
}

val MINECRAFT_VERSION: String by rootProject.extra
val PARCHMENT_VERSION: String? by rootProject.extra
val FABRIC_LOADER_VERSION: String by rootProject.extra
val FABRIC_API_VERSION: String by rootProject.extra

val SODIUM_VERSION: String by rootProject.extra
val SODIUM_EXTRA_VERSION: String by rootProject.extra

val ANVIANS_LIB: String by rootProject.extra

// This trick hides common tasks in the IDEA list.
tasks.configureEach {
    group = null
}

dependencies {
    minecraft("com.mojang:minecraft:$MINECRAFT_VERSION")
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")
    compileOnly("net.fabricmc:sponge-mixin:0.13.2+mixin.0.8.5")

    implementation("net.fabricmc.fabric-api:fabric-api:$FABRIC_API_VERSION")


    implementation("net.caffeinemc:sodium-fabric:$SODIUM_VERSION")
    implementation("maven.modrinth:sodium-extra:$SODIUM_EXTRA_VERSION+fabric")

    compileOnly("net.anvian.anvianslib:anvianslib-common-26.1:$ANVIANS_LIB")
}

tasks.withType<AbstractRemapJarTask>().forEach {
    it.targetNamespace = "named"
}

tasks.named("compileJava") {
    mustRunAfter("genSourcesWithVineflower")
}

loom {
    mixin {
        useLegacyMixinAp = false
    }

    //accessWidenerPath = file("src/main/resources/${rootProject.name}.accesswidener")
}
