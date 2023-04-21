package com.example.tele2education.data.api

import android.util.Log
import com.example.tele2education.data.models.AuthResult
import com.example.tele2education.data.models.education.*
import com.example.tele2education.App
import com.example.tele2education.data.models.*
import com.example.tele2education.ui.adapter.models.Participant
import com.example.tele2education.ui.adapter.models.ParticipantScore
import com.example.tele2education.ui.adapter.models.Task
import com.example.tele2education.ui.game_preparing.GamePrepareEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar

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

    suspend fun getQuizs(): ArrayList<Quiz> {
        val quizs = arrayListOf<Quiz>()
        val snapshots = FirebaseDatabase.getInstance().getReference("quizs").get().await()
        snapshots.children.forEach {
            quizs.add(it.getValue(Quiz::class.java)!!)
        }
        return quizs
    }

    fun connectPrepareListener(roomId: String, listener: GamePrepareEventListener) {
        FirebaseDatabase.getInstance().getReference("quiz_room/$roomId/participants")
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

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            })

        FirebaseDatabase.getInstance().getReference("quiz_room/$roomId/state")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.getValue(Int::class.java)!! == 1) {
                            listener.onQuizStarted()
                        }
                    } catch (_:Exception) {}
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    suspend fun createRoom(hostId: String, quizRoom: String, questionCount: Int): String {
        val roomId = FirebaseDatabase.getInstance().getReference("quiz_room")
            .push().key!!
        FirebaseDatabase.getInstance().getReference("quiz_room/$roomId").setValue(
            QuizRoom(roomId, quizRoom,
                listOf(QuizParticipant(hostId, QuizParticipant.STATE_JOINED)),
                App.api.getCurrentUser()!!.uid,
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
        onUploadFinished()
    }

    suspend fun getRooms(quizId: String): List<QuizRoom> {
        val roomsSnapshot = FirebaseDatabase.getInstance().getReference("quiz_room")
            .orderByChild("quizId").equalTo(quizId).get().await()
        val rooms = mutableListOf<QuizRoom>()
        roomsSnapshot.children.forEach {
            rooms.add(it.getValue(QuizRoom::class.java)!!)
        }
        return rooms
    }

    suspend fun addToRoom(uid: String, roomId: String) {
        val participants = arrayListOf<QuizParticipant>()
        FirebaseDatabase.getInstance()
            .getReference("quiz_room/$roomId/participants").get().await()
            .children.forEach {
                participants.add(it.getValue(QuizParticipant::class.java)!!)
            }
        participants.add(QuizParticipant(uid))
        FirebaseDatabase.getInstance()
            .getReference("quiz_room/$roomId/participants").setValue(
                participants.toSet().toList()
            )
    }

    suspend fun loadRoomUsers(roomId: String): List<Participant> {
        val participants = arrayListOf<Participant>()
        val participantsSnapshot = FirebaseDatabase.getInstance()
            .getReference("quiz_room/$roomId/participants").get().await()
        participantsSnapshot.children.forEach {
            val participant = it.getValue(QuizParticipant::class.java)!!
            participants.add(
                Participant(FirebaseDatabase.getInstance()
                    .getReference("users/${participant.userId}").get().await()
                    .getValue(User::class.java)!!,
                    participant.state)
            )
        }
        return participants
    }

    suspend fun loadChat(roomId: String): List<Message> {
        val messagesSnapshot = FirebaseDatabase.getInstance()
            .getReference("chats/$roomId").get().await()
        val messages = arrayListOf<Message>()
        messagesSnapshot.children.forEach {
            messages.add(it.getValue(Message::class.java)!!)
        }
        return messages
    }

    fun attachToUpdates(roomId: String, onNewMessage: (message: Message) -> Unit) {
        FirebaseDatabase.getInstance().getReference("chats/$roomId").addChildEventListener(
            object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    onNewMessage(snapshot.getValue(Message::class.java)!!)
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            }
        )
    }

    fun uploadMessage(message: Message) {
        val id = FirebaseDatabase.getInstance().getReference("chats/${message.roomId}")
            .push().key!!
        message.id = id
        FirebaseDatabase.getInstance().getReference("chats/${message.roomId}/$id")
            .setValue(message)
    }

    suspend fun loadTasks(roomId: String): List<Task> {
        val tasksSnapshot = FirebaseDatabase.getInstance()
            .getReference("quiz_room/$roomId/script").get().await()
        val tasks = arrayListOf<Task>()
        tasksSnapshot.children.forEach {
            tasks.add(Task(it.getValue(QuizItem::class.java)!!))
        }
        return tasks
    }

    suspend fun loadBestPlayers(roomId: String): List<ParticipantScore> {
        var participants = arrayListOf<ParticipantScore>()
        FirebaseDatabase.getInstance().getReference("quiz_progress/$roomId").get().await()
            .children.forEach {
                participants.add(
                    ParticipantScore(
                        getUser(it.key!!), it.getValue(Int::class.java)!!
                    )
                )
            }
        return participants.sortedBy { it.score }
    }

    suspend fun saveScoreToBalance(score: Int) {
        val userBalance = getUser(getCurrentUser()!!.uid).balance + score
        FirebaseDatabase.getInstance().getReference("users/${getCurrentUser()!!.uid}/balance")
            .setValue(userBalance)
    }

    suspend fun getQuiz(quizId: String): Quiz =
        FirebaseDatabase.getInstance()
            .getReference("quizs/$quizId")
            .get().await().getValue(Quiz::class.java)!!

}