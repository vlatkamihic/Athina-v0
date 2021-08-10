package com.example.athina.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//import android.app.AlarmManager;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.athina.R;
import com.example.athina.database_notifications.AppDatabaseNotifications;
import com.example.athina.database_notifications.Notification;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import static android.graphics.Color.rgb;

public class AddNotification extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private Date tempDate;
    private Time tempTime;

    private boolean isTimeSet;
    private boolean isDateSet;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    //NotificationBroadcast broadcast;

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("NotificationChannel", "NotificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        Button saveButton = (Button) findViewById(R.id.saveFeature);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nName.getText().toString().isEmpty() || nDescription.getText().toString().isEmpty() || !isDateSet || !isTimeSet ){
                    Toast.makeText(getApplicationContext(), "Cannot add empty plan!", Toast.LENGTH_SHORT).show();
                }else{
                    int id = saveNewNotification(nName.getText().toString(), nDescription.getText().toString());

                    Calendar timeFuture = Calendar.getInstance();
                    timeFuture.set(Calendar.YEAR, year);
                    timeFuture.set(Calendar.MONTH, month);
                    timeFuture.set(Calendar.DAY_OF_MONTH, day);
                    timeFuture.set(Calendar.HOUR_OF_DAY, hour);
                    timeFuture.set(Calendar.MINUTE, minute);
                    timeFuture.set(Calendar.SECOND, 0);

                    long diff = timeFuture.getTimeInMillis();

                    scheduleNotification(AddNotification.this, nName.getText().toString(), nDescription.getText().toString(), diff, id);

                    //makeNotification(nName.getText().toString(), nDescription.getText().toString(), id);

                    finish();
                }
            }
        });

    }

    public void scheduleNotification(Context context, String nName, String nDescription, long delay, int notificationId)
    {
        //delay is after how much time(in millis) from current time you want to schedule the notification
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setContentTitle(nName)
                .setContentText(nDescription)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(nDescription))
                .setChannelId( NOTIFICATION_CHANNEL_ID )
                .setColor(rgb(237, 234, 238));

        android.app.Notification notification = builder.build();

        Intent notificationIntent = new Intent( this, NotificationBroadcast. class ) ;
        notificationIntent.putExtra(NotificationBroadcast. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(NotificationBroadcast. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
*/

        Intent intent = new Intent(context, NotificationBroadcast.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("todo", nDescription);
        intent.putExtra("title", nName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);

    }

    /*private void makeNotification(String nName, String nDescription, int id) {


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(tempDate.getYear(), tempDate.getMonth(), tempDate.getDay(),
                tempTime.getHours(), tempTime.getMinutes(), 0);
        long milisdateAndTime = calendar.getTimeInMillis();

        broadcast = new NotificationBroadcast();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("NotificationChannel", "NotificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        String dateAndTime = tempDate.getYear() + "-" + tempDate.getMonth() + "-" + tempDate.getDay() + " " + tempTime.getHours() + ":" + tempTime.getMinutes() + ":00";


        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddNotification.this, "NotificationBuilder");
        builder.setContentTitle(nName);
        builder.setContentText(dateAndTime);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(nDescription));
        builder.setChannelId("NotificationChannel");
        builder.setColor(rgb(237, 234, 238));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AddNotification.this);
        managerCompat.notify(id, builder.build());

    }*/

    private int saveNewNotification(String nName, String nDescription) {
        AppDatabaseNotifications database = AppDatabaseNotifications.getDBInstance(this.getApplicationContext());

        Notification notification = new Notification();
        notification.nTitle = nName;
        notification.nDescription = nDescription;
        notification.nYear = tempDate.getYear();
        notification.nMonth = tempDate.getMonth() + 1;
        notification.nDay = tempDate.getDate();
        notification.nHour = tempTime.getHours();
        notification.nMinute = tempTime.getMinutes();

        database.notificationDao().insertNotification(notification);

        Toast.makeText(getApplicationContext(), "New notification was made!", Toast.LENGTH_SHORT).show();
        return notification.uid;
    }

    private void showDatePickerDialog(){
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tempDate = new Date(year, month, dayOfMonth);
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
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
        tempTime = new Time(hourOfDay, minute, 0);
        this.hour = hourOfDay;
        this.minute = minute;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}