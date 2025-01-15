package org.example.conference_app_demo.controller

import org.example.conference_app_demo.service.ConferenceService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@Controller
class HomeController(private val conferenceService: ConferenceService) {
    @GetMapping("/", "/homepage")
    fun homepage(model: Model): String {
        return "homepage"
    }
}
