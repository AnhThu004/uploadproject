package com.example.a1150070042_lethianhthu_lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo layout là activity_main2.xml
        setContentView(R.layout.activity_main2);

        // Gắn sự kiện cho nút BACK (dùng ID btn_back từ layout)
        Button btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClick(v);
                }
            });
        }
    }

    // Phương thức xử lý khi nhấn nút BACK (được gọi từ Button trong XML hoặc listener)
    public void onBackClick(View view) {
        finish(); // Kết thúc Activity hiện tại

        // Áp dụng animation chuyển tiếp ngược lại:
        // Main2Activity (slide out right) | MainActivity (slide in left)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Xử lý khi người dùng nhấn nút Back cứng của thiết bị
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Áp dụng animation khi back
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}