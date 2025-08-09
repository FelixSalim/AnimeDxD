package com.example.animedxd.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.R;
import com.example.animedxd.adapters.ReviewsAdapter;
import com.example.animedxd.models.Review;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    Button reviewButton;

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

        // Klik tombol review → munculkan overlay
        reviewButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.setContentView(R.layout.dialog_review_overlay);

            RecyclerView recyclerView = dialog.findViewById(R.id.reviewsRecyclerView);
            Button addReviewBtn = dialog.findViewById(R.id.addReviewButton);

            // Setup list review
            List<Review> reviews = getReviewsFromDatabase(); // ganti sesuai sumber data
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ReviewsAdapter(reviews));

            // Pindah ke halaman Add Review
            addReviewBtn.setOnClickListener(btn -> {
                dialog.dismiss();
                startActivity(new Intent(this, AddReviewActivity.class));
            });

            dialog.show();
        });
    }

    // Contoh data dummy
    private List<Review> getReviewsFromDatabase() {
        List<Review> list = new ArrayList<>();
        list.add(new Review("user123", "this is one of the best anime"));
        list.add(new Review("animeFan99", "Absolutely loved the story and characters!"));
        return list;
    }
}