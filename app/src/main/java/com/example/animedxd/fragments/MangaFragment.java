package com.example.animedxd.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.R;
import com.example.animedxd.adapters.MangaAdapter;
import com.example.animedxd.models.Manga;

import java.util.ArrayList;
import java.util.List;

public class MangaFragment extends Fragment {

    public MangaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate fragment layout
        View view = inflater.inflate(R.layout.fragment_manga, container, false);

        // ambil RecyclerView dari fragment_manga.xml
        RecyclerView recyclerView = view.findViewById(R.id.mangaRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // isi dummy data manga
        List<Manga> mangaList = new ArrayList<>();
        mangaList.add(new Manga("Orange Marmalade",
                "People are no longer afraid of vampires but that doesn't stop humans from discriminating against them. Can Mari Baek hide her identity when she falls in love with a popular boy who hates vampires?",
                R.drawable.manga_orangemarmalade));
        mangaList.add(new Manga("How To Fight",
                "Scrawny high school student Hobin Yu is probably the last guy you’d expect to star in a NewTube channel that revolves around fighting. But Hobin is soon knocking out guys stronger than him and raking in more money than he could have ever dreamed of. ",
                R.drawable.manga_howtofight));
        mangaList.add(new Manga("True Beauty",
                "After binge-watching beauty videos online, a shy comic book fan masters the art of makeup and sees her social standing skyrocket as she becomes her school’s prettiest pretty girl overnight.",
                R.drawable.manga_truebeauty));
        mangaList.add(new Manga("Mercenary enrollment",
                "After binge-watching beauty videos online, a shy comic book fan masters the art of makeup and sees her social standing skyrocket as she becomes her school’s prettiest pretty girl overnight.",
                R.drawable.manga_mercenaryenrollment));
        mangaList.add(new Manga("Siren's Lament",
                "A shy comic book fan becomes a beauty guru after mastering makeup skills...",
                R.drawable.manga_sirenslament));
        mangaList.add(new Manga("Sweet Home",
                "A reality where monsters are trying to wipe out humanity. Now he must fight alongside a handful of reluctant heroes to try and save the world before it’s too late.",
                R.drawable.manga_sweethome));
        mangaList.add(new Manga("Our Beloved Summer",
                "At Midsummer High, Yeonsu Guk is at the top of her class, while Wung Choi is ranked dead-last. When a filmmaker comes to make a documentary about their school life over the course of a month, they’re not exactly thrilled to be spending so much time together.",
                R.drawable.manga_ourbelovedsummer));

        // set adapter
        MangaAdapter adapter = new MangaAdapter(mangaList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
