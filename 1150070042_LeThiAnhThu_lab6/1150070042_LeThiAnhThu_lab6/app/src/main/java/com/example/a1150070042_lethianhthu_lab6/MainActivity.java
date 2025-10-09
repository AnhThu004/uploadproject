package com.example.a1150070042_lethianhthu_lab6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến
    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml, btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml,
            btnZoomOutCode, btnRotateXml, btnRotateCode, btnMoveXml, btnMoveCode,
            btnSlideUpXml, btnSlideUpCode, btnBounceXml, btnBounceCode, btnCombineXml,
            btnCombineCode;
    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;
    private Button btnOpenViewPager; // Biến cho Bài tập 4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các View và Biến
        findViewsByIds();
        initVariables();

        // Bài tập 1 & 2: Xử lý Animation
        handleAllXmlAnimations();
        handleAllCodeAnimations();

        // Bài tập 3: Xử lý chuyển Activity (ivUitLogo -> Main2Activity)
        handleActivityTransition();

        // Bài tập 4: Xử lý chuyển Activity (Button -> ViewPagerActivity)
        handleViewPagerTransition();
    }

    // --- Các hàm khởi tạo ---

    private void findViewsByIds() {
        // ImageView
        ivUitLogo = findViewById(R.id.iv_uit_logo);
        // Ánh xạ nút cho Bài tập 4 (Cần đảm bảo ID này tồn tại trong XML)
        btnOpenViewPager = findViewById(R.id.btn_open_viewpager);

        // Buttons XML
        btnFadeInXml = findViewById(R.id.btn_fade_in_xml);
        btnFadeOutXml = findViewById(R.id.btn_fade_out_xml);
        btnBlinkXml = findViewById(R.id.btn_blink_xml);
        btnZoomInXml = findViewById(R.id.btn_zoom_in_xml);
        btnZoomOutXml = findViewById(R.id.btn_zoom_out_xml);
        btnRotateXml = findViewById(R.id.btn_rotate_xml);
        btnMoveXml = findViewById(R.id.btn_move_xml);
        btnSlideUpXml = findViewById(R.id.btn_slide_up_xml);
        btnBounceXml = findViewById(R.id.btn_bounce_xml);
        btnCombineXml = findViewById(R.id.btn_combine_xml);

        // Buttons Code
        btnFadeInCode = findViewById(R.id.btn_fade_in_code);
        btnFadeOutCode = findViewById(R.id.btn_fade_out_code);
        btnBlinkCode = findViewById(R.id.btn_blink_code);
        btnZoomInCode = findViewById(R.id.btn_zoom_in_code);
        btnZoomOutCode = findViewById(R.id.btn_zoom_out_code);
        btnRotateCode = findViewById(R.id.btn_rotate_code);
        btnMoveCode = findViewById(R.id.btn_move_code);
        btnSlideUpCode = findViewById(R.id.btn_slide_up_code);
        btnBounceCode = findViewById(R.id.btn_bounce_code);
        btnCombineCode = findViewById(R.id.btn_combine_code);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing to do
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing to do
            }
        };
    }

    // --- Bài tập 1: Xử lý Animation từ XML ---

    private void handleClickAnimationXml(Button btn, int animId) {
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, animId);
        animation.setAnimationListener(animationListener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    private void handleAllXmlAnimations() {
        handleClickAnimationXml(btnFadeInXml, R.anim.anim_fade_in);
        handleClickAnimationXml(btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXml(btnBlinkXml, R.anim.anim_blink);
        handleClickAnimationXml(btnZoomInXml, R.anim.anim_zoom_in);
        handleClickAnimationXml(btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXml(btnRotateXml, R.anim.anim_rotate);
        handleClickAnimationXml(btnMoveXml, R.anim.anim_move);
        handleClickAnimationXml(btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXml(btnBounceXml, R.anim.anim_bounce);
        handleClickAnimationXml(btnCombineXml, R.anim.anim_combine);
    }

    // --- Bài tập 2: Xử lý Animation từ Code ---

    private void handleClickAnimationCode(Button btn, final Animation animation) {
        animation.setAnimationListener(animationListener);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    private void handleAllCodeAnimations() {
        handleClickAnimationCode(btnFadeInCode, initFadeInAnimation());
        handleClickAnimationCode(btnFadeOutCode, initFadeOutAnimation());
        handleClickAnimationCode(btnBlinkCode, initBlinkAnimation());
        handleClickAnimationCode(btnZoomInCode, initZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode, initZoomOutAnimation());
        handleClickAnimationCode(btnRotateCode, initRotateAnimation());
        handleClickAnimationCode(btnMoveCode, initMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode, initSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode, initBounceAnimation());
        handleClickAnimationCode(btnCombineCode, initCombineAnimation());
    }

    // Các hàm khởi tạo Animation bằng Code

    private Animation initFadeInAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation initFadeOutAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation initBlinkAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(300);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(3);
        return animation;
    }

    private Animation initZoomInAnimation() {
        ScaleAnimation animation = new ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation initZoomOutAnimation() {
        ScaleAnimation animation = new ScaleAnimation(1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation initRotateAnimation() {
        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(600);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(2);
        animation.setInterpolator(new CycleInterpolator(1f));
        return animation;
    }

    private Animation initMoveAnimation() {
        // Di chuyển 75% chiều rộng của cha
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0.75f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(800);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        return animation;
    }

    private Animation initSlideUpAnimation() {
        // Thu nhỏ Y từ 100% đến 0%
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation initBounceAnimation() {
        // Phóng to Y từ 0% đến 100% với BounceInterpolator
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setInterpolator(new BounceInterpolator());
        return animation;
    }

    private Animation initCombineAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setFillAfter(true);
        set.setInterpolator(new LinearInterpolator());

        // Scale (Zoom In)
        ScaleAnimation scale = new ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(4000);
        set.addAnimation(scale);

        // Rotate
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setRepeatCount(2);
        rotate.setRepeatMode(Animation.RESTART);
        set.addAnimation(rotate);

        return set;
    }

    // --- Bài tập 3: Xử lý chuyển Activity ---

    private void handleActivityTransition() {
        ivUitLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNewActivity = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(iNewActivity);

                // Animation chuyển cảnh
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    // --- Bài tập 4: Xử lý chuyển Activity ---
    private void handleViewPagerTransition() {
        // Thêm kiểm tra Null để tăng tính an toàn
        if (btnOpenViewPager == null) {
            Toast.makeText(MainActivity.this, "LỖI: Button Bài 4 (btn_open_viewpager) không được tìm thấy!", Toast.LENGTH_LONG).show();
            return;
        }

        btnOpenViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảm bảo tên Activity là ViewPagerActivity.class
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });
    }
}