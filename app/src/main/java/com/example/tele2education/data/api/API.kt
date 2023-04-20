package com.example.tele2education.data.api

import android.util.Log
import com.example.tele2education.data.models.AuthResult
import com.example.tele2education.data.models.education.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class API() {

    suspend fun signInWithEmail(email: String, password: String, callback: (AuthResult) -> Unit){
        //signin in
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = FirebaseAuth.getInstance().currentUser
                callback(AuthResult(true, user!!, ""))
            }else{
                Log.d("FirebaseAuthSignIn", task.exception.toString())
                callback(AuthResult(false, null, task.exception.toString()))
            }
        }
    }

    suspend fun signUpWithEmail(email: String, password: String, callback: (AuthResult) -> Unit){
        //signin up
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = FirebaseAuth.getInstance().currentUser
                callback(AuthResult(true, user!!, ""))
            }else{
                Log.d("FirebaseAuthSignUp", task.exception.toString())
                callback(AuthResult(false, null, task.exception.toString()))
            }
        }
    }

    suspend fun resetPassword(email: String, callback: (AuthResult) -> Unit){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback(AuthResult(true, null, ""))
            }else{
                callback(AuthResult(false, null, task.exception.toString()))
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    // Получаем список предметов для нужного класса
    suspend fun getEducationItems(form: Int): ArrayList<Education> {

        val arrData: ArrayList<Education> = arrayListOf()

        val data = Firebase.database.reference.child("education")
            .child("form$form").get().await()

        data.children.forEach {
            arrData.add(Education(it.key.toString(), it.child("name").value.toString(), it.child("srcImg").value.toString()))
        }

        return arrData
    }

    // Получаем список доступных уроков для предмета
    suspend fun getEducationLessonItems(form: Int, id: String): ArrayList<EducationLesson> {
        val arr: ArrayList<EducationLesson> = arrayListOf()
        val data = Firebase.database.reference.child("education/form$form/$id/lessons").get().await()
        data.children.forEach {
            arr.add(EducationLesson(it.key!!, it.child("name").value.toString(), it.child("srcImg").value.toString()))
        }

        return arr

    }

    suspend fun getInteractiveEducationItems(form: Int, id: String, id_lesson: String): ArrayList<Any>{
        val items: ArrayList<Any> = arrayListOf()
        val data = Firebase.database.reference.child("education/form$form/$id/lessons/$id_lesson/education_items").get().await()
        data.children.forEach {
            when (it.child("type").value) {
                "theory"    -> items.add(it.getValue(Theory::class.java)!!)
                "test"      -> items.add(it.getValue(TestTask::class.java)!!)
                "dictation" -> items.add(it.getValue(DictationTask::class.java)!!)
                else        -> items.add(it.getValue(SentenceTask::class.java)!!)
            }
        }
        return items
    }


}