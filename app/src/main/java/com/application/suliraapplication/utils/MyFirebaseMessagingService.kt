package com.application.suliraapplication.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        var strDeviceToken: String = ""
    }

    override fun onNewToken(deviceToken: String) {
        super.onNewToken(deviceToken)
        Log.d("deviceToken", "------->" + deviceToken + "<-------")
        strDeviceToken = deviceToken

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("onMessageReceived", "balalalal" + message.data)
    }

    /*Send Notification*/
    private fun sendNotification(title: String, body: String) {
        val intent: Intent
        val pendingIntent: PendingIntent
        intent = Intent(this, HomeTabActivity::class.java)
        intent.action = java.lang.Long.toString(System.currentTimeMillis())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val random = Random()
        pendingIntent = PendingIntent.getActivity(this, random.nextInt(10), intent, 0)
        val channelId = getString(R.string.channel_id)
        var notification: Uri? = null
        try {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        Pattern pattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4,5})\\b");
//        StringBuffer sb = new StringBuffer();
//        Matcher m = pattern.matcher(body);
//        while (m.find()) {
//            int cp = Integer.parseInt(m.group(1), 16);
//            String added = cp < 0x10000 ? String.valueOf((char) cp) : new String(new int[]{cp}, 0, 1);
//            m.appendReplacement(sb, added);
//        }
//        m.appendTail(sb);
//        String content = sb.toString();
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(getNotificationIcon())
                .setColor(resources.getColor(R.color.colorPrimaryDark))
                .setContentTitle(title)
                .setAutoCancel(true)

                //.setStyle(Notification.BigTextStyle().bigText(body))
                .setVibrate(longArrayOf(500, 1000))
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                    getApplicationContext().getPackageName() + "/" + R.raw.notification);
            mChannel = NotificationChannel(
                channelId,
                "Sulira Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.lightColor = Color.BLUE
            mChannel.enableLights(true)
            mChannel.setShowBadge(true)
            mChannel.vibrationPattern = longArrayOf(500, 1000)
            mChannel.enableVibration(true)

            // Allow lockscreen playback control
            mChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            //            mChannel.setSound(soundUri, audioAttributes);
            notificationManager?.createNotificationChannel(mChannel)
        }
        assert(notificationManager != null)
        val t = Random()
        val notificationId: Int = t.nextInt(10)
        notificationManager.notify(1, notificationBuilder.build())
    }

    /*Notification Icon*/
    private fun getNotificationIcon(): Int {
        val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (useWhiteIcon) R.drawable.app_icon else R.drawable.app_icon
    }

}