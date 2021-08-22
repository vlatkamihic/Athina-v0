package com.example.athina.ui.notifications;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athina.R;
import com.example.athina.database_notifications.AppDatabaseNotifications;
import com.example.athina.database_notifications.Notification;

import java.util.Calendar;

public class AddNotification extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    private boolean isTimeSet;
    private boolean isDateSet;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        EditText nName = (EditText) findViewById(R.id.editTextTitleNotif);
        EditText nDescription = (EditText) findViewById(R.id.et_description_add);

        isDateSet = false;
        isTimeSet = false;

        Button nDate = (Button) findViewById(R.id.button_add_date);
        nDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                isDateSet = true;
            }

        });

        Button nTime = (Button) findViewById(R.id.button_time_add);
        nTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
                isTimeSet = true;
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveFeature);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nName.getText().toString().isEmpty() || nDescription.getText().toString().isEmpty() || !isDateSet || !isTimeSet) {
                    Toast.makeText(getApplicationContext(), "Cannot add empty plan!", Toast.LENGTH_SHORT).show();
                } else {
                    saveAndScheduleNotification(nName.getText().toString(), nDescription.getText().toString());
                    finish();
                }
            }
        });

    }

    private int saveAndScheduleNotification(String nName, String nDescription) {
        AppDatabaseNotifications database = AppDatabaseNotifications.getDBInstance(this.getApplicationContext());

        Notification notification = new Notification();
        notification.nTitle = nName;

        long alarmTime = getDateTimeFromSelectedValues();
        notification.nAlarmId = (int) alarmTime;

        notification.nDescription = nDescription;

        notification.nYear = year;
        notification.nMonth = month;
        notification.nDay = day;
        notification.nHour = hour;
        notification.nMinute = minute;

        database.notificationDao().insertNotification(notification);
        addAlarm(alarmTime, notification.nTitle, notification.nDescription);
        Toast.makeText(getApplicationContext(), "New notification was made!", Toast.LENGTH_SHORT).show();
        return notification.uid;
    }

    public long getDateTimeFromSelectedValues() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        Log.d("NOTIFICATION", "addAlarm: day: " + day);
        Log.d("NOTIFICATION", "addAlarm: month: " + month);
        Log.d("NOTIFICATION", "addAlarm: year: " + year);
        Log.d("NOTIFICATION", "addAlarm: hour: " + hour);
        Log.d("NOTIFICATION", "addAlarm: minute: " + minute);
        return cal.getTimeInMillis();
    }


    private void addAlarm(long timeToSet, String name, String description) {
        long alarmTime = 0L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeToSet);
        alarmTime = calendar.getTimeInMillis();
        Log.d("NOTIFICATION", "addAlarm: alarm will trigger in: " + calendar.getTime());
        PendingIntent alarmIntent = getAlarmIntent(alarmTime, description, name);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, alarmTime, alarmIntent);
    }

    private PendingIntent getAlarmIntent(long alarmId, String description, String name) {
        Intent intent = new Intent(getApplicationContext(), NotificationBroadcast.class);
        intent.putExtra("todo", description);
        intent.putExtra("title", name);
        intent.setAction("notification_action");
        return PendingIntent.getBroadcast(getApplicationContext(), (int) alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void showDatePickerDialog() {
        datePickerDialog = new DatePickerDialog(
                this,
                R.style.DialogTheme,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void showTimePickerDialog() {
        timePickerDialog = new TimePickerDialog(
                this,
                R.style.DialogTheme,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}