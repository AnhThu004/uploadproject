package com.example.multithread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MessageActivity extends AppCompatActivity {
    private ProgressBar pbFirst, pbSecond;
    private TextView tvMsgWorking, tvMsgReturned;
    private Button btnStart;
    private volatile boolean isRunning = false;
    private final int MAX_SEC = 20;
    private int intTest = 1; // Biến toàn cục dùng chung
    private Thread bgThread;
    private Handler handler;
    private static final int  MSG_UPDATE_PROGRESS  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_message);

        // 1. Ánh xạ View
        pbFirst = findViewById(R.id.pb_first);
        pbSecond = findViewById(R.id.pb_second);
        tvMsgWorking = findViewById(R.id.tv_working);
        tvMsgReturned = findViewById(R.id.tv_return);
        btnStart = findViewById(R.id.btn_start);

        // 2. Khởi tạo biến và Handler
        initVariables();

        // 4. OnClick
        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                isRunning = true;
                pbFirst.setProgress(0);
                pbFirst.setVisibility(View.VISIBLE);
                pbSecond.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.GONE);
                initBgThread(); // Khởi tạo Thread
                bgThread.start(); // Bắt đầu Thread
            }
        });
    }

    private void initVariables() {
        pbFirst.setMax(MAX_SEC);
        pbFirst.setVisibility(View.GONE);
        pbSecond.setVisibility(View.GONE);

        // Khởi tạo Handler để nhận Message (chạy trên UI Thread)
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_UPDATE_PROGRESS) {
                    String returnedValue = (String) msg.obj;
                    tvMsgReturned.append(returnedValue + "\n");
                    pbFirst.incrementProgressBy(2); // Tăng tiến độ (2% mỗi lần)
                    int progress = pbFirst.getProgress();
                    tvMsgWorking.setText("Working... Progress: " + progress + "/" + MAX_SEC);

                    // Kiểm tra hoàn thành
                    if (progress >= MAX_SEC) {
                        tvMsgWorking.setText("Done \nBackground thread has been stopped");
                        pbFirst.setVisibility(View.GONE);
                        pbSecond.setVisibility(View.GONE);
                        btnStart.setVisibility(View.VISIBLE);
                        isRunning = false;
                    }
                }
            }
        };
    }

    // 3. Khởi tạo Background Thread
    private void initBgThread() {
        bgThread = new Thread(new Runnable() {
            private final Random rnd = new Random();
            @Override
            public void run() {
                // Loop cho đến khi hoàn thành hoặc bị dừng
                for (int i = 0; i < MAX_SEC && isRunning; i += 2) {
                    try {
                        Thread.sleep(1000); // Ngủ 1 giây
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    if (isRunning) {
                        int threadValue = rnd.nextInt(101); // Dữ liệu ngẫu nhiên
                        // Lưu ý: intTest++ không đồng bộ, dễ gây lỗi nếu có nhiều Thread
                        intTest++;
                        String data = "Thread value: " + threadValue +
                                "\n global value seen by all thread " + intTest;

                        // Tạo và gửi Message
                        Message msg = handler.obtainMessage(MSG_UPDATE_PROGRESS, data);
                        handler.sendMessage(msg);
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false; // Đặt cờ dừng khi Activity bị dừng
    }
}