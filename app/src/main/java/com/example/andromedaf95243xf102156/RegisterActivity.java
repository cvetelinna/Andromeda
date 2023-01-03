package com.example.andromedaf95243xf102156;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private UserDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabaseHelper = new UserDatabaseHelper(this);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        Button mRegisterButton = (Button) findViewById(R.id.register_button);

        mRegisterButton.setOnClickListener(view -> {
            String username = mUsernameEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                // Show an error message if the username or password is empty
                // You could also use a TextInputLayout with the setError() method to display an error message
                return;
            }
            if (isUserExisting(username)) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
                mUsernameEditText.getText().clear();
                mPasswordEditText.getText().clear();

                return;
            }
            addUserToDatabase(username, password);
            // Clear the EditText fields
            mUsernameEditText.getText().clear();
            mPasswordEditText.getText().clear();

            Intent intent = new Intent(this, HomePageActivity.class);
            intent.putExtra(Constants.EXTRA_USERNAME, username);
            startActivity(intent);
        });
    }

    //check if user exists, and if it does - do not add it to the database
    private boolean isUserExisting(String username) {
        int id = mDatabaseHelper.getCurrentUser(username);
        if (id != -1){
            return true;
        }
        return false;
    }

    private void addUserToDatabase(String username, String password) {
        boolean insertData = mDatabaseHelper.addData(username, password);
        if (insertData) {
            Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User was not created!", Toast.LENGTH_SHORT).show();
        }
    }
}
