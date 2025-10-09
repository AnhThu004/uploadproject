package com.example.multithread;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {
    private ProgressBar pbWaiting;
    private TextView tvTopCaption;
    private EditText etInput;
    private Button btnExecute;
    private int globalValue = 0; // Biến dùng chung
    private int accum = 0;
    private long startTime;
    private Handler handler;
    private Runnable fgRunnable, bgRunnable;
    private Thread testThread;
    private final String PATIENCE = "Some important data is being collected now.\nPlease be patient...wait...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        findViewByIds();
        initVariables();

        btnExecute.setOnClickListener(v -> {
            String input = etInput.getText().toString();
            Toast.makeText(PostActivity.this, "Input: " + input, Toast.LENGTH_SHORT).show();
        });

        // 4. Bắt đầu
        testThread = new Thread(bgRunnable);
        testThread.start();
        handler.post(fgRunnable); // Bắt đầu lặp lại tác vụ UI
    }

    private void findViewByIds() {
        pbWaiting = findViewById(R.id.pb_waiting);
        tvTopCaption = findViewById(R.id.tv_top_caption);
        etInput = findViewById(R.id.et_input);
        btnExecute = findViewById(R.id.btn_execute);
    }

    private void initVariables() {
        pbWaiting.setProgress(0);
        handler = new Handler(getMainLooper());
        startTime = System.currentTimeMillis();

        // 2. Runnable chạy trên UI Thread (Cập nhật giao diện và tăng giá trị nhanh)
        fgRunnable = new Runnable() {
            @Override
            public void run() {
                long totalTime = System.currentTimeMillis() - startTime;

                // Đồng bộ hóa: Khối synchronized cần thiết khi truy cập globalValue
                synchronized (bgRunnable) { // Đồng bộ hóa trên đối tượng bgRunnable
                    globalValue += 100; // Tăng nhanh
                }

                accum += 100;

                // Cập nhật UI
                pbWaiting.incrementProgressBy(100);
                tvTopCaption.setText(PATIENCE +
                        "\nProgress: " + accum + "/" + pbWaiting.getMax() +
                        "\nTotal Time: " + totalTime/1000 + "s" +
                        "\nGlobal Value (Fast+Slow): " + globalValue);

                if (accum < pbWaiting.getMax()) {
                    // Lặp lại chính nó sau 10ms
                    handler.postDelayed(fgRunnable, 10);
                } else {
                    tvTopCaption.setText("Background work is over!");
                    pbWaiting.setVisibility(View.GONE);
                }
            }
        };

        // 3. Runnable chạy trên Background Thread (Tăng giá trị chậm)
        bgRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (accum < pbWaiting.getMax()) {
                        Thread.sleep(1000); // Ngủ 1 giây

                        // Đồng bộ hóa khi truy cập biến toàn cục
                        synchronized (this) {
                            globalValue += 1; // Tăng chậm
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}