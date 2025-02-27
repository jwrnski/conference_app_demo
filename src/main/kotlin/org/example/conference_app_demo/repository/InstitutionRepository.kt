package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Institution
import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InstitutionRepository : JpaRepository<Institution, Long> {
    fun existsByName(name: String): Boolean
}