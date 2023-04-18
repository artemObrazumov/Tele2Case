package com.example.tele2education

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tele2education.data.api.API

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        api = API()
        //preferencesManager = PreferencesManager(applicationContext)
    }

    companion object{
        lateinit var instance: App
            private set

        lateinit var api: API
            private set

        /*@SuppressLint("StaticFieldLeak")
        lateinit var preferencesManager: PreferencesManager
            private set*/

        var syncUser: Boolean = false
            private set

        fun onUserSync() {
            syncUser = true
        }
    }
}