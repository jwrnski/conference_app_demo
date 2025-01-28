package org.example.conference_app_demo

import org.example.conference_app_demo.model.ConferenceCategory
import org.example.conference_app_demo.model.Institution
import org.example.conference_app_demo.model.Topic
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.InstitutionRepository
import org.example.conference_app_demo.repository.TopicRepository
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ConferenceAppDemoApplication

fun main(args: Array<String>) {
    runApplication<ConferenceAppDemoApplication>(*args)
}
@Component
class DatabaseInitializer(private val topicRepository: TopicRepository,
                          private val institutionRepository: InstitutionRepository,
                          private val userRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val topics = listOf(
            // Computer Science Topics
            Topic(name = "Artificial Intelligence", conferenceCategory = ConferenceCategory.COMPUTER_SCIENCE),
            Topic(name = "Machine Learning", conferenceCategory = ConferenceCategory.COMPUTER_SCIENCE),
            Topic(name = "Data Science", conferenceCategory = ConferenceCategory.COMPUTER_SCIENCE),
            Topic(name = "Software Engineering", conferenceCategory = ConferenceCategory.COMPUTER_SCIENCE),
            Topic(name = "Cybersecurity & Networking", conferenceCategory = ConferenceCategory.COMPUTER_SCIENCE),

            // Electronics and Communication Topics
            Topic(name = "Wireless Communication", conferenceCategory = ConferenceCategory.ELECTRONICS_AND_COMMUNICATION),
            Topic(name = "Signal Processing", conferenceCategory = ConferenceCategory.ELECTRONICS_AND_COMMUNICATION),
            Topic(name = "Internet of Things (IoT)", conferenceCategory = ConferenceCategory.ELECTRONICS_AND_COMMUNICATION),
            Topic(name = "VLSI Design", conferenceCategory = ConferenceCategory.ELECTRONICS_AND_COMMUNICATION),
            Topic(name = "Digital Circuits", conferenceCategory = ConferenceCategory.ELECTRONICS_AND_COMMUNICATION),

            // Mechanical Engineering Topics
            Topic(name = "Robotics", conferenceCategory = ConferenceCategory.MECHANICAL_ENGINEERING),
            Topic(name = "Fluid Dynamics", conferenceCategory = ConferenceCategory.MECHANICAL_ENGINEERING),
            Topic(name = "Thermodynamics", conferenceCategory = ConferenceCategory.MECHANICAL_ENGINEERING),
            Topic(name = "CAD/CAM (Computer-Aided Design/Manufacturing)", conferenceCategory = ConferenceCategory.MECHANICAL_ENGINEERING),
            Topic(name = "Renewable Energy Systems", conferenceCategory = ConferenceCategory.MECHANICAL_ENGINEERING),

            // Electrical Engineering Topics
            Topic(name = "Power Systems", conferenceCategory = ConferenceCategory.ELECTRICAL_ENGINEERING),
            Topic(name = "Microcontrollers and Embedded Systems", conferenceCategory = ConferenceCategory.ELECTRICAL_ENGINEERING),
            Topic(name = "Electric Vehicles & Storage", conferenceCategory = ConferenceCategory.ELECTRICAL_ENGINEERING),
            Topic(name = "Control Systems", conferenceCategory = ConferenceCategory.ELECTRICAL_ENGINEERING),
            Topic(name = "Renewable Energy Systems", conferenceCategory = ConferenceCategory.ELECTRICAL_ENGINEERING)
        )

        // Save topics to the database if they do not already exist
        topics.forEach { topic ->
            if (!topicRepository.existsByName(topic.name)) {
                topicRepository.save(topic)
            }
        }


        val institutions = listOf(
            Institution(name = "Uniwersytet Morski w Gdyni", country = "Poland", city = "Gdynia", url = "https://umg.edu.pl"),
            Institution(name = "Politechnika Gdańska", country = "Poland", city = "Gdańsk", url = "https://pg.edu.pl/"),
            Institution(name = "Politechnika Warszawska", country = "Poland", city = "Warszawa", url = "https://www.pw.edu.pl/"))
        institutions.forEach{
            institution ->
            if(!institutionRepository.existsByName(institution.name)) {
                institutionRepository.save(institution)
            }
        }

    }
}

