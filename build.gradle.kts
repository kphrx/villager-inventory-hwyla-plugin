import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
  // Add repositories to retrieve artifacts from in here.
  // You should only use this when depending on other mods because
  // Loom adds the essential maven repositories to download Minecraft and libraries from
  // automatically.
  // See https://docs.gradle.org/current/userguide/declaring_repositories.html
  // for more information about repositories.
  exclusiveContent {
    forRepository {
      maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
      }
    }
    filter { includeGroup("maven.modrinth") }
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
  // To change the versions see the gradle.properties file
  minecraft(libs.minecraft)
  implementation(libs.bundles.fabric)
  implementation(libs.jade)
  implementation(libs.wthit)
}

tasks.withType<ProcessResources> {
  inputs.property("version", version)
  inputs.property("java", 25)
  inputs.property("minecraft", libs.versions.minecraft.get().replace("snapshot-", "alpha."))
  inputs.property("fabricloader", libs.versions.fabric.loader)
  inputs.property("jade", libs.versions.jade)
  inputs.property("wthit", libs.versions.wthit.get().removePrefix("fabric-"))

  filesMatching("fabric.mod.json") { expand(inputs.properties) }
}

tasks.withType<JavaCompile>().configureEach { options.release = 25 }

tasks.withType<KotlinCompile>().configureEach { exclude("wthit/**") }

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_25 } }

java {
  withSourcesJar()

  sourceCompatibility = JavaVersion.VERSION_25
  targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<Jar> {
  inputs.property("archivesName", base.archivesName)

  from("LICENSE") { rename { "${it}_${inputs.properties["archivesName"]}" } }
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
  uploadFile.set(tasks.jar)
  additionalFiles.add(tasks.named("sourcesJar"))
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
