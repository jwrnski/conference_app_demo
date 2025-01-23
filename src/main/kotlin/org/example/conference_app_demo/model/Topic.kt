package org.example.conference_app_demo.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "topics")
class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    val id: Long = 0,

    var name: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conference_id", nullable = false)
    var conference: Conference,

    @ManyToMany(mappedBy = "topics", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var submissions: MutableList<Submission> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)