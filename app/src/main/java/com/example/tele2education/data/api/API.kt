package com.example.tele2education.data.api

import android.util.Log
import com.example.tele2education.data.models.*
import com.example.tele2education.ui.game_preparing.GamePrepareEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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

    suspend fun getQuizs(): ArrayList<Quiz> {
        val quizs = arrayListOf<Quiz>()
        val snapshots = FirebaseDatabase.getInstance().getReference("quizs").get().await()
        snapshots.children.forEach {
            quizs.add(it.getValue(Quiz::class.java)!!)
        }
        return quizs
    }

    fun connectPrepareListener(roomId: String, listener: GamePrepareEventListener) {
        FirebaseDatabase.getInstance().getReference("quiz_rooms/$roomId/participants")
            .addChildEventListener(object: ChildEventListener{

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    listener.onPlayerJoined(snapshot.getValue(QuizParticipant::class.java)!!.userId)
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val participant = snapshot.getValue(QuizParticipant::class.java)!!
                    listener.onPlayerStateChanged(participant.userId, participant.state)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    listener.onPlayerLeft(snapshot.getValue(QuizParticipant::class.java)!!.userId)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    suspend fun createRoom(hostId: String, quizRoom: String, questionCount: Int): String {
        val roomId = FirebaseDatabase.getInstance().getReference("quiz_room")
            .push().key!!
        FirebaseDatabase.getInstance().getReference("quiz_room/$roomId").setValue(
            QuizRoom(roomId,
                listOf(QuizParticipant(hostId, QuizParticipant.STATE_JOINED)),
                createRandomQuizQuestions(quizRoom, questionCount)
            )
        ).await()
        return roomId
    }

    private suspend fun createRandomQuizQuestions(quizRoom: String, questionCount: Int): List<QuizItem> {
        val questionsSnapshots = FirebaseDatabase.getInstance()
            .getReference("quizQuestions/$quizRoom").get().await()
        val questions = arrayListOf<QuizItem>()
        val roomQuestions = arrayListOf<QuizItem>()
        questionsSnapshots.children.forEach {
            questions.add(it.getValue(QuizItem::class.java)!!)
        }
        for (i in 0 until questionCount) {
            val element = questions.random()
            roomQuestions.add(element)
            questions.remove(element)
        }
        return roomQuestions
    }

    suspend fun getUser(id: String): User =
        FirebaseDatabase.getInstance().getReference("users/$id")
            .get().await().getValue(User::class.java)!!

    suspend fun uploadUserData(user: User, onUploadFinished: () -> Unit) {
        FirebaseDatabase.getInstance().getReference("users/${user.id}")
            .setValue(user).await()
        onUploadFinished
    }

}