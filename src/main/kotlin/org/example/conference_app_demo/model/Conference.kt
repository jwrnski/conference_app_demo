package org.example.conference_app_demo.model
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "conferences")
class Conference(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name:        String,
    var city:        String,
    var url:         String,
    var description: String,
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var startDate:   LocalDateTime,
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var endDate:     LocalDateTime,
    var tags:        String,

    @OneToMany(mappedBy = "conference", cascade = [CascadeType.ALL], orphanRemoval = true)
    var schedules: MutableList<Schedule> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
