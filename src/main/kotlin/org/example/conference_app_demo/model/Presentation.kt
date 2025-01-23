package org.example.conference_app_demo.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "presentations")
class Presentation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var title: String,
    var description: String,
    var startTime: LocalTime,
    var endTime: LocalTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    var conference: Conference,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    var schedule: Schedule,

    @OneToOne(fetch = FetchType.LAZY)
    var submission: Submission,

    @ManyToMany
    @JoinTable(
        name = "presentation_user",
        joinColumns = [JoinColumn(name = "presentation_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var authors: MutableList<User> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()

)
