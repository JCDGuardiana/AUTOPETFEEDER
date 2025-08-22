package com.example.autopetfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page); // loads your landing.xml

        // Find the button by ID
        Button landingBTN = findViewById(R.id.landingBTN);

        // When button is clicked → go to LoginActivity (for now we can just point to MainActivity)
        //switching scene to login page
        landingBTN.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
