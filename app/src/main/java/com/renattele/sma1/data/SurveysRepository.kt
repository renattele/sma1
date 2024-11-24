package com.renattele.sma1.data

import com.renattele.sma1.domain.SurveyQuestion

object SurveysRepository {
    val questions by lazy {
        mutableListOf(
            SurveyQuestion(
                question = "How do you usually spend your weekends?",
                answers = listOf("Relaxing at home", "Exploring outdoors", "Socializing with friends", "Working on personal projects")
            ),
            SurveyQuestion(
                question = "What kind of music do you enjoy the most?",
                answers = listOf("Pop", "Classical", "Rock", "Jazz")
            ),
            SurveyQuestion(
                question = "What is your preferred way of commuting?",
                answers = listOf("Driving", "Public transportation", "Cycling", "Walking")
            ),
            SurveyQuestion(
                question = "How often do you cook at home?",
                answers = listOf("Every day", "A few times a week", "Rarely", "Never")
            ),
            SurveyQuestion(
                question = "What type of movies do you usually watch?",
                answers = listOf("Action", "Romance", "Comedy", "Horror")
            ),
            SurveyQuestion(
                question = "Which activity do you find the most relaxing?",
                answers = listOf("Reading", "Meditating", "Exercising", "Watching TV")
            ),
            SurveyQuestion(
                question = "How do you prefer to celebrate your birthday?",
                answers = listOf("A quiet day with family", "A big party with friends", "A solo trip", "I don't celebrate birthdays")
            ),
            SurveyQuestion(
                question = "What is your favorite season of the year?",
                answers = listOf("Spring", "Summer", "Autumn", "Winter")
            ),
            SurveyQuestion(
                question = "How do you usually spend your vacations?",
                answers = listOf("Traveling abroad", "Visiting family", "Exploring local attractions", "Staying at home and relaxing")
            ),
            SurveyQuestion(
                question = "What do you enjoy most about weekends?",
                answers = listOf("Sleeping in", "Spending time with family", "Catching up on hobbies", "Getting ahead on work")
            ),
            SurveyQuestion(
                question = "Which beverage do you prefer to start your day with?",
                answers = listOf("Coffee", "Tea", "Juice", "Water")
            ),
            SurveyQuestion(
                question = "How often do you attend social events?",
                answers = listOf("Frequently", "Occasionally", "Rarely", "Almost never")
            )
        )
    }
    @Synchronized
    fun update(block: MutableList<SurveyQuestion>.() -> Unit) {
        questions.block()
    }
}