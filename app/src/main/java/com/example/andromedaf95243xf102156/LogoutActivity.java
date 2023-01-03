package com.example.andromedaf95243xf102156;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Intent intent = new Intent(this, MainActivity.class);

        // Add the FLAG_ACTIVITY_CLEAR_TOP flag to the intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Start the MainActivity
        startActivity(intent);
    }
}