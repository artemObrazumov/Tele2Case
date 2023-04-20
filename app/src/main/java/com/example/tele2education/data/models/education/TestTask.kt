package com.example.tele2education.data.models.education

data class TestTask(
    val type: String = "test",
    val test_items: ArrayList<Test> = arrayListOf(),
)
