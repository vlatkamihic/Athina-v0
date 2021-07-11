package com.example.athina.database_notifications;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM `Notification`")
    List<Notification> getAllNotifications();

    @Insert
    void insertNotification(Notification... notifications);

    @Delete
    void delete(Notification notification);
}
