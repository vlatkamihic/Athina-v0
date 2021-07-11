package com.example.athina.database_notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Notification {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "notificationTitle")
    public String nTitle;

    @ColumnInfo(name = "notificationDescription")
    public String nDescription;

    @ColumnInfo(name = "date")
    public Date nDate;

    @ColumnInfo(name = "time")
    public Time nTime;

}
