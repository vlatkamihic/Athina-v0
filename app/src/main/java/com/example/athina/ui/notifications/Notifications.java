package com.example.athina.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.athina.R;
import com.example.athina.database_notifications.AppDatabaseNotifications;
import com.example.athina.database_notifications.Notification;
import com.example.athina.ui.SimpleItemTouchHelperCallback;

import java.util.List;

public class Notifications extends AppCompatActivity implements NotificationsListAdapter.OnNotifRemoved{

    private NotificationsListAdapter notificationsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button


        Button addNotification = (Button) findViewById(R.id.add_notification_button);
        addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent redirect = new Intent(v.getContext() , AddNotification.class);
                        startActivity(redirect);
            }
        });

        initNotificationRecyclerView();
        loadNotificationList();

    }

    private void loadNotificationList() {
        AppDatabaseNotifications databaseNotifications = AppDatabaseNotifications.getDBInstance(this.getApplicationContext());
        List<Notification> notifications = databaseNotifications.notificationDao().getAllNotifications();
        notificationsListAdapter.setNotificationList(notifications);

    }

    private void initNotificationRecyclerView() {
        RecyclerView notificationRecyclerView = findViewById(R.id.recyclerView_Notifications);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationsListAdapter = new NotificationsListAdapter(this);
        notificationRecyclerView.setAdapter(notificationsListAdapter);

        notificationsListAdapter.setDeleteListener(this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(notificationsListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);

        touchHelper.attachToRecyclerView(notificationRecyclerView);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNotificationList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotificationList();
    }

    @Override
    public void onNotifRemoved(Notification notification) {
        deleteNotification(notification);
    }

    private void deleteNotification(Notification notification) {
        AppDatabaseNotifications databaseNotifications = AppDatabaseNotifications.getDBInstance(getApplicationContext());

        databaseNotifications.notificationDao().delete(notification);

        /*NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(notification.uid)*/
    }
}