package org.example.conference_app_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class ConferenceAppDemoApplication

fun main(args: Array<String>) {
    runApplication<ConferenceAppDemoApplication>(*args)
}
