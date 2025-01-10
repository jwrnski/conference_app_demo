package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Institution
import org.example.conference_app_demo.service.InstitutionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/institutions")
class InstitutionController(private val institutionService: InstitutionService) {

    @GetMapping
    fun getAllInstitutions(): ResponseEntity<List<Institution>> {
        val institutions = institutionService.findAll();
        return ResponseEntity.ok(institutions);
    }

    @GetMapping("/{id}")
    fun getInstitutionById(@PathVariable id: Long): ResponseEntity<Institution> {
        val institution = institutionService.findById(id);
        return ResponseEntity.ok(institution);
    }

    @PostMapping("/")
    fun saveInstitution(@RequestBody institution: Institution): ResponseEntity<Institution> {
        val savedInstitution = institutionService.save(institution);
        return ResponseEntity.status(201).body(savedInstitution);
    }

    @DeleteMapping("/{id}")
    fun deleteInstitutionById(@PathVariable id: Long): ResponseEntity<Void> {
        institutionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    fun updateInstitution(@PathVariable id: Long, @RequestBody institution: Institution): ResponseEntity<Institution>{
        val updatedInstitution = institutionService.update(id, institution);
        return ResponseEntity.ok(updatedInstitution);
    }
}