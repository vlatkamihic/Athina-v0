package com.example.athina.ui.notifications;

import static android.graphics.Color.rgb;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

public class NotificationBroadcast extends BroadcastReceiver {

    /*int id;
    String nName;
    String nDescription;
    String dateAndTime;

    public void setValues(String nName, String nDescription, String dateAndTime, int id){

        this.id = id;
        this.dateAndTime = dateAndTime;
        this.nDescription = nDescription;
        this.nName = nName;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, AddNotification.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(AddNotification.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationBuilder");
        builder.setContentTitle(nName);
        builder.setContentText(dateAndTime);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(nDescription));
        builder.setChannelId("NotificationChannel");
        builder.setColor(rgb(237, 234, 238));


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(id, builder.build());
    }*/
    /*
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    public void onReceive (Context context , Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , notification) ;
    }*/
    public void onReceive (Context context , Intent intent) {

        int notificationId = intent.getIntExtra("notificationId", 0);
        String nDescription = intent.getStringExtra("todo");
        String nTitle = intent.getStringExtra("title");

        Intent mainIntent = new Intent(context, AddNotification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationChannel")
                .setContentTitle(nTitle)
                .setContentText(nDescription)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(nDescription))
                .setContentIntent(pendingIntent)
                .setColor(rgb(237, 234, 238));

        myNotificationManager.notify(notificationId, builder.build());
    }

}
