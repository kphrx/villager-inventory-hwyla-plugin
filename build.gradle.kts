import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

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
  mappings(loom.officialMojangMappings())
  modImplementation(libs.bundles.fabric)
  modImplementation(libs.jade)
  modImplementation(libs.wthit)
}

tasks.withType<ProcessResources> {
  inputs.property("version", version)
  inputs.property("minecraft", libs.versions.minecraft)
  inputs.property("fabricloader", libs.versions.fabric.loader)
  inputs.property("jade", libs.versions.jade)
  inputs.property("wthit", libs.versions.wthit)

  filesMatching("fabric.mod.json") { expand(inputs.properties) }
}

tasks.withType<JavaCompile>().configureEach { options.release = 21 }

tasks.withType<KotlinJvmCompile>().all { compilerOptions { jvmTarget.set(JvmTarget.JVM_21) } }

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

val mod_version_type: String by project
val modrinth_changelog: String? by project
val minecraft_forword_compatible_versions: String by project
val release: String? by project

modrinth {
  token.set(System.getenv("MODRINTH_TOKEN"))
  projectId.set(project.base.archivesName)
  versionType.set(mod_version_type)
  if (modrinth_changelog != "") {
    changelog.set(modrinth_changelog)
  }
  uploadFile.set(tasks.remapJar)
  gameVersions.add(libs.versions.minecraft.get())
  gameVersions.addAll(
      minecraft_forword_compatible_versions.split(",").map { it.trim() }.filter { it != "" })
  dependencies {
    required.project("fabric-language-kotlin")
    optional.project("jade")
    optional.project("wthit")
  }
  debugMode.set(release == null)

  syncBodyFrom.set(rootProject.file("README.md").readText())
}
