package com.example.multithread;

import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity {
    private Button btnQuickJob, btnSlowJob;
    private TextView tvStatus;
    private SlowTask slowTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        findViewByIds();

        // 1. Khởi tạo đối tượng Task ban đầu
        slowTask = new SlowTask(this, tvStatus);

        // Xử lý Quick Job (Chạy trên UI Thread)
        btnQuickJob.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            tvStatus.setText("Status: Quick Job done at " + sdf.format(new Date()));
        });

        // Xử lý Slow Job (Chạy AsyncTask)
        btnSlowJob.setOnClickListener(v -> {
            AsyncTask.Status currentStatus = slowTask.getStatus();
            if (currentStatus == AsyncTask.Status.RUNNING) {
                // Thông báo nếu đang chạy
                tvStatus.setText("Status: Task is already running!");
            } else if (currentStatus == AsyncTask.Status.FINISHED) {
                // QUAN TRỌNG: AsyncTask chỉ execute được 1 lần, phải tạo đối tượng mới
                slowTask = new SlowTask(Main3Activity.this, tvStatus);
                slowTask.execute("Start");
            } else {
                // Trạng thái PENDING, gọi lần đầu
                slowTask.execute("Start");
            }
        });
    }

    private void findViewByIds() {
        btnQuickJob = findViewById(R.id.btn_quick_job);
        btnSlowJob = findViewById(R.id.btn_slow_job);
        tvStatus = findViewById(R.id.tv_status);
    }
}