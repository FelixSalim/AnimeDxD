package com.example.animedxd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
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

        ImageButton menuIcon = findViewById(R.id.dropdownMenu);
        menuIcon.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View popupView = inflater.inflate(R.layout.logout_menu, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setTouchable(true);
            popupWindow.setElevation(10f);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            popupWindow.setAnimationStyle(R.style.PopupAnimation);

            View rootView = findViewById(android.R.id.content);
            rootView.setForeground(new ColorDrawable(Color.parseColor("#88000000")));

            popupWindow.setOnDismissListener(() -> {
                rootView.setForeground(null);
            });

            popupWindow.showAsDropDown(menuIcon, 0, -400);

            TextView logout = popupView.findViewById(R.id.logoutBtn);
            logout.setOnClickListener(vl -> {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            });
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