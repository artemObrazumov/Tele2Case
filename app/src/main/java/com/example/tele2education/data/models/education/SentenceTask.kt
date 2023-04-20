package com.example.tele2education.data.models.education

data class SentenceTask(
    val type: String = "sentence",
    val sentences: ArrayList<SentenceItem> = arrayListOf()
)
