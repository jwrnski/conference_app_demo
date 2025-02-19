package org.example.conference_app_demo.model
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "conferences")
class Conference(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @NotBlank(message = "Name can't be blank")
    var name:        String,
    @NotBlank(message = "City can't be blank")
    var city:        String,
    @NotNull(message = "Country can't be blank")
    var country:     Country,
    @NotBlank(message = "Description can't be blank")
    var description: String,
    @field:NotNull(message = "Please select start date")
    @field:FutureOrPresent(message = "Start date must be today or in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    var startDate: LocalDate,
    @field:NotNull(message = "Please select end date")
    @field:FutureOrPresent(message = "End date must be today or in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    var endDate:     LocalDate,

    @NotNull(message = "Please select category")
    @Enumerated(EnumType.STRING)
    var category:    ConferenceCategory,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var organizer: User,

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
