import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id(Plugins.SPRING_BOOT)
    id(Plugins.DEPENDENCY_MANAGEMENT)
    id(Plugins.KOTLIN_SPRING) version PluginVersions.KOTLIN
    id(Plugins.KOTLIN_JPA) version PluginVersions.KOTLIN
    id(Plugins.EXAMPLE_DATA_JPA)
}

repositories {
    mavenCentral()
}

dependencies {
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(ProjectConfigs.JSR_305)
        jvmTarget = ProjectConfigs.JVM
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false

