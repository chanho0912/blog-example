plugins {
    id("java-conventions")
    id("testing-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("ch.qos.logback:logback-core:1.2.3")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("org.slf4j:slf4j-api:1.7.25")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
}
