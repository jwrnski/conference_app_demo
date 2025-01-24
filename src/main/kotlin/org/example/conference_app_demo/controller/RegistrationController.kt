package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Registration
import org.example.conference_app_demo.service.RegistrationService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/registrations")
class RegistrationController(private val registrationService: RegistrationService) {


    @PostMapping
    fun registerUser(conferenceId: Long, userId: Long): String{
        registrationService.registerUser(conferenceId, userId)
        return "redirect:/conferences/$conferenceId"
    }

    @PutMapping
    fun unregisterUser(conferenceId: Long, userId: Long): String{
        registrationService.unregisterUser(conferenceId, userId)
        return "redirect:/conferences/$conferenceId"
    }
}