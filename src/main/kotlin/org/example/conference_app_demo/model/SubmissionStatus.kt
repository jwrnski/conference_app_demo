package org.example.conference_app_demo.model

enum class SubmissionStatus {
    PENDING{
        override fun toString() = "Pending"
    },
    APPROVED{
        override fun toString() = "Approved"
    },
    REJECTED{
        override fun toString() = "Rejected"
    };
}