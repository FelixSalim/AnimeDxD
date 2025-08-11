package com.example.animedxd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animedxd.R;

public class AddReviewActivity extends AppCompatActivity {
    private EditText etUsername, etReview;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review); // pastikan nama file xml benar

        etUsername = findViewById(R.id.etUsername); // sesuaikan id jika berbeda
        etReview = findViewById(R.id.etReview);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String review = etReview.getText().toString().trim();

            if (username.isEmpty() || review.isEmpty()) {
                Toast.makeText(this, "Mohon isi username dan review", Toast.LENGTH_SHORT).show();
                return;
            }

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
    }
}
