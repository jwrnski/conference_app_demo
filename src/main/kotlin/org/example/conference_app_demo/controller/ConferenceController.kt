package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.model.ConferenceCategory
import org.example.conference_app_demo.model.Country
import org.example.conference_app_demo.service.ConferenceService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/conferences")
class ConferenceController(private val conferenceService: ConferenceService) {

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