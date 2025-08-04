package com.example.animedxd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField,passwordField;
    private AppCompatButton loginBtn;
    private TextView errorMessage;

    private String extractString(EditText field) {
        return field.getText().toString();
    }

    private void sendToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);
        errorMessage = findViewById(R.id.errorMessage);

        loginBtn.setOnClickListener(v -> {
            loginBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            loginBtn.setTextColor(Color.parseColor("#FA00FF"));

            String username = extractString(usernameField);
            String password = extractString(passwordField);

            if (username.isEmpty()) {
                errorMessage.setText("Username cannot be empty");
            } else if (password.isEmpty()) {
                errorMessage.setText("Password cannot be empty");
            } else if (username.length() < 5 || username.length() > 10) {
                errorMessage.setText("Username must be between 5-10 characters (inclusive)");
            } else {
                errorMessage.setText("");
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}