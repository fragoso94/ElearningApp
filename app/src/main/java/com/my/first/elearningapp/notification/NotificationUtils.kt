package com.my.first.elearningapp.notification

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.my.first.elearningapp.R
import com.my.first.elearningapp.home.HomeActivity
import com.my.first.elearningapp.notification.*
import com.my.first.elearningapp.notification.NotificationApp.Companion.CHANNEL_ID

// el nombre de la acción a ejecutar por el botón en la notificación
const val ACTION_RECEIVED = "action_received"
private val GRUPO_SIMPLE = "GRUPO_SIMPLE"

@SuppressLint("MissingPermission")
fun simpleNotification(context: Context) {
    with(context) {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID) //CHANNEL_ID
                .setSmallIcon(R.drawable.triforce)
                .setColor(getColor(R.color.triforce))
                .setContentTitle(getString(R.string.simple_title))
                .setContentText(getString(R.string.simple_body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        } else {
            NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID) //CHANNEL_ID
                .setSmallIcon(R.drawable.triforce)
                .setContentTitle(getString(R.string.simple_title))
                .setContentTitle(getString(R.string.simple_title))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        }

        NotificationManagerCompat.from(this).run {
            notify(20, builder.build())
        }
    }
}

fun simpleNotificationGroup(context: Context) {
    with(context) {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            simpleNotificationBuilder(context, R.string.simple_title, R.string.action_body)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val builder2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            simpleNotificationBuilder(context, R.string.simple_title_2, R.string.action_body)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val builder3 = simpleNotificationBuilder(context, R.string.simple_title_3, R.string.action_body)

        val sumaryNotification = NotificationCompat.Builder(context, NotificationApp.CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setGroup(GRUPO_SIMPLE)
            .setGroupSummary(true)
            .build()


        NotificationManagerCompat.from(this).run {
            notify(20, builder.build())
            notify(21, builder2.build())
            notify(22, builder3.build())
            notify(23, sumaryNotification)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
private fun simpleNotificationBuilder(context: Context, titleId: Int, contentId: Int) =
    NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.triforce)
        .setColor(context.getColor(R.color.triforce))
        .setContentTitle(context.getString(titleId))
        .setContentText(context.getString(contentId))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("MissingPermission")
fun touchNotification(activity: Activity) {
    with(activity) {
        val intent = Intent(activity, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            activity,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setColor(getColor(R.color.triforce))
            .setContentTitle(getString(R.string.action_title))
            .setContentText(getString(R.string.action_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).run {
            notify(20, builder.build())
        }
    }
}

@SuppressLint("MissingPermission")
fun buttonNotification(context: Context) {

    //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
    val acceptIntent = Intent(context, NotificationReceiver::class.java).apply {
        action = ACTION_RECEIVED
    }
    //creamos un PendingIntent que describe el pending anterior
    val acceptPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, 0, acceptIntent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.bedu_icon)
        .setContentTitle(context.getString(R.string.button_title))
        .setContentText(context.getString(R.string.button_body))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(R.drawable.bedu_icon, context.getString(R.string.button_text), // agregamos la acción
            acceptPendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(20, builder.build())
    }
}

@SuppressLint("MissingPermission")
fun expandableNotification(context: Context) {
    with(context) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.large_text))
            .setLargeIcon(getDrawable(R.drawable.bedu_icon)?.toBitmap()) // ícono grande a la derecha
            .setStyle(
                NotificationCompat.BigTextStyle() // este estilo define al expandible
                .bigText(getString(R.string.large_text)))
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(50, notification)
        }
    }
}

@SuppressLint("MissingPermission")
fun customNotification(context: Context) {

    with(context) {
        // obtenemos los layouts por medio de RemoteViews
        val notificationLayout = RemoteViews(packageName, R.layout.notification_custom)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_custom_expanded)


        var notification = NotificationCompat.Builder(this, CHANNEL_ID) //CHANNEL_OTHERS
            .setSmallIcon(R.drawable.bedu_icon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) //este estilo define que es personalizable
            .setCustomContentView(notificationLayout) //contenido en modo colapsado
            .setCustomBigContentView(notificationLayoutExpanded) //contenido en modo expandido
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(60, notification)
        }
    }
}