package com.example.a1150070042_lethianhthu_lab6;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo layout là activity_view_pager.xml
        setContentView(R.layout.activity_view_pager);

        ViewPager2 viewPager = findViewById(R.id.view_pager);

        // Khởi tạo Adapter và gán cho ViewPager2
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // ViewPager2 mặc định đã có hiệu ứng trượt màn hình (screen slide animation)
    }
}