package com.example.athina.database_notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Notification {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "notificationTitle")
    public String nTitle;

    @ColumnInfo(name = "alarmId")
    public int nAlarmId;

    @ColumnInfo(name = "notificationDescription")
    public String nDescription;

    @ColumnInfo(name = "year")
    public int nYear;

    @ColumnInfo(name = "month")
    public int nMonth;

    @ColumnInfo(name = "day")
    public int nDay;

    @ColumnInfo(name = "hour")
    public int nHour;

    @ColumnInfo(name = "minute")
    public int nMinute;

}
