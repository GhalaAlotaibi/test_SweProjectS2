package com.example.sweprojects2;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_f);
        Intent intent = getIntent();
        int clientId = intent.getIntExtra("clientId", -1);

        // Find the LinearLayout views for the services
        LinearLayout service1Layout = findViewById(R.id.Service1);
        LinearLayout service2Layout = findViewById(R.id.Service2);
        LinearLayout service3Layout = findViewById(R.id.Service3);
// Assuming you have a reference to the Service1 view
        Button logoutButton = findViewById(R.id.button4);
// Set click listener

        // Set click listeners for the service LinearLayouts
        service1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Service1
                navigateToBookPage(clientId);
            }
        });

        service2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Service2
                navigateToBookPage(clientId);
            }
        });

        service3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Service3
                navigateToBookPage(clientId);
            }
        });




     logoutButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent to start MainActivity
            Intent mainActivityIntent = new Intent(HomePageF.this, MainActivity.class);
            // Clear the back stack so the user can't navigate back to the HomePageF after logging out
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainActivityIntent);
        }
    });
}
    // Method to navigate to the book page activity
    private void navigateToBookPage(int clientId) {
        Intent intent = new Intent(HomePageF.this, book.class);
        intent.putExtra("clientId", clientId);
        startActivity(intent);
    }
}