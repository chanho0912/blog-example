package com.noah.springdata.blog.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SimpleJDBCServiceTest : FunSpec({

    val service = SimpleJDBCService()
    test("insert") {
        service.insert(10, "blog-example")
    }

    test("find") {
        val saved = service.findById(10)
        saved.second shouldBe "blog-example"
    }
})
