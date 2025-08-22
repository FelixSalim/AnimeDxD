package com.example.animedxd.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.R;
import com.example.animedxd.adapters.ReviewsAdapter;
import com.example.animedxd.models.Review;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List<Review> reviews;
    private ReviewsAdapter adapter;

    private Dialog reviewDialog;

    private static final int REQUEST_ADD_REVIEW = 100;
    AppCompatButton reviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        super.setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String usersname = getIntent().getStringExtra("username");

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, ListActivity.class);
            intent.putExtra("username", usersname);
            startActivity(intent);
            finish();
        });
        // Ambil data dari intent
        String animeTitle = getIntent().getStringExtra("title");
        String animeGenre = getIntent().getStringExtra("genre");
        String animeSynopsis = getIntent().getStringExtra("synopsis");
        int animeImageCover = getIntent().getIntExtra("imageCover", -1);

        // Set UI
        ImageView coverImage = findViewById(R.id.coverImage);
        TextView titleLabel = findViewById(R.id.titleLabel);
        TextView genreLabel = findViewById(R.id.genreLabel);
        TextView synopsisLabel = findViewById(R.id.synopsisLabel);
        reviewButton = findViewById(R.id.reviewButton);

        coverImage.setImageResource(animeImageCover);
        titleLabel.setText(animeTitle);
        genreLabel.setText(animeGenre);
        synopsisLabel.setText(animeSynopsis);

        // Inisialisasi list review dari "database"
        reviews = getReviewsFromDatabase();

        // Klik tombol review → munculkan overlay
        reviewButton.setOnClickListener(v -> {
            reviewButton.setBackgroundResource(R.drawable.review_button_bg_done);
            reviewButton.setTextColor(getResources().getColor(R.color.accent));

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_review_overlay, null);

            RecyclerView recyclerView = dialogView.findViewById(R.id.reviewsRecyclerView);
            Button addReviewBtn = dialogView.findViewById(R.id.addReviewButton);

            // Gunakan adapter global
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ReviewsAdapter(reviews);
            recyclerView.setAdapter(adapter);

            addReviewBtn.setOnClickListener(btn -> {
                BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
                View dialogView2 = getLayoutInflater().inflate(R.layout.activity_add_review, null, false);

                // cukup sekali saja
                bottomSheetDialog2.setContentView(dialogView2);

                // gunakan setBackgroundResource, bukan setBackgroundColor
                addReviewBtn.setBackgroundResource(R.drawable.add_review_button_done);
                addReviewBtn.setTextColor(getResources().getColor(R.color.accent));

                Button btnCancel = dialogView2.findViewById(R.id.btnCancel);
                Button btnSave = dialogView2.findViewById(R.id.btnSave);

                TextView tvErrorUsername, tvErrorReview;
                tvErrorUsername = dialogView2.findViewById(R.id.tvErrorUsername);
                tvErrorReview = dialogView2.findViewById(R.id.tvErrorReview);

                EditText etUsername, etReview;
                etUsername = dialogView2.findViewById(R.id.etUsername);
                etReview = dialogView2.findViewById(R.id.etReview);

                btnCancel.setOnClickListener(view -> {
                    btnCancel.setBackgroundResource(R.drawable.save_button_done);
                    btnCancel.setTextColor(getResources().getColor(R.color.accent));
                    bottomSheetDialog2.dismiss();
                });

                btnSave.setOnClickListener(view -> {
                    btnSave.setBackgroundResource(R.drawable.save_button_done);
                    btnSave.setTextColor(getResources().getColor(R.color.accent));

                    boolean valid = true;
                    String username = etUsername.getText().toString();
                    String comment = etReview.getText().toString();

                    if (username.isEmpty()) {
                        tvErrorUsername.setVisibility(View.VISIBLE);
                        valid = false;
                    } else {
                        tvErrorUsername.setVisibility(View.INVISIBLE);
                    }

                    if (comment.isEmpty()) {
                        tvErrorReview.setVisibility(View.VISIBLE);
                        valid = false;
                    } else {
                        tvErrorReview.setVisibility(View.INVISIBLE);
                    }

                    if (!valid) return;

                    // Simpan flag di SharedPreferences kalau memang dibutuhkan
                    // prefs.edit().putBoolean(KEY_IS_SAVED, true).apply();

                    reviews.add(new Review(username, comment));
                    adapter.notifyItemInserted(reviews.size() - 1);
                    recyclerView.scrollToPosition(reviews.size() - 1);
                    bottomSheetDialog2.dismiss();

                });

                etUsername.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            tvErrorUsername.setVisibility(TextView.INVISIBLE);
                        }
                    }
                    @Override public void afterTextChanged(Editable s) {}
                });

                etReview.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            tvErrorReview.setVisibility(TextView.INVISIBLE);
                        }
                    }
                    @Override public void afterTextChanged(Editable s) {}
                });

                bottomSheetDialog2.setContentView(dialogView2);

                FrameLayout bottomSheet = bottomSheetDialog2.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setSkipCollapsed(true);
                }

                bottomSheetDialog2.show();

            });

            bottomSheetDialog.setContentView(dialogView);
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setSkipCollapsed(true);
            }
            bottomSheetDialog.show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_REVIEW && resultCode == RESULT_OK && data != null) {
            String username = data.getStringExtra("username");
            String reviewText = data.getStringExtra("review");

            if (username != null && reviewText != null) {
                reviews.add(new Review(username, reviewText));

                if (adapter != null) {
                    adapter.notifyItemInserted(reviews.size() - 1);
                }
            }
        }
    }


    // Contoh data dummy
    private List<Review> getReviewsFromDatabase() {
        List<Review> list = new ArrayList<>();
        list.add(new Review("user123", "this is one of the best anime"));
        list.add(new Review("animeFan99", "Absolutely loved the story and characters!"));
        return list;
    }
}
