package com.example.animedxd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animedxd.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        TEMPORARY CODE, REMOVE WHEN U START WORKING
        TextView welcome = findViewById(R.id.welcome);
        String username = getIntent().getStringExtra("username");
        welcome.setText("Welcome, " + username);

        Button listPageBtn = findViewById(R.id.listPageBtn);
        Button aboutPageBtn = findViewById(R.id.aboutPageBtn);

        listPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        aboutPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}