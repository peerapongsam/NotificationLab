package io.github.peerapongsam.notificationlab.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.github.peerapongsam.notificationlab.R


/**
 * Created by peerapongsam on 3/14/18.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "MyFirebaseMessagingServ"
        private const val TOPIC_COMMENT_CHANNEL_ID = "topic_comment_channel3"
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived() called with: message = [$message]")
        message?.data?.let {
            showNotification(it)
        }
    }

    private fun showNotification(data: MutableMap<String, String>) {
        val builder = NotificationCompat.Builder(this).apply {
            setContentTitle(data["title"])
            setContentText(data["body"])
            priority = NotificationCompat.PRIORITY_HIGH
            setVibrate(LongArray(0))
            setSmallIcon(R.mipmap.ic_launcher)
            setCategory(NotificationCompat.CATEGORY_CALL)
            if (data["sound"]?.equals("DEFAULT", true) == true) {
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            }

        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel(TOPIC_COMMENT_CHANNEL_ID, TOPIC_COMMENT_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH).apply {
//                description = "channel description"
//                setShowBadge(true)
//                canShowBadge()
//                enableLights(true)
//                lightColor = Color.RED
//                enableVibration(true)
//                vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
//                if (data["sound"]?.equals("DEFAULT", true) == false) {
//                    setSound(null, null)
//                }
//            }.also {
//                manager.createNotificationChannel(it)
//            }
//        }
        manager.notify(0, builder.build())
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Log.d(TAG, "onDeletedMessages() called")
    }
}