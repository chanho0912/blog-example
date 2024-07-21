plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = ProjectConfigs.GROUP

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(ProjectDependencies.Lib.KOTLIN)
    testImplementation(ProjectDependencies.TestLib.KOTLIN_TEST)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(ProjectConfigs.JSR_305)
        jvmTarget = ProjectConfigs.JVM
    }
}

tasks.getByName<Delete>("clean") {
    delete("${rootProject.buildDir}")
}

tasks.register("prepareKotlinBuildScriptModel") {}
