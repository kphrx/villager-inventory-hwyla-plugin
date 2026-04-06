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
  implementation(libs.bundles.fabric)
  implementation(libs.jade)
  compileOnly(libs.wthit.api)
  runtimeOnly(libs.wthit.runtime)
  runtimeOnly(libs.badpackets)
}

tasks.withType<ProcessResources> {
  inputs.property("version", version)
  inputs.property("java", 25)
  inputs.property("minecraft", libs.versions.minecraft.get().replace("snapshot-", "alpha."))
  inputs.property("jadefabric", libs.versions.jade.fabric)
  inputs.property("jadeneoforge", libs.versions.jade.neoforge)
  inputs.property("wthit", libs.versions.wthit.get().removePrefix("mojmap-"))

  filesMatching("fabric.mod.json") { expand(inputs.properties) }

  filesMatching("META-INF/neoforge.mods.toml") { expand(inputs.properties) }
}

tasks.withType<JavaCompile>().configureEach { options.release = 25 }

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
