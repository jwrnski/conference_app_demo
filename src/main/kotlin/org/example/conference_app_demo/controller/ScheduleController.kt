package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Schedule
import org.example.conference_app_demo.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {

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