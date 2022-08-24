package ru.acediat.ossmpei

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.acediat.core_res.R as resR
import java.util.*
import kotlin.random.Random

class FirebaseNotificationService : FirebaseMessagingService() {

    private val SENDER_LOG = "OSS_TAG_SENDER"
    private val CHANNEL_ID = "oss mpei channel"

    private val channel = NotificationChannel(
        CHANNEL_ID, "oss",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        enableVibration(true)
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    }

    override fun onNewToken(token: String) {
        Log.d(SENDER_LOG, "Refreshed token: $token")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(SENDER_LOG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            Log.d(SENDER_LOG, task.result)
        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(SENDER_LOG, "FROM: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(SENDER_LOG, "Message data payload: " + remoteMessage.data)
            sendNotification(remoteMessage.data["title"] ?: "error",remoteMessage.data["description"]?:"error")
        }
    }


    private fun sendNotification(title : String, body : String){
        val builder = NotificationCompat.Builder(baseContext, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(resR.drawable.ic_mpei)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(channel)
        }.notify(Random(Date().time).nextInt(), builder.build())
    }
}