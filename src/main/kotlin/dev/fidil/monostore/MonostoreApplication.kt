package dev.fidil.monostore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MonostoreApplication

fun main(args: Array<String>) {
    runApplication<MonostoreApplication>(*args)
}
