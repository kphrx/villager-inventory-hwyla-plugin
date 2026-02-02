import net.fabricmc.loom.task.RemapJarTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.fabric.loom)
  id("maven-publish")
  alias(libs.plugins.kotlin)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.minotaur)
}

val mod_version: String by project
val maven_group: String by project
val archives_base_name: String by project

version = mod_version

group = maven_group

base { archivesName.set(archives_base_name) }

repositories {
  maven {
    name = "Modrinth"
    url = uri("https://api.modrinth.com/maven")
    content { includeGroup("maven.modrinth") }
  }
  maven {
    name = "Badasintended"
    url = uri("https://maven4.bai.lol")
    content {
      includeGroup("lol.bai")
      includeGroup("mcp.mobius.waila")
    }
  }
}

loom {
  splitEnvironmentSourceSets()

  mods {
    create("villager-inventory-hwyla-plugin") {
      sourceSet(sourceSets["client"])
      sourceSet(sourceSets["main"])
    }
  }
}

fabricApi { configureDataGeneration { client = true } }

dependencies {
  minecraft(libs.minecraft)
  mappings(loom.officialMojangMappings())
  modImplementation(libs.jade)
  modCompileOnly(libs.wthit.api)
  modRuntimeOnly(libs.wthit.runtime)
  modRuntimeOnly(libs.badpackets)
}

tasks.withType<ProcessResources> {
  inputs.property("version", version)
  inputs.property("minecraft", libs.versions.minecraft)
  inputs.property("jade", libs.versions.jade)
  inputs.property("wthit", libs.versions.wthit.get().removePrefix("mojmap-"))

  filesMatching("fabric.mod.json") { expand(inputs.properties) }

  filesMatching("META-INF/neoforge.mods.toml") { expand(inputs.properties) }
}

tasks.withType<JavaCompile>().configureEach { options.release = 21 }

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_21 } }

java {
  // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
  // if it is present.
  // If you remove this line, sources will not be generated.
  withSourcesJar()

  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Jar> {
  inputs.property("archivesName", base.archivesName)

  from("LICENSE") { rename { "${it}_${inputs.properties["archivesName"]}" } }
}

val mojmapJar =
    tasks.register<RemapJarTask>("mojmapJar") {
      group = "build"
      description = "official mappings."

      sourceNamespace.set("intermediary")
      // targetNamespace.set("official")
      inputFile.set(tasks.jar.get().archiveFile)
    }

tasks.assemble { dependsOn(mojmapJar) }

mojmapJar {
  archiveClassifier.set("mojmap")
  exclude("fabric.mod.json")
}

tasks.remapJar {
  archiveClassifier.set("intermediary")
  exclude("META-INF/neoforge.mods.toml")
}

// configure the maven publication
publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      artifactId = archives_base_name
      from(components["java"])
    }
  }

  // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to
  // set up publishing.
  repositories {
    // Add repositories to publish to here.
    // Notice: This block does NOT have the same function as the block in the top level.
    // The repositories here will be used for publishing your artifact, not for
    // retrieving dependencies.
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY")}")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}

modrinth {
  val mod_version_type: String by project
  val modrinth_changelog: String? by project

  token.set(System.getenv("MODRINTH_TOKEN"))
  projectId.set(project.base.archivesName)
  versionType.set(mod_version_type)
  if (modrinth_changelog != "") {
    changelog.set(modrinth_changelog)
  }
  uploadFile.set(tasks.remapJar)
  additionalFiles.add(tasks.remapSourcesJar)
  gameVersions.add(libs.versions.minecraft.get())
  if (providers.gradleProperty("minecraft_forward_compatible_versions").isPresent) {
    val minecraft_forward_compatible_versions: String by project
    gameVersions.addAll(
        minecraft_forward_compatible_versions.split(",").map { it.trim() }.filter { it != "" })
  }
  dependencies {
    required.project("fabric-language-kotlin")
    optional.project("jade")
    optional.project("wthit")
  }
  debugMode.set(!providers.gradleProperty("release").isPresent)

  syncBodyFrom.set(rootProject.file("README.md").readText())
}

tasks.named("modrinth") { dependsOn(tasks.modrinthSyncBody) }
