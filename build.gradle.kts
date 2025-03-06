plugins {
    kotlin("jvm") version "2.0.20"
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = "no.ruter.gradle"
version = "0.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
    }
}

gradlePlugin {
    website.set("https://github.com/RuterNo/rdp-gradle-gitlab-repositories")
    vcsUrl.set("https://github.com/RuterNo/rdp-gradle-gitlab-repositories")
    plugins {
        create("rdpGradleGitlabRepositories") {
            id = "no.ruter.gradle.rdp-gradle-gitlab-repositories"
            displayName = "RDP Gradle Git repositories"
            implementationClass = "no.ruter.gradle.RdpGradleGitlabRepositoriesPlugin"
            description = "A plugin that add extension for using GitLab repositories"
            tags = listOf("repositories", "gitlab")
        }
    }
}
