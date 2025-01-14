package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.model.Presentation
import org.example.conference_app_demo.service.PresentationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/presentations")
class PresentationController(private val presentationService: PresentationService) {

    @GetMapping
    fun getAllPresentations(): ResponseEntity<List<Presentation>> {
        val presentations = presentationService.findAll();
        return ResponseEntity.ok(presentations);
    }

    @GetMapping("/{id}")
    fun getPresentationById(@PathVariable id: Long): ResponseEntity<Presentation> {
        val presentation = presentationService.findById(id);
        return ResponseEntity.ok(presentation);
    }

    @PostMapping
    fun createPresentation(@RequestBody presentation: Presentation): ResponseEntity<Presentation> {
        val savedPresentation = presentationService.save(presentation);
        return ResponseEntity.status(201).body(savedPresentation);
    }

    @DeleteMapping("/{id}")
    fun deletePresentationById(@PathVariable id: Long): ResponseEntity<Presentation> {
        presentationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    fun updatePresentation(@PathVariable id: Long, @RequestBody presentation: Presentation): ResponseEntity<Presentation>{
        val updatedPresentation = presentationService.update(id, presentation);
        return ResponseEntity.ok(updatedPresentation);
    }
}