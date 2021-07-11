package com.example.athina.database_notifications;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.athina.database_plan.Plan;

@Database(entities = {Notification.class}, version = 1)
public abstract class AppDatabaseNotifications extends RoomDatabase {

    public abstract NotificationDao notificationDao();

    private static AppDatabaseNotifications INSTANCE;

    public static AppDatabaseNotifications getDBInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseNotifications.class, "DataBaseNotifications").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
