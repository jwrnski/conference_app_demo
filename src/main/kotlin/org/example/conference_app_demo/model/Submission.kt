package org.example.conference_app_demo.model
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "submissions")
class Submission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @NotBlank(message = "Paper title is required!")
    var paperTitle: String,
    @NotBlank(message = "Paper abstract is required!")
    var abstract: String,
    @NotBlank(message = "Select a file!")
    var filePath: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var authors: MutableList<User> = mutableListOf(),

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", nullable = false)
    var conference: Conference? = null,

    @NotBlank(message = "Select at least one topic!")
    @ManyToMany
    @JoinTable(
        name = "submission_topics",
        joinColumns = [JoinColumn(name = "submission_id")],
        inverseJoinColumns = [JoinColumn(name = "topic_id")]
    )
    var topics: MutableList<Topic> = mutableListOf(),

    var status: SubmissionStatus = SubmissionStatus.PENDING,

    var comments: String? = null,

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
