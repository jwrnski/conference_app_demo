package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Submission
import org.example.conference_app_demo.service.SubmissionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/submissions")
class SubmissionController(private val submissionService: SubmissionService) {

    @GetMapping
    fun getAllSubmissions(): ResponseEntity<List<Submission>> {
        return ResponseEntity.ok(submissionService.findAll())
    }

    @GetMapping("/{id}")
    fun getSubmissionById(@PathVariable id: Long): ResponseEntity<Submission> {
        return ResponseEntity.ok(submissionService.findById(id))
    }

    @PostMapping
    fun createSubmission(@RequestBody submission: Submission): ResponseEntity<Submission> {
        val savedSubmission = submissionService.save(submission)
        return ResponseEntity.status(201).body(savedSubmission)
    }

    @DeleteMapping("/{id}")
    fun deleteSubmissionById(@PathVariable id: Long): ResponseEntity<Void> {
        submissionService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun updateSubmission(@PathVariable id: Long, @RequestBody submission: Submission): ResponseEntity<Submission> {
        val updatedSubmission = submissionService.update(id, submission);
        return ResponseEntity.ok(updatedSubmission);
    }
}