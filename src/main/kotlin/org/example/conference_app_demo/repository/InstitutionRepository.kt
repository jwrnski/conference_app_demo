package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Institution
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstitutionRepository : JpaRepository<Institution, Long> {
}