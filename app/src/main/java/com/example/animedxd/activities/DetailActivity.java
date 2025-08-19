package com.example.animedxd.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
            reviewButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
            reviewButton.setTextColor(Color.parseColor("#FA00FF"));

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_review_overlay, null);

            RecyclerView recyclerView = dialogView.findViewById(R.id.reviewsRecyclerView);
            Button addReviewBtn = dialogView.findViewById(R.id.addReviewButton);

            // Gunakan adapter global
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ReviewsAdapter(reviews);
            recyclerView.setAdapter(adapter);

            addReviewBtn.setOnClickListener(btn -> {
                addReviewBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                addReviewBtn.setTextColor(Color.parseColor("#FA00FF"));
                startActivityForResult(new Intent(this, AddReviewActivity.class), REQUEST_ADD_REVIEW);
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

    private void showReviewDialog() {
        // Kalau dialog sudah ada, tutup dulu supaya nggak dobel
        if (reviewDialog != null && reviewDialog.isShowing()) {
            reviewDialog.dismiss();
        }

        reviewDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        reviewDialog.setContentView(R.layout.dialog_review_overlay);

        RecyclerView recyclerView = reviewDialog.findViewById(R.id.reviewsRecyclerView);
        Button addReviewBtn = reviewDialog.findViewById(R.id.addReviewButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewsAdapter(reviews);
        recyclerView.setAdapter(adapter);

        // Pindah ke AddReviewActivity tanpa dismiss dialog
        addReviewBtn.setOnClickListener(btn -> {
            Intent intent = new Intent(this, AddReviewActivity.class);
            startActivityForResult(intent, REQUEST_ADD_REVIEW);
        });

        reviewDialog.show();
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

                    // Kalau dialog masih kebuka, scroll ke review terbaru
                    if (reviewDialog != null && reviewDialog.isShowing()) {
                        RecyclerView rv = reviewDialog.findViewById(R.id.reviewsRecyclerView);
                        if (rv != null) {
                            rv.scrollToPosition(reviews.size() - 1);
                        }
                    } else {
                        // Kalau dialog nggak kebuka, buka lagi
                        showReviewDialog();
                    }
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
