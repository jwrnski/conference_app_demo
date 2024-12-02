package org.example.conference_app_demo.model
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Conference(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name:        String,
    var city:        String,
    var url:         String,
    var description: String,
    var startDate:   LocalDateTime,
    var endDate:     LocalDateTime,
    var tags:        String,

    @OneToMany(mappedBy = "conference", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var presentations: MutableList<Presentation> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
