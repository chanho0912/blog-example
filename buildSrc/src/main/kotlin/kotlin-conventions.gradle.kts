import gradle.kotlin.dsl.accessors._fa393d92bf243f86e14930a7cb20dbb9.implementation
import gradle.kotlin.dsl.accessors._fa393d92bf243f86e14930a7cb20dbb9.testImplementation

plugins {
    id("java-conventions")

    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm")
}

kotlin {
    jvmToolchain {
        languageVersion.set(
            JavaLanguageVersion.of(17)
        )
    }
    compilerOptions {
        @Suppress("SpellCheckingInspection")
        freeCompilerArgs.add("-Xjsr305=strict")
        allWarningsAsErrors = false
    }
}

dependencies {
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use Kotlin logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // Using Kotest
    ProjectDependencies.TestLib.KOTLIN_TEST.forEach { dp ->
        testImplementation(dp)
    }
}
