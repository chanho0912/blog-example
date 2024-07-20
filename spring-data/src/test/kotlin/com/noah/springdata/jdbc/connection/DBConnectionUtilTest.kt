package com.noah.springdata.jdbc.connection

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe

class DBConnectionUtilTest : ShouldSpec({

    context("DBConnectionUtilTest") {
        should("getConnection") {
            val connection = DBConnectionUtil.getConnection()
            connection shouldNotBe null
        }
    }
})
