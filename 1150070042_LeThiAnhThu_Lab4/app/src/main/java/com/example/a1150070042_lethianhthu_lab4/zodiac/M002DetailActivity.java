package com.example.a1150070042_lethianhthu_lab4.zodiac;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1150070042_lethianhthu_lab4.R;

public class M002DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m002_act_detail);

        initViews();
        getAndSetData();
    }

    private void initViews() {
        ImageView ivBack = findViewById(R.id.iv_back_detail);
        ivBack.setOnClickListener(v -> finish());
    }

    private void getAndSetData() {
        ImageView ivIcon = findViewById(R.id.iv_detail_icon);
        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvDate = findViewById(R.id.tv_detail_date);
        TextView tvContent = findViewById(R.id.tv_detail_content);
        Zodiac zodiac = (Zodiac) getIntent().getSerializableExtra("ZODIAC_DATA");

        if (zodiac != null) {
            ivIcon.setImageResource(zodiac.getIconId());
            tvName.setText(zodiac.getName());
            tvDate.setText("Ngày sinh: " + zodiac.getDate());
            tvContent.setText(zodiac.getDetailContent());
        }
    }
}