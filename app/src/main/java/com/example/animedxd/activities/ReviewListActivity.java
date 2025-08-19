package com.example.animedxd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.animedxd.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.adapters.ReviewsAdapter;
import com.example.animedxd.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewListActivity extends AppCompatActivity {

    private RecyclerView reviewsRecyclerView;
    private ReviewsAdapter reviewsAdapter;
    private List<Review> reviewList;
    private Button addReviewButton;

    public static final int REQUEST_ADD_REVIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_review_overlay);

        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        addReviewButton = findViewById(R.id.addReviewButton);
        RelativeLayout rootLayout = findViewById(R.id.rootLayout);
        LinearLayout cardContainer = findViewById(R.id.cardContainer);

        // Data awal (bisa kosong atau contoh)
        reviewList = new ArrayList<>();
        reviewList.add(new Review("Alice", "Anime-nya keren banget!"));
        reviewList.add(new Review("Budi", "Ceritanya agak lambat di awal."));

        // Setup RecyclerView
        reviewsAdapter = new ReviewsAdapter(reviewList);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        // Klik luar card -> nutup
        rootLayout.setOnClickListener(v -> finish());

        // Biar klik card gak nutup
                cardContainer.setOnClickListener(v -> {});

        // Ubah warna button pas diklik
                addReviewButton.setOnClickListener(v -> {
                    Intent intent = new Intent(ReviewListActivity.this, AddReviewActivity.class);
                    startActivityForResult(intent, REQUEST_ADD_REVIEW);

                    addReviewButton.setBackgroundResource(R.drawable.add_review_button_done);
                    addReviewButton.setTextColor(getResources().getColor(R.color.accent));
                });

    }

    // Tangkap hasil dari AddReviewActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_REVIEW && resultCode == RESULT_OK && data != null) {
            String username = data.getStringExtra("username");
            String comment = data.getStringExtra("comment");

            if (username != null && comment != null) {
                reviewList.add(new Review(username, comment));
                reviewsAdapter.notifyItemInserted(reviewList.size() - 1);
                reviewsRecyclerView.scrollToPosition(reviewList.size() - 1);
            }
        }
    }
}
