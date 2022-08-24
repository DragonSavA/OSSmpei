package ru.acediat.ossmpei

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.messaging.subscribeToTopic("tasks").addOnCompleteListener { task -> }
    }
}