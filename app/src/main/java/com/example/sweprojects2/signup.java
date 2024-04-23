package com.example.sweprojects2;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private EditText birthdayEditText;
    private EditText passwordEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        nameEditText = findViewById(R.id.editTextText5);
        emailEditText = findViewById(R.id.editTextText11);
        phoneNumberEditText = findViewById(R.id.editTextText22);
        birthdayEditText = findViewById(R.id.editTextText55);
        passwordEditText = findViewById(R.id.editTextText2);
        signupButton = findViewById(R.id.button2);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String birthday = birthdayEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // Check if any field is empty
                if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || birthday.isEmpty() || password.isEmpty()) {
                    Toast.makeText(signup.this, "Please enter all the required information.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Perform signup operation
                performSignup(name, email, phoneNumber, birthday, password);
            }
        });
    }
    private void performSignup(String name, String email, String phoneNumber, String birthday, String password) {
        DBHelper dbHelper = new DBHelper(signup.this);

        // Check if email already exists
        if (dbHelper.isEmailExists(email)) {
            Toast.makeText(signup.this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the client
        boolean result = dbHelper.addClient(name, email, phoneNumber, birthday, password);

        if (result){
            Toast.makeText(signup.this, "Client registered successfully", Toast.LENGTH_SHORT).show();
            // Redirect to home activity
            Intent intent = new Intent(signup.this, login.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(signup.this, "Client registration failed", Toast.LENGTH_SHORT).show();
    }

}