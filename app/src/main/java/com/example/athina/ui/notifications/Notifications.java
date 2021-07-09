package com.example.athina.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.athina.R;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}