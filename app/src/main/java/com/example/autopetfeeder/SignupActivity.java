package com.example.autopetfeeder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    public boolean hasUppercase = false;
    public boolean hasDigit = false;
    public boolean hasSpecial = false;


    //database firebase
    private DatabaseReference DRUser;
    private FirebaseDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        DB = FirebaseDatabase.getInstance(
                "https://autopetfeeder-cc6d7-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );

        DRUser = DB.getReference("Users");

        EditText usernameField = findViewById(R.id.usernameField);
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);
        EditText confirmpassField = findViewById(R.id.confirmpassField);
        Button signupBTN = findViewById(R.id.signupBTN);

        signupBTN.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmpassField.getText().toString().trim();
            String domain = "@gmail.com";
            String[] specialChars = {"!", "@", "#", "$", "%", "^", "&", "*"};

            // Check empty fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Email validation
            if (!email.endsWith(domain)) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Password match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Password validation
            if (password.length() < 8) {
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                return;
            }


            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) hasUppercase = true;
                if (Character.isDigit(c)) hasDigit = true;
                if (Arrays.asList(specialChars).contains(String.valueOf(c))) hasSpecial = true;
            }

            if (!hasUppercase) {
                Toast.makeText(this, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!hasDigit) {
                Toast.makeText(this, "Password must contain at least one digit", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!hasSpecial) {
                Toast.makeText(this, "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
                return;
            }

            // All checks passed → add user
            addUser(username, email, password);
        });
    }

    private void addUser(String username, String email, String password) {
        //store by pair key-value in database
        //assoc array
        // Write user to Firebase or your database
        HashMap<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);

        DRUser.child(username).setValue(user)  //add to database
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(SignupActivity.this, "User added to database", Toast.LENGTH_SHORT).show()
                        //check if it add the user to database successfully
                )
                .addOnFailureListener(e ->
                        Toast.makeText(SignupActivity.this, "Failed to add user: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        //it display failure to add the user to database
                );


        Toast.makeText(this, "User validated! Ready to add to database.", Toast.LENGTH_SHORT).show();
    }
}
