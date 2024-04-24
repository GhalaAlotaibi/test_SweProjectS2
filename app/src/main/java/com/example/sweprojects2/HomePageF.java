package com.example.sweprojects2;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_f);
        // Find the LinearLayout views for the services
        LinearLayout service1Layout = findViewById(R.id.Service1);
        LinearLayout service2Layout = findViewById(R.id.Service2);
        LinearLayout service3Layout = findViewById(R.id.Service3);

        // Set click listeners for the service LinearLayouts
        service1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Service1
                navigateToBookPage();
            }
        });

        service2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Service2
                navigateToBookPage();
            }
        });

        service3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Service3
                navigateToBookPage();
            }
        });
    }

    // Method to navigate to the book page activity
    private void navigateToBookPage() {
        Intent intent = new Intent(HomePageF.this, book.class);
        startActivity(intent);
    }
}