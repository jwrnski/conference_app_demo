package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.model.ConferenceCategory
import org.example.conference_app_demo.model.Country
import org.example.conference_app_demo.repository.RegistrationRepository
import org.example.conference_app_demo.service.ConferenceService
import org.example.conference_app_demo.service.RegistrationService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/conferences")
class ConferenceController(
    private val conferenceService: ConferenceService,
    private val registrationService: RegistrationService,
    private val registrationRepository: RegistrationRepository
) {

    @PostMapping
    fun createConference(@ModelAttribute conference: Conference): String {
        conferenceService.save(conference);
        return "redirect:/conferences";
    }

    @GetMapping("/create-conference")
    fun createConferencePage(model: Model): String {
        model.addAttribute("categories", ConferenceCategory.entries.toTypedArray())
        model.addAttribute("countries", Country.entries.toTypedArray())
        return "conference/create-conference"
    }

    @PutMapping("/edit/{id}")
    fun updateConference(@PathVariable id:Long, @ModelAttribute conference: Conference): String {
        conferenceService.update(conference, id);
        return "redirect:/conferences/$id";
    }

    @GetMapping("/{id}")
    fun getConferenceDetails(@PathVariable id: Long, model: Model): String {
        val conference = conferenceService.findById(id)
        model.addAttribute("conference", conference)
        val day = conference.startDate.dayOfMonth
        val month = conference.startDate.monthValue
        model.addAttribute("day", day)
        model.addAttribute("month", month)
        val authenticatedUser = SecurityContextHolder.getContext().authentication
        val principal = authenticatedUser.principal
        if (principal is org.example.conference_app_demo.auth.CustomUserDetails) {
            val userId = principal.getId()
            model.addAttribute("userId", userId)
            val isRegistered = registrationService.isUserRegistered(conference.id, userId)
            model.addAttribute("isRegistered", isRegistered)
        }
        return "conference/conference-details"
    }

    @GetMapping("/edit/{id}")
    fun getEditConferencePage(@PathVariable id: Long, model: Model): String {
        val conference = conferenceService.findById(id)
        model.addAttribute("conference", conference)
        return "conference/edit-conference"
    }

    @GetMapping()
    fun getAllConferences(model: Model): String {
        val conferences = conferenceService.findAll();
        model.addAttribute("conferences", conferences);
        return "conference/conferences";
    }

    @DeleteMapping("/{id}")
    fun deleteConferenceById(@PathVariable id: Long): String{
        conferenceService.deleteById(id);
        return "redirect:conference/conferences";
    }

}