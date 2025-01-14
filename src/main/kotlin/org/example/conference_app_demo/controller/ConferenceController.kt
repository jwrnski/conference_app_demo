package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.service.ConferenceService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/conferences")
class ConferenceController(private val conferenceService: ConferenceService) {

    @PostMapping
    fun createConference(@RequestBody conference: Conference): ResponseEntity<Conference> {
        val savedConference = conferenceService.save(conference);
        return ResponseEntity.status(201).body(savedConference);
    }

    @PutMapping("/{id}")
    fun updateConference(@PathVariable id:Long, @RequestBody conference: Conference): ResponseEntity<Conference> {
        val updatedConference = conferenceService.update(conference, id);
        return ResponseEntity.ok(updatedConference);
    }

    @GetMapping("/{id}")
    fun getConferenceById(@PathVariable id: Long): ResponseEntity<Conference> {
        val conference = conferenceService.findById(id);
        return ResponseEntity.ok(conference);
    }

    @GetMapping()
    fun getAllConferences(model: Model): String {
        val conferences = conferenceService.findAll();
        model.addAttribute("conferences", conferences);
        return "conferences";
    }

    @DeleteMapping("/{id}")
    fun deleteConferenceById(@PathVariable id: Long): ResponseEntity<Void> {
        conferenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}