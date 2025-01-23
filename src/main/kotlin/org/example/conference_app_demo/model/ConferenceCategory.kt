package org.example.conference_app_demo.model

enum class ConferenceCategory {
    COMPUTER_SCIENCE {
        override fun toString() = "Computer Science"
    },
    ELECTRONICS_AND_COMMUNICATION {
        override fun toString() = "Electronics and Communication"
    },
    MECHANICAL_ENGINEERING {
        override fun toString() = "Mechanical Engineering"
    },
    ELECTRICAL_ENGINEERING {
        override fun toString() = "Electrical Engineering"
    };
}