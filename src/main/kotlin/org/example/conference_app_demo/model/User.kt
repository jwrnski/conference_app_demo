package org.example.conference_app_demo.model
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String,
    var surname: String,
    var title: String,
    var email: String,
    var password: String,
    var phone: String,
    var role: Role,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "institution_id", nullable = false)
    var institution: Institution,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var registrations: MutableList<Registration> = mutableListOf(),

    @ManyToMany(mappedBy = "authors")
    var submissions: MutableList<Submission> = mutableListOf(),

    //@ManyToMany(mappedBy = "attendees")
    //var attending: MutableList<Conference> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()

)
