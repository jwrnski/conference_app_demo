package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.InstitutionDTO
import org.example.conference_app_demo.model.Institution
import org.example.conference_app_demo.repository.InstitutionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InstitutionService(private val institutionRepository: InstitutionRepository) {


    fun save(institution: Institution): Institution {
        return institutionRepository.save(institution)
    }

    fun findAll(): List<Institution> {
        return institutionRepository.findAll()
    }

    fun findById(id: Long): Institution{
        return institutionRepository.findById(id).orElseThrow { Exception("Institution with id $id not found") }
    }

    fun deleteById(id: Long) {
        if(institutionRepository.findById(id).isEmpty) throw Exception()
        institutionRepository.deleteById(id)
    }

    fun update(id: Long, institution: Institution): Institution {
        val existingInstitution = findById(id) ?: throw Exception("Institution with id $id not found")
        existingInstitution.name = institution.name
        existingInstitution.country = institution.country
        existingInstitution.city = institution.city
        existingInstitution.logo = institution.logo
        existingInstitution.url = institution.url
        existingInstitution.updatedAt = LocalDateTime.now()

        return institutionRepository.save(existingInstitution)
    }


    /*fun toDTO(institution: Institution): InstitutionDTO {
        return InstitutionDTO(
            id = institution.id,
            name = institution.name,
            country = institution.country,
            city = institution.city,
            logo = institution.logo,
            url = institution.url,
            createdAt = institution.createdAt,
            updatedAt = institution.updatedAt
        )
    }

    fun toEntity(dto: InstitutionDTO): Institution {
        return Institution(
            id = dto.id,
            name = dto.name,
            country = dto.country,
            city = dto.city,
            logo = dto.logo,
            url = dto.url,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }*/
}