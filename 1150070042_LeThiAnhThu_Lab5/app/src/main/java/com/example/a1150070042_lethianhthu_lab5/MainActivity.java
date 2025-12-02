package com.example.a1150070042_lethianhthu_lab5;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Hằng số là tên file SharedPreferences được lưu trong hệ thống Android
    public static final String SAVE_PREF = "save_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn ActionBar (Tiêu đề trên cùng)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Gắn layout chính chứa Fragment
        setContentView(R.layout.activity_main);

        // Khởi tạo Fragment Đăng nhập đầu tiên
        gotoLoginScreen();
    }

    /**
     * Phương thức chuyển sang màn hình Đăng ký (M001RegisterFragment)
     * Sử dụng FragmentTransaction.replace để thay thế nội dung trong container R.id.ln_main
     */
    public void gotoRegisterScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ln_main, new M001RegisterFragment())
                .commit();
    }

    /**
     * Phương thức chuyển sang màn hình Đăng nhập (M000LoginFragment)
     */
    public void gotoLoginScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ln_main, new M000LoginFragment())
                .commit();
    }
}