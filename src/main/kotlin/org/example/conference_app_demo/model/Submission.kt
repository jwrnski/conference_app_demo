package org.example.conference_app_demo.model
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "submissions")
class Submission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    var paperTitle: String,
    var abstract: String,
    var filePath: String,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var author: MutableList<User> = mutableListOf(),
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", nullable = false)
    var conference: Conference? = null,
    var category: String,
    var status: SubmissionStatus = SubmissionStatus.PENDING,
    var comments: String? = null,

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
