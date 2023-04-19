package com.example.tele2education.data

import android.content.Context

class PreferencesManager(private val context: Context) {

    fun getRegisterStatus() =
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).getBoolean(USER_FINISHED_REGISTER, false)

    fun saveRegisteredStatus(status: Boolean) =
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).edit().putBoolean(USER_FINISHED_REGISTER, status).apply()

    companion object {
        private const val SAVEFILE = "tele2_edu"
        private const val USER_FINISHED_REGISTER = "user_finished_reg"
    }
}