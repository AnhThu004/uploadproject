package com.example.multithread;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLab1 = findViewById(R.id.btn_lab1);
        Button btnLab2 = findViewById(R.id.btn_lab2);
        Button btnLab3 = findViewById(R.id.btn_lab3);

        btnLab1.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MessageActivity.class));
        });

        btnLab2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        });

        btnLab3.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Main3Activity.class));
        });
    }
}