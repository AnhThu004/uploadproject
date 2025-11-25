package com.example.a1150070042_lethianhthu_lab4.zodiac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1150070042_lethianhthu_lab4.R;

import java.util.ArrayList;

public class M001MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Zodiac> listZodiacs;
    private Zodiac currentSelectedZodiac;

    private ImageView ivCenterIcon;
    private TextView tvZodiacName;
    private TextView tvZodiacDate;
    private TextView tvZodiacSummary;
    private Button btnXemThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m001_act_menu);

        listZodiacs = ZodiacData.getZodiacs();
        initViews();
        selectZodiac(listZodiacs.get(0));
    }

    private void initViews() {
        ivCenterIcon = findViewById(R.id.iv_center_zodiac);
        tvZodiacName = findViewById(R.id.tv_zodiac_name);
        tvZodiacDate = findViewById(R.id.tv_zodiac_date);
        tvZodiacSummary = findViewById(R.id.tv_zodiac_summary);
        btnXemThem = findViewById(R.id.btn_xem_them);

        for (int i = 1; i <= 12; i++) {
            int resId = getResources().getIdentifier("iv_zodiac_" + i, "id", getPackageName());
            ImageView iv = findViewById(resId);

            if (iv != null && i <= listZodiacs.size()) {
                iv.setOnClickListener(this);
                iv.setTag(listZodiacs.get(i - 1));
                iv.setImageResource(listZodiacs.get(i - 1).getIconId());
            }
        }

        btnXemThem.setOnClickListener(v -> gotoM002DetailActivity());
    }

    private void selectZodiac(Zodiac zodiac) {
        currentSelectedZodiac = zodiac;

        ivCenterIcon.setImageResource(zodiac.getIconId());
        tvZodiacName.setText(zodiac.getName());
        tvZodiacDate.setText("Ngày sinh: " + zodiac.getDate());
        tvZodiacSummary.setText(zodiac.getSummary());
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Zodiac) {
            Zodiac selected = (Zodiac) v.getTag();
            selectZodiac(selected);
            Toast.makeText(this, "Đã chọn: " + selected.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoM002DetailActivity() {
        if (currentSelectedZodiac == null) {
            Toast.makeText(this, "Vui lòng chọn một cung trước.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, M002DetailActivity.class);
        intent.putExtra("ZODIAC_DATA", currentSelectedZodiac);
        startActivity(intent);
    }
}