package com.example.athina.ui.notifications;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.athina.R;
import com.example.athina.database_notifications.AppDatabaseNotifications;
import com.example.athina.database_notifications.Notification;
import com.example.athina.database_plan.AppDatabasePlan;
import com.example.athina.database_plan.Plan;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class AddNotification extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Date tempDate;
    Time tempTime;

    boolean isTimePicked = false;
    boolean isDatePicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        EditText nName = (EditText) findViewById(R.id.editTextTitleNotif);
        EditText nDescription = (EditText) findViewById(R.id.et_description_add);



        Button nDate = (Button) findViewById(R.id.button_add_date);
        nDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                isDatePicked = true;
            }

        });

        Button nTime = (Button) findViewById(R.id.button_time_add);
        nTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
                isTimePicked = true;
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveFeature);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nName.getText().toString().isEmpty() || nDescription.getText().toString().isEmpty() || isTimePicked || isDatePicked){
                    Toast.makeText(getApplicationContext(), "Cannot add empty plan!", Toast.LENGTH_SHORT).show();
                }else{
                    saveNewNotification(nName.getText().toString(), nDescription.getText().toString());
                    finish();
                }
            }
        });

    }

    private void saveNewNotification(String nName, String nDescription) {
        AppDatabaseNotifications database = AppDatabaseNotifications.getDBInstance(this.getApplicationContext());

        Notification notification = new Notification();
        notification.nTitle = nName;
        notification.nDescription = nDescription;
        notification.nDate = tempDate;
        notification.nTime = tempTime;

        database.notificationDao().insertNotification(notification);

        Toast.makeText(getApplicationContext(), "New notification was made!", Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog(){
        datePickerDialog = new DatePickerDialog(
                this,
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
    }

    private void showTimePickerDialog() {
        timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tempTime = new Time(hourOfDay, minute, 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}