package com.example.a1150070042_lethianhthu_lab6;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2; // Có 2 trang: Elizabeth và Catherine

    public ScreenSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                // Trang 1: Elizabeth Johnson
                return CardFragment.newInstance(
                        "Elizabeth Johnson",
                        "Project Manager",
                        "elizabethjohnson@example.com"
                );
            case 1:
                // Trang 2: Catherine Johnson
                return CardFragment.newInstance(
                        "Catherine Johnson",
                        "President of Sales",
                        "catherinejohnson@example.com"
                );
            default:
                // Trường hợp mặc định
                return CardFragment.newInstance("", "", "");
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}