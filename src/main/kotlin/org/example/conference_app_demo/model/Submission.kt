package org.example.conference_app_demo.model
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Submission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var abstractTitle: String,
    var content: String,
    var author: String,
    var conference: String,
    var topic: String,
    var status: String,
    var rating: Int,
    var votes: Int,
    var comments: Int,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
