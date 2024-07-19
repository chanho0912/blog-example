object Dependencies {
    object Lib {
        val KOTLIN = listOf(
            "org.jetbrains.kotlin:kotlin-reflect",
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8",
            "io.github.microutils:kotlin-logging-jvm:3.0.4"
        )
    }

    object TestLib {
        val KOTLIN_TEST = listOf(
            "io.kotest:kotest-property:${TestLibVersion.KOTEST}",
            "io.kotest:kotest-assertions-core:${TestLibVersion.KOTEST}",
            "io.kotest:kotest-runner-junit5:${TestLibVersion.KOTEST}",
            "io.kotest:kotest-framework-datatest:${TestLibVersion.KOTEST}",
            "io.kotest.extensions:kotest-extensions-wiremock:${TestLibVersion.KOTEST_WIRE_MOCK}",
            "io.mockk:mockk:${TestLibVersion.MOCKK}"
        )
    }

    object TestLibVersion {
        const val MOCKK = "1.13.5"
        const val KOTEST = "5.7.2"
        const val KOTEST_WIRE_MOCK = "2.0.0"
    }
}
