package com.example.autopetfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotlink;
    private TextView signuplink;
    private View edittext;
    private Button loginBTN;
    private Button googleBTN;
    private Button facebookBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in); // loads your landing.xml

        forgotlink = findViewById(R.id.forgot_link);
        signuplink = findViewById(R.id.signup_link);
        edittext = findViewById(R.id.loginusernameField);
        edittext = findViewById(R.id.loginpassField);
        loginBTN = findViewById(R.id.loginBTN);
        googleBTN = findViewById(R.id.googleBTN);
        facebookBTN = findViewById(R.id.facebookBTN);




        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
