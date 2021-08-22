package com.example.athina.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.athina.R;

import static android.graphics.Color.rgb;

public class NotificationBroadcast extends BroadcastReceiver {

    private static final String NOTIFICATION_CHANNEL_ID = "10001";

    public void onReceive(Context context, Intent intent) {

        int notificationId = intent.getIntExtra("notificationId", 0);
        String nDescription = intent.getStringExtra("todo");
        String nTitle = intent.getStringExtra("title");

        Intent mainIntent = new Intent(context, Notifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationChannel")
                .setContentTitle(nTitle)
                .setContentText(nDescription)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_athina_foreground)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(nDescription))
                .setContentIntent(pendingIntent)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setColor(rgb(237, 234, 238));
        Notification notification = builder.build();
        NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
            notifManager.createNotificationChannel(channel);
        }
        notifManager.notify(notificationId, notification);
    }
}