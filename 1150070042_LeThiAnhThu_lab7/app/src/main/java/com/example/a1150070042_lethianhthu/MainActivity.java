package com.example.a1150070042_lethianhthu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.a1150070042_lethianhthu.DownloadService;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl;
    private Button btnDownload;

    // Launcher để xin quyền POST_NOTIFICATIONS
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
                    startDownload(); // Gọi lại hàm download nếu đã có quyền
                } else {
                    Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        btnDownload = findViewById(R.id.btnDownload);

        // Gán sẵn 1 link để test

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndDownload();
            }
        });
    }

    private void checkPermissionAndDownload() {
        // Chỉ cần xin quyền trên Android 13 (TIRAMISU) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                startDownload(); // Đã có quyền, bắt đầu download
            } else {
                // Xin quyền
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            startDownload(); // Các phiên bản cũ hơn không cần xin quyền này
        }
    }

    private void startDownload() {
        String url = etUrl.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", url);
        intent.setAction(DownloadService.ACTION_START);
        startService(intent);

        Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show();
    }
}