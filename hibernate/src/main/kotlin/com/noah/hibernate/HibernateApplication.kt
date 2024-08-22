package com.noah.hibernate

import jakarta.persistence.Persistence

class HibernateApplication

fun main(args: Array<String>) {

    Persistence.createEntityManagerFactory("noah").use { emf ->
        emf.createEntityManager().use { em ->
            println("hello world!")
        }
    }
}