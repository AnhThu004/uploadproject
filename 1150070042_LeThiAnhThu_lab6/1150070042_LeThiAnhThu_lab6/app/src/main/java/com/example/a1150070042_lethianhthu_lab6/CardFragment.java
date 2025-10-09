package com.example.a1150070042_lethianhthu_lab6;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardFragment extends Fragment {

    // Khóa Bundle để lưu trữ dữ liệu (tốt cho việc tái tạo Fragment)
    private static final String ARG_NAME = "name";
    private static final String ARG_POSITION = "position";
    private static final String ARG_EMAIL = "email";

    private String name, position, email;

    // 1. Constructor mặc định (BẮT BUỘC cho Fragments)
    public CardFragment() {
        // Required empty public constructor
    }

    // 2. Phương thức static tạo Fragment (Khuyến nghị)
    public static CardFragment newInstance(String name, String position, String email) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_POSITION, position);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lấy dữ liệu từ Bundle
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            position = getArguments().getString(ARG_POSITION);
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho Fragment này
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        // Ánh xạ và gán dữ liệu vào TextViews
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPosition = view.findViewById(R.id.tv_position);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        tvName.setText(name);
        tvPosition.setText(position);
        tvEmail.setText(email);

        return view;
    }
}