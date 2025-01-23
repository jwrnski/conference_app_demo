package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Schedule
import org.example.conference_app_demo.repository.PresentationRepository
import org.example.conference_app_demo.service.ConferenceService
import org.example.conference_app_demo.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/schedules")
class ScheduleController(
    private val scheduleService: ScheduleService,
    private val conferenceService: ConferenceService,
    private val presentationRepository: PresentationRepository
) {

    @GetMapping
    fun getAllSchedules(): ResponseEntity<List<Schedule>> {
        val schedules = scheduleService.findAll();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    fun getScheduleById(@PathVariable id: Long): ResponseEntity<Schedule> {
        val schedule = scheduleService.findById(id);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/create-schedule")
    fun createSchedulePage(@RequestParam conferenceId: Long, model: Model): String {
        val conference = conferenceService.findById(conferenceId);
        model.addAttribute("conference", conference);
        val presentations = presentationRepository.findByConferenceId(conferenceId)
        model.addAttribute("presentations", presentations);
        return "schedule/create-schedule"
    }

    @PostMapping
    fun createSchedule(@RequestBody schedule: Schedule): ResponseEntity<Schedule> {
        val savedSchedule = scheduleService.save(schedule);
        return ResponseEntity.status(201).body(savedSchedule);

    }

    @DeleteMapping("/{id}")
    fun deleteScheduleById(@PathVariable id: Long): ResponseEntity<Void> {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    fun updateSchedule(@PathVariable id: Long, @RequestBody schedule: Schedule): ResponseEntity<Schedule>{
        val updatedSchedule = scheduleService.update(id, schedule);
        return ResponseEntity.ok(updatedSchedule);
    }
}