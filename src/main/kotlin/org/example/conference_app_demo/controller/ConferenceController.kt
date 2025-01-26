package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.*
import org.example.conference_app_demo.repository.RegistrationRepository
import org.example.conference_app_demo.service.ConferenceService
import org.example.conference_app_demo.service.RegistrationService
import org.example.conference_app_demo.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/conferences")
class ConferenceController(
    private val conferenceService: ConferenceService,
    private val registrationService: RegistrationService,
    private val registrationRepository: RegistrationRepository,
    private val userService: UserService
) {

    @PostMapping
    fun createConference(@ModelAttribute conference: Conference,
                         @RequestParam("userId") userId: Long): String {
        var currentDate = conference.startDate
        while (!currentDate.isAfter(conference.endDate)) {
            val schedule = Schedule(
                conference = conference,
                startDate = currentDate
            )
            conference.schedules.add(schedule) // Add schedule to the conference's schedule list
            currentDate = currentDate.plusDays(1) // Increment the date
        }
        val organizer = userService.findById(userId)
        conference.organizer = organizer
        conferenceService.save(conference);
        conferenceService.update(conference, conference.id)

        return "redirect:/conferences";
    }

    @GetMapping("/create-conference")
    fun createConferencePage(model: Model): String {
        model.addAttribute("categories", ConferenceCategory.entries.toTypedArray())
        model.addAttribute("countries", Country.entries.toTypedArray())
        val authenticatedUser = SecurityContextHolder.getContext().authentication
        val principal = authenticatedUser.principal
        if (principal is org.example.conference_app_demo.auth.CustomUserDetails) {
            val userId = principal.getId()
            model.addAttribute("userId", userId)
        }
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

        val schedules = conference.schedules

        model.addAttribute("schedules", schedules)

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