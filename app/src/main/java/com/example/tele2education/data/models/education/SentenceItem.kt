package com.example.tele2education.data.models.education

data class SentenceItem(
    val answer: String = "", // Вопрос
    val correctSentence: String = "", // Правильно составленная последовательность
    val words: ArrayList<String> = arrayListOf() // Рандомная последовательность слов
)
