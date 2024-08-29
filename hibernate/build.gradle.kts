plugins {
    id("kotlin-conventions")
    id("testing-conventions")
    kotlin("plugin.jpa")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.hibernate:hibernate-core:6.4.9.Final")
    implementation("jakarta.persistence:jakarta.persistence-api")

    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("mysql:mysql-connector-java:8.0.31")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

//sourceSets {
//    main {
//        //if you truly want to override the defaults:
//        output.setResourcesDir(file("src/main/resources/META-INF"))
//        // Compiled Java classes should use this directory
//        java.destinationDirectory.set(file("build/classes"))
//    }
//}
//
//tasks.named<Jar>("sourcesJar") {
//    dependsOn(tasks.named("processResources"))
//}