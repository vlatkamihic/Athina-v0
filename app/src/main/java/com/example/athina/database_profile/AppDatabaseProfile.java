package com.example.athina.database_profile;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Feature.class}, version = 1)
public abstract class AppDatabaseProfile extends RoomDatabase {

    public abstract FeatureDao featureDao();

    private static AppDatabaseProfile INSTANCE;

    public static AppDatabaseProfile getDBInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseProfile.class, "DataBaseProfile").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


}
