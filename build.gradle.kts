plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "org.labs"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
    flatDir {
        dirs("lib")
    }
}

intellij {
    version.set("2024.3")
    type.set("IU") // Utilisez "IU" pour la version Ultimate
    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.1")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")
    // Inclusion de tous les JARs du dossier lib
    implementation(fileTree("lib") { include("*.jar") })
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.compilerArgs.add("-parameters")
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")  // Supports IntelliJ from version 2023.2
        untilBuild.set("243.*")  // Supports IntelliJ up to version 2024.3
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
