package org.example.conference_app_demo.model
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "conferences")
class Conference(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name:        String,
    var city:        String,
    var country:     Country,
    var description: String,
    @JsonFormat(pattern = "dd-MM-yyyy")
    var startDate: LocalDate,
    @JsonFormat(pattern = "dd-MM-yyyy")
    var endDate:     LocalDate,

    @Enumerated(EnumType.STRING)
    var category:    ConferenceCategory,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var organizer: User? = null,

    @OneToMany(mappedBy = "conference", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var registrations: MutableList<Registration> = mutableListOf(),


    @OneToMany(mappedBy = "conference", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var schedules: MutableList<Schedule> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
