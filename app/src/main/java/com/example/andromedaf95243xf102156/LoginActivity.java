package com.example.andromedaf95243xf102156;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private UserDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabaseHelper = new UserDatabaseHelper(this);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        Button mRegisterButton = (Button) findViewById(R.id.login_button);

        mRegisterButton.setOnClickListener(view -> {
            String username = mUsernameEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                // Show an error message if the username or password is empty
                // You could also use a TextInputLayout with the setError() method to display an error message
                return;
            }
            if (isUserExisting(username, password)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                mUsernameEditText.getText().clear();
                mPasswordEditText.getText().clear();
                Intent intent = new Intent(this, HomePageActivity.class);
                intent.putExtra(Constants.EXTRA_USERNAME, username);
                startActivity(intent);
            }
            else if (!isUserExisting(username, password)){
                Toast.makeText(this, "Wrong credentials! Please try again", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean isUserExisting(String username, String password) {
        int id = mDatabaseHelper.getCurrentPasswordAndUser(username, password);
        if (id != -1){
            return true;
        }
        return false;
    }
}