package com.example.animedxd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animedxd.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {

    private String username;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = getIntent().getStringExtra("username");

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.navAbout);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.navHome) {
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.navList) {
                intent = new Intent(this, ListActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.navAbout) {
                intent = new Intent(this, AboutActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            }
            return false;
        });


        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + username + "!");
    }
}