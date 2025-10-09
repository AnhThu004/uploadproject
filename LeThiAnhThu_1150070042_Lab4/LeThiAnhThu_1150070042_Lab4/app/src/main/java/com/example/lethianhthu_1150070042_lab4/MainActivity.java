package com.example.lethianhthu_1150070042_lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHello = findViewById(R.id.btnHelloWorld);
        Button btnRegister = findViewById(R.id.btnDangKy);

        // Nút "Hello World"
        btnHello.setOnClickListener(v -> {
            // Chuyển sang màn hình HelloActivity
            Intent intent = new Intent(MainActivity.this, HelloActivity.class);
            startActivity(intent);
        });

        // Nút "Đăng ký"
        btnRegister.setOnClickListener(v -> {
            // Sửa dòng này để chuyển đến HelloActivity thay vì DangKyActivity
            Intent intent = new Intent(MainActivity.this, DangKyActivity.class);
            startActivity(intent);
        });
    }
}