package com.example.animedxd.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.animedxd.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.adapters.ReviewsAdapter;
import com.example.animedxd.models.Review;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
        setContentView(R.layout.activity_review_list);

        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        addReviewButton = findViewById(R.id.addReviewButton);

        // Data awal (bisa kosong atau contoh)
        reviewList = new ArrayList<>();
        reviewList.add(new Review("Alice", "Anime-nya keren banget!"));
        reviewList.add(new Review("Budi", "Ceritanya agak lambat di awal."));

        // Setup RecyclerView
        reviewsAdapter = new ReviewsAdapter(reviewList);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        // Ubah warna button pas diklik
        addReviewButton.setOnClickListener(v -> {
            addReviewButton.setBackgroundResource(R.drawable.add_review_button_done);
            addReviewButton.setTextColor(getResources().getColor(R.color.accent));

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            View dialogView = getLayoutInflater().inflate(R.layout.activity_add_review, null, false);
            bottomSheetDialog.setContentView(dialogView);

            // Tombol cancel dan save
            Button btnCancel = dialogView.findViewById(R.id.btnCancel);
            Button btnSave = dialogView.findViewById(R.id.btnSave);

            btnCancel.setOnClickListener(view -> bottomSheetDialog.dismiss());

            btnSave.setOnClickListener(view -> {
                String username = ((android.widget.EditText) dialogView.findViewById(R.id.etUsername)).getText().toString();
                String comment = ((android.widget.EditText) dialogView.findViewById(R.id.etReview)).getText().toString();

                if (!username.isEmpty() && !comment.isEmpty()) {
                    reviewList.add(new Review(username, comment));
                    reviewsAdapter.notifyItemInserted(reviewList.size() - 1);
                    reviewsRecyclerView.scrollToPosition(reviewList.size() - 1);
                    bottomSheetDialog.dismiss();
                }
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
