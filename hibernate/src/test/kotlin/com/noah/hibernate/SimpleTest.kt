package com.noah.hibernate

import io.kotest.core.spec.style.ShouldSpec

class SimpleTest : ShouldSpec({
    var simpleInt = 1
    context("sss") {
        for (i in 1..3) {
            if (a() && ++simpleInt == 2) {
                println("a")
            }
            println(simpleInt)
        }
    }

})

fun a(): Boolean {
    return false
}
