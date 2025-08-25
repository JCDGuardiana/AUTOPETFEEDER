package com.example.autopetfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    CardView realTimeCard;
    CardView feedingCard;
    CardView historyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the CardViews from the layout by their IDs
        realTimeCard = findViewById(R.id.realTimeCard);
        feedingCard = findViewById(R.id.feedingCard);
        historyCard = findViewById(R.id.historyCard);

        // Set up the click listener for the Real-Time Card
        realTimeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since this is the main activity (Real-Time), no action is needed
                // or you could refresh the page.
            }
        });

        // Set up the click listener for the Feeding Schedule Card
        feedingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedingActivity.class);
                startActivity(intent);
            }
        });

        // Set up the click listener for the History Card
        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}