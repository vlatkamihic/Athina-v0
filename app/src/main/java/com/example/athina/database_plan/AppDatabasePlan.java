package com.example.athina.database_plan;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Plan.class}, version = 1)
public abstract class AppDatabasePlan extends RoomDatabase {

    public abstract PlanDao planDao();

    private static AppDatabasePlan INSTANCE;

    public static AppDatabasePlan getDBInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabasePlan.class, "DataBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
