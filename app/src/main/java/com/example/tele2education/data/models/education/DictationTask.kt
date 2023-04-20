package com.example.tele2education.data.models.education

data class DictationTask(
    var type: String = "dictation",
    val correctWords: ArrayList<DictationWord> = arrayListOf(),
    val words: ArrayList<String> = arrayListOf(),
)
