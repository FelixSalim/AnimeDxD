package com.example.animedxd.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.animedxd.fragments.NewsFragment;
import com.example.animedxd.fragments.MangaFragment;

public class HomeAdapter extends FragmentStateAdapter {

    public HomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else {
            return new MangaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
