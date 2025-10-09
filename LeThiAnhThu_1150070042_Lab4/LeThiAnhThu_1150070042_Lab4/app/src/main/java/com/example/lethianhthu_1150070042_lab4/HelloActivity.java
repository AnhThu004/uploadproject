package com.example.lethianhthu_1150070042_lab4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dòng này rất quan trọng: liên kết Activity với layout XML
        setContentView(R.layout.activity_hello);
    }
}