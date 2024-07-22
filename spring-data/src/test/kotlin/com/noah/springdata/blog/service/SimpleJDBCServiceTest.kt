package com.noah.springdata.blog.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class SimpleJDBCServiceTest : FunSpec({

    val service = SimpleJDBCService()
    val testName = "blog-example"
    val testId = Random(10).nextInt()

    test("insert") {
        service.insert(testId, testName)
    }

    test("find") {
        val saved = service.findById(testId)
        saved.second shouldBe testName
    }
})
