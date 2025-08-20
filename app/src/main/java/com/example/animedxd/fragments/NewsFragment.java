package com.example.animedxd.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.animedxd.R;
import com.example.animedxd.adapters.CarouselAdapter;
import com.example.animedxd.models.News;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private static final int DELAY_MS = 5000; // 5 detik
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

    private ViewPager2 carouselViewPager;
    private CarouselAdapter carouselAdapter;
    private TabLayout carouselTabLayout;

    private List<News> newsList;
    private ViewPager2.OnPageChangeCallback pageCallback;

    public NewsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        carouselViewPager = root.findViewById(R.id.carouselViewPager);
        carouselTabLayout = root.findViewById(R.id.carouselTabLayout);

        // ====== DATASET ASLI ======
        newsList = new ArrayList<>();
        newsList.add(new News(R.drawable.news_jujutsukaisen));
        newsList.add(new News(R.drawable.news_sololeveling));
        newsList.add(new News(R.drawable.news_haikyuu));
        newsList.add(new News(R.drawable.news_spyxfamily));

        // ====== DATASET INFINITE: [last, originals..., first] ======
        List<News> infiniteList = new ArrayList<>();
        infiniteList.add(newsList.get(newsList.size() - 1));
        infiniteList.addAll(newsList);
        infiniteList.add(newsList.get(0));

        // Adapter + posisi awal pada item 1 (real first)
        carouselAdapter = new CarouselAdapter(infiniteList);
        carouselViewPager.setAdapter(carouselAdapter);
        carouselViewPager.setOffscreenPageLimit(1);
        carouselViewPager.setCurrentItem(1, false);

        // ====== DOTS (TabLayout) ======
        carouselTabLayout.removeAllTabs();
        for (int i = 0; i < newsList.size(); i++) {
            carouselTabLayout.addTab(carouselTabLayout.newTab());
        }
        if (carouselTabLayout.getTabAt(0) != null) {
            carouselTabLayout.selectTab(carouselTabLayout.getTabAt(0));
        }

        // Klik dot -> pindah ke halaman real (offset +1 karena sentinel)
        carouselTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int target = tab.getPosition() + 1;
                if (carouselViewPager.getCurrentItem() != target) {
                    carouselViewPager.setCurrentItem(target, true);
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {
                carouselViewPager.setCurrentItem(tab.getPosition() + 1, true);
            }
        });

        // Sinkronkan dot saat swipe + perbaiki lompatan sentinel
        pageCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Lompat senyap saat menyentuh sentinel
                if (position == 0) {
                    carouselViewPager.post(() ->
                            carouselViewPager.setCurrentItem(newsList.size(), false));
                    return;
                } else if (position == newsList.size() + 1) {
                    carouselViewPager.post(() ->
                            carouselViewPager.setCurrentItem(1, false));
                    return;
                }

                // Hitung posisi real (0..N-1) untuk dot
                int realPos = position - 1;
                TabLayout.Tab tab = carouselTabLayout.getTabAt(realPos);
                if (tab != null && !tab.isSelected()) {
                    carouselTabLayout.selectTab(tab);
                }
            }
        };
        carouselViewPager.registerOnPageChangeCallback(pageCallback);

        // ====== AUTO SLIDE ======
        runnable = new Runnable() {
            @Override
            public void run() {
                int current = carouselViewPager.getCurrentItem();
                int next = current + 1;

                // Jika next melewati sentinel terakhir, lompat ke 1 (first real) tanpa animasi
                if (next > newsList.size()) {
                    carouselViewPager.setCurrentItem(1, false);
                } else {
                    carouselViewPager.setCurrentItem(next, true);
                }

                handler.postDelayed(this, DELAY_MS);
            }
        };

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, DELAY_MS);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (pageCallback != null) {
            carouselViewPager.unregisterOnPageChangeCallback(pageCallback);
        }
    }
}
