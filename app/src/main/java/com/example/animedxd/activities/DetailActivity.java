package com.example.animedxd.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animedxd.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String animeTitle = getIntent().getStringExtra("title");
        String animeGenre = getIntent().getStringExtra("genre");
        String animeSynopsis = getIntent().getStringExtra("synopsis");
        int animeImageCover = getIntent().getIntExtra("imageCover", -1);

        ImageView coverImage = findViewById(R.id.coverImage);
        TextView titleLabel = findViewById(R.id.titleLabel);
        TextView genreLabel = findViewById(R.id.genreLabel);
        TextView synopsisLabel = findViewById(R.id.synopsisLabel);

        coverImage.setImageResource(animeImageCover);
        titleLabel.setText(animeTitle);
        genreLabel.setText(animeGenre);
        synopsisLabel.setText(animeSynopsis);
    }
}