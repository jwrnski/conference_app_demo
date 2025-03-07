package org.example.conference_app_demo.controller

import jakarta.validation.Valid
import org.example.conference_app_demo.auth.CustomUserDetails
import org.example.conference_app_demo.dto.ConferenceDto
import org.example.conference_app_demo.model.*
import org.example.conference_app_demo.repository.RegistrationRepository
import org.example.conference_app_demo.service.ConferenceService
import org.example.conference_app_demo.service.RegistrationService
import org.example.conference_app_demo.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/conferences")
class ConferenceController(
    private val conferenceService: ConferenceService,
    private val registrationService: RegistrationService,
    private val userService: UserService
) {

    @PostMapping
    fun createConference(@Valid @ModelAttribute("conference") conferenceDTO: ConferenceDto,
                         bindingResult: BindingResult,
                         @RequestParam("userId") userId: Long,
                         model: Model
                         ): String {

        // Field Validation, return if there are errors
        if (bindingResult.hasErrors()) {
            //bindingResult.allErrors.forEach {error -> println("\nValidation error: ${error.defaultMessage}\n") }
            model.addAttribute("categories", ConferenceCategory.entries.toTypedArray())
            model.addAttribute("countries", Country.entries.toTypedArray())
            model.addAttribute("userId", userId)
            model.addAttribute("conference", conferenceDTO)
            return "conference/create-conference" // Return the form page with errors
        }

        val loggedInUser = userService.findById(userId)

        val conference = conferenceService.toEntity(conferenceDTO, loggedInUser)

        var currentDate = conference.startDate
        while (!currentDate.isAfter(conference.endDate)) {
            val schedule = Schedule(
                conference = conference,
                startDate = currentDate
            )
            conference.schedules.add(schedule) // Add schedule to the conference's schedule list
            currentDate = currentDate.plusDays(1) // Increment the date
        }

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
        if (principal is CustomUserDetails) {
            val userId = principal.getId()
            model.addAttribute("userId", userId)
        }
        model.addAttribute("conference", ConferenceDto())
        return "conference/create-conference"
    }

    @PutMapping("/edit/{id}")
    fun updateConference(@PathVariable id:Long, @Valid @ModelAttribute("conference") conferenceDTO: ConferenceDto,
                         bindingResult: BindingResult, model: Model
    ): String {
        if(bindingResult.hasErrors()){
            model.addAttribute("categories", ConferenceCategory.entries.toTypedArray())
            model.addAttribute("countries", Country.entries.toTypedArray())
            model.addAttribute("conference", conferenceDTO)
            return "conference/edit-conference"
        }
        val organizer = conferenceService.findById(id).organizer
        val conference = conferenceService.toEntity(conferenceDTO, organizer)
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

        var userId: Long? = null
        var isRegistered = false

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.isAuthenticated &&
            authentication.principal is CustomUserDetails) {
            val principal = authentication.principal as CustomUserDetails
            userId = principal.getId()
            isRegistered = registrationService.isUserRegistered(conference.id, userId)
        }


        model.addAttribute("userId", userId)
        model.addAttribute("isRegistered", isRegistered)

        val schedules = conference.schedules
        model.addAttribute("schedules", schedules)

        return "conference/conference-details"
    }

    @GetMapping("/edit/{id}")
    fun getEditConferencePage(@PathVariable id: Long, model: Model): String {
        val conference = conferenceService.findById(id)
        val conferenceDTO = conferenceService.toDto(conference)
        val category = conference.category
        model.addAttribute("category", category)
        val country = conference.country
        model.addAttribute("country", country)
        model.addAttribute("conference", conferenceDTO)
        model.addAttribute("categories", ConferenceCategory.entries.toTypedArray())
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
        return "conference/conferences";
    }

}