package com.renattele.sma1.domain

import java.util.UUID

data class SurveyQuestion(
    val id: String = UUID.randomUUID().toString(),
    val question: String,
    val answers: List<String>,
    val selectedAnswer: Int? = null,
)