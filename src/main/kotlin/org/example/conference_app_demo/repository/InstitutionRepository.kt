package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Institution
import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InstitutionRepository : JpaRepository<Institution, Long> {
    override fun <S : Institution?> save(entity: S & Any): S & Any {
        TODO("Not yet implemented")
    }

    override fun <S : Institution?> findAll(example: Example<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<Institution> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }
}