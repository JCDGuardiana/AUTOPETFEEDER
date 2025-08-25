package com.example.autopetfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSchedActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private ChipGroup dayChipGroup;
    private Button saveButton;
    private DatabaseReference DRsched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule_page);

        timePicker = findViewById(R.id.timePicker);
        Log.d("Click", "found");
        dayChipGroup = findViewById(R.id.dayChipGroup);
        Log.d("Click", "found");
        saveButton = findViewById(R.id.saveButton);
        Log.d("Click", "found");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://autopetfeeder-cc6d7-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DRsched = db.getReference("DRsched");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("Click", "found");
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                HashMap<String, Object> sched = new HashMap<>();

                List<String> days = new ArrayList<>();
                for (int id : dayChipGroup.getCheckedChipIds()) {
                    Chip chip = dayChipGroup.findViewById(id);
                    String day = chip.getText().toString();
                    days.add(day);
                }

                sched.put("hour", hour);
                sched.put("minute", minute);
                sched.put("days", days);
                //check
                Log.d("AddSchedActivity", "Attempting to save: " + sched.toString());

                DRsched.push().setValue(sched)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddSchedActivity.this, "Schedule saved successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddSchedActivity.this, FeedingActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddSchedActivity.this, "Failed to save schedule: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }
        });
    }
}