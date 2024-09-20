plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.24"
  id("org.jetbrains.intellij") version "1.17.3"
}

group = "org.plugin"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://www.jetbrains.com/intellij-repository/releases")
}

intellij {
  version.set("2024.1.4")
  type.set("IU") // Utilisez "IU" pour la version Ultimate
  plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
  implementation("org.jetbrains:annotations:24.0.1")
  // autres dépendances
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
  }

  patchPluginXml {
    sinceBuild.set("232")
    untilBuild.set("242.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
