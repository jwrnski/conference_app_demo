package org.example.conference_app_demo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Institution(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String,
    var country: String,
    var city: String,
    var logo: String,
    var url: String,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
