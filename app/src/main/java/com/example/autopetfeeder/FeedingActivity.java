package com.example.autopetfeeder;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FeedingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeding_schedule_page); // Your main layout

        // Find the Floating Action Button by its ID
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        // Set a click listener on the button
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(FeedingActivity.this, AddSchedActivity.class);
            startActivity(intent);
        });
    }
}