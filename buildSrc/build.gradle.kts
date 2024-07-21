plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main'
    // that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
    mavenCentral()
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
    // buildSrc in combination with this plugin ensures that the version set here
    // will be set to the same for all other Kotlin dependencies / plugins in the project.
    add("implementation", "org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")

    // https://kotlinlang.org/docs/all-open-plugin.html
    // contains also https://kotlinlang.org/docs/all-open-plugin.html#spring-support
    // The all-open compiler plugin adapts Kotlin to the requirements of those frameworks and makes classes annotated
    // with a specific annotation and their members open without the explicit open keyword.
    add("implementation", "org.jetbrains.kotlin:kotlin-allopen:1.9.20")

    // https://kotlinlang.org/docs/no-arg-plugin.html
    // contains also https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    // The no-arg compiler plugin generates an additional zero-argument constructor for classes
    // with a specific annotation.
    add("implementation", "org.jetbrains.kotlin:kotlin-noarg:1.9.20")

    // https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
    // The Spring Boot Gradle Plugin provides Spring Boot support in Gradle.
    // It allows you to package executable jar or war archives, run Spring Boot applications,
    // and use the dependency management provided by spring-boot-dependencies
    add("implementation", "org.springframework.boot:spring-boot-gradle-plugin:3.1.5")
}
