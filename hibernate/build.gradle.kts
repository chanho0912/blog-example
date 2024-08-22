plugins {
    id("kotlin-conventions")
    id("testing-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.hibernate:hibernate-core:6.4.9.Final")
    implementation("mysql:mysql-connector-java:8.0.31")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
