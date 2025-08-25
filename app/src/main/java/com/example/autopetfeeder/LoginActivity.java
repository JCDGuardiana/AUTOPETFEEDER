package com.example.autopetfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotlink;
    private TextView signuplink;
    private Button loginBTN;
    private Button googleBTN;
    private Button facebookBTN;

    private EditText email_field;
    private EditText password_field;

    private FirebaseAuth auth;
    private DatabaseReference DRUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        auth = FirebaseAuth.getInstance();

        // This line is enough. No need for a separate 'db' variable.
        DRUser = FirebaseDatabase.getInstance("https://autopetfeeder-cc6d7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

        forgotlink = findViewById(R.id.forgot_link);
        signuplink = findViewById(R.id.signup_link);
        email_field = findViewById(R.id.loginemailField);
        password_field = findViewById(R.id.loginpassField);
        loginBTN = findViewById(R.id.loginBTN);
        googleBTN = findViewById(R.id.googleBTN);
        facebookBTN = findViewById(R.id.facebookBTN);

        loginBTN.setOnClickListener(v -> {
            String Email = email_field.getText().toString().trim();
            String password = password_field.getText().toString().trim();

            if (Email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String uid = user.getUid();
                        DRUser.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // Call finish() to prevent returning to the login screen
                                } else {
                                    Toast.makeText(LoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                                    auth.signOut(); // Sign out the user if data doesn't exist
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(LoginActivity.this, "Something went wrong: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        forgotlink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FogotPasswordActivity.class);
            startActivity(intent);
        });

        signuplink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}