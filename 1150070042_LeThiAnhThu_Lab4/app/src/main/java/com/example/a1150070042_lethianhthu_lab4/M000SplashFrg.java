package com.example.a1150070042_lethianhthu_lab4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class M000SplashFrg extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new Handler(Looper.getMainLooper()).postDelayed(this::gotoM001Screen, 2000);
        return inflater.inflate(R.layout.m000_frg_splash, container, false);
    }
    private void gotoM001Screen() {
        if (getActivity() != null && isAdded()) {
            ((MainActivity) getActivity()).gotoM001Screen();
        }
    }
}