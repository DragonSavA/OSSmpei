package ru.acediat.ossmpei

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.yandex.mapkit.MapKitFactory
import ru.acediat.core_android.APP_PREFERENCES
import ru.acediat.core_android.NOTIFICATION_ENABLED
import ru.acediat.core_android.TASKS_NOTIFICATION
import ru.acediat.core_android.YANDEX_MAP_API_KEY

class App: Application() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(YANDEX_MAP_API_KEY)
        MapKitFactory.initialize(this)
        preferences = applicationContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val isNotificationEnabled = preferences.getBoolean(NOTIFICATION_ENABLED, true)
        if(isNotificationEnabled) {
            Firebase.messaging.subscribeToTopic(TASKS_NOTIFICATION)
                .addOnCompleteListener { task -> }
        }else {
            Firebase.messaging.unsubscribeFromTopic(TASKS_NOTIFICATION)
        }
    }
}