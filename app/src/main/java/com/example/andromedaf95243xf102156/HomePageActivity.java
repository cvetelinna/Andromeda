package com.example.andromedaf95243xf102156;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra(Constants.EXTRA_USERNAME);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.welcome_message);
        textView.setText(getString(R.string.welcome_message, username));

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, LogoutActivity.class);
                startActivity(intent);
            }
        });
    }

}