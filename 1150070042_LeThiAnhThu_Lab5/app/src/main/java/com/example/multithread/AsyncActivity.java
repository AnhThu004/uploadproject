package com.example.multithread;

import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AsyncActivity extends AppCompatActivity {

    private Button btnQuickJob, btnSlowJob;
    private TextView tvStatus;
    private SlowTask slowTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        // 1. Ánh xạ View
        btnQuickJob = findViewById(R.id.btn_quick_job);
        btnSlowJob = findViewById(R.id.btn_slow_job);
        tvStatus = findViewById(R.id.tv_status);

        // Khởi tạo AsyncTask lần đầu
        slowTask = new SlowTask(this, tvStatus);

        btnQuickJob.setOnClickListener(v -> {
            // Quick Job: Chạy trên UI Thread
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            tvStatus.setText("Status: Quick Job done at " + currentTime);
        });

        btnSlowJob.setOnClickListener(v -> {
            // Slow Job: Chạy AsyncTask
            if (slowTask.getStatus() != AsyncTask.Status.RUNNING) {
                // QUAN TRỌNG: Phải tạo lại instance của AsyncTask mỗi lần muốn chạy
                slowTask = new SlowTask(AsyncActivity.this, tvStatus);
                slowTask.execute("Start"); // Bắt đầu Task
            } else {
                tvStatus.setText("Status: Slow Job is already running...");
            }
        });
    }
}