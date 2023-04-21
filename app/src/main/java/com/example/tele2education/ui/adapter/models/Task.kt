package com.example.tele2education.ui.adapter.models

import com.example.tele2education.data.models.QuizItem

data class Task(
    var quizTask: QuizItem = QuizItem(),
    var hasChecked: Boolean = false
)
