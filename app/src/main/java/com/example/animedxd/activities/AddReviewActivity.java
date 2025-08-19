package com.example.animedxd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animedxd.R;

public class AddReviewActivity extends AppCompatActivity {
    private EditText etUsername, etReview;
    private Button btnSave, btnCancel;
    private TextView tvErrorUsername, tvErrorReview;

    private SharedPreferences prefs;
    private static final String PREF_NAME = "review_prefs";
    private static final String KEY_IS_SAVED = "is_saved";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        etUsername = findViewById(R.id.etUsername);
        etReview = findViewById(R.id.etReview);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        tvErrorUsername = findViewById(R.id.tvErrorUsername);
        tvErrorReview = findViewById(R.id.tvErrorReview);

        btnSave.setOnClickListener(v -> {
            btnSave.setBackgroundResource(R.drawable.add_review_button_done);
            btnSave.setTextColor(getResources().getColor(R.color.accent));

            boolean valid = true;
            String username = etUsername.getText().toString().trim();
            String review = etReview.getText().toString().trim();

            if (username.isEmpty()) {
                tvErrorUsername.setVisibility(View.VISIBLE);
                valid = false;
            } else {
                tvErrorUsername.setVisibility(View.GONE);
            }

            if (review.isEmpty()) {
                tvErrorReview.setVisibility(View.VISIBLE);
                valid = false;
            } else {
                tvErrorReview.setVisibility(View.GONE);
            }

            if (!valid) return;

            // Simpan flag di SharedPreferences kalau memang dibutuhkan
            // prefs.edit().putBoolean(KEY_IS_SAVED, true).apply();

            Intent result = new Intent();
            result.putExtra("username", username);
            result.putExtra("review", review);
            setResult(RESULT_OK, result);
            finish();
        });


        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    tvErrorUsername.setVisibility(TextView.GONE);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        etReview.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    tvErrorReview.setVisibility(TextView.GONE);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
