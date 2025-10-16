package com.example.a1150070042_lethianhthu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            // Tạo một intent mới để gửi lệnh đến com.example.a1150070042_lethianhthu.DownloadService
            Intent serviceIntent = new Intent(context, DownloadService.class);
            serviceIntent.setAction(intent.getAction()); // Copy action từ intent nhận được
            context.startService(serviceIntent);
        }
    }
}