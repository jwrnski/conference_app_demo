package org.example.conference_app_demo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String,
    var surname: String,
    var title: String,
    var email: String,
    var password: String,
    var phone: String,
    var institution: String,
    var role: Role,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

)
