plugins {
    id("kotlin-conventions")
    id("testing-conventions")
    id("spring-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-server
    implementation("org.springframework.cloud:spring-cloud-config-server:4.1.3")


    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
