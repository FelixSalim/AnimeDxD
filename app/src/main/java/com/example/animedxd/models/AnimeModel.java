package com.example.animedxd.models;

public class AnimeModel {

    private final String title, genre, synopsis;
    private final int imageCover;

    public AnimeModel(String title, String genre, String synopsis, int imageCover) {
        this.title = title;
        this.genre = genre;
        this.synopsis = synopsis;
        this.imageCover = imageCover;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getImageCover() {
        return imageCover;
    }
}
