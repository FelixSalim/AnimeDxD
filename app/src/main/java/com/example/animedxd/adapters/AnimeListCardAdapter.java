package com.example.animedxd.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.R;
import com.example.animedxd.activities.DetailActivity;
import com.example.animedxd.models.AnimeModel;

public class AnimeListCardAdapter extends RecyclerView.Adapter<AnimeListCardAdapter.ViewHolder> {

    private Context context;
    private AnimeModel[] animes;

    public AnimeListCardAdapter(Context context, AnimeModel[] animes) {
        this.context = context;
        this.animes = animes;
    }

    @NonNull
    @Override
    public AnimeListCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.anime_list_view, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListCardAdapter.ViewHolder holder, int position) {

        holder.coverImage.setImageResource(animes[position].getImageCover());
        holder.titleLabel.setText(animes[position].getTitle());
        holder.genreLabel.setText(animes[position].getGenre());
        holder.synopsisLabel.setText(animes[position].getSynopsis());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", animes[position].getTitle());
            intent.putExtra("genre", animes[position].getGenre());
            intent.putExtra("synopsis", animes[position].getSynopsis());
            intent.putExtra("imageCover", animes[position].getImageCover());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animes.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView titleLabel, genreLabel, synopsisLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImage = itemView.findViewById(R.id.coverImage);
            titleLabel = itemView.findViewById(R.id.titleLabel);
            genreLabel = itemView.findViewById(R.id.genreLabel);
            synopsisLabel = itemView.findViewById(R.id.synopsisLabel);
        }
    }

}
