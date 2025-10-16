package com.example.a1150070042_lethianhthu;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "DownloadChannel";
    private static final String TAG = "DownloadService"; // Thêm TAG để lọc log

    public static final String ACTION_START = "com.example.ACTION_START";
    public static final String ACTION_PAUSE = "com.example.ACTION_PAUSE";
    public static final String ACTION_RESUME = "com.example.ACTION_RESUME";
    public static final String ACTION_CANCEL = "com.example.ACTION_CANCEL";

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private RemoteViews remoteViews;

    private Thread downloadThread;
    private volatile boolean isPaused = false;
    private volatile boolean isCancelled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_START:
                    // Ngăn việc bắt đầu download mới nếu đã có một tiến trình đang chạy
                    if (downloadThread != null && downloadThread.isAlive()) {
                        Toast.makeText(this, "A download is already in progress.", Toast.LENGTH_SHORT).show();
                        return START_NOT_STICKY;
                    }
                    String url = intent.getStringExtra("url");
                    startDownload(url);
                    break;
                case ACTION_PAUSE:
                    pauseDownload();
                    break;
                case ACTION_RESUME:
                    resumeDownload();
                    break;
                case ACTION_CANCEL:
                    cancelDownload();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    private void startDownload(String urlString) {
        isCancelled = false;
        isPaused = false;

        startForeground(NOTIFICATION_ID, createNotification());

        downloadThread = new Thread(() -> {
            HttpURLConnection connection = null;
            InputStream input = null;
            FileOutputStream output = null;
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "Server Response Code: " + responseCode); // In log response code

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new Exception("Server returned HTTP " + responseCode
                            + " " + connection.getResponseMessage());
                }

                int fileLength = connection.getContentLength();
                input = connection.getInputStream();
                File file = new File(getFilesDir(), "downloaded_file.dat");
                output = new FileOutputStream(file);

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled) {
                        break;
                    }
                    while (isPaused) {
                        Thread.sleep(100);
                    }
                    total += count;
                    output.write(data, 0, count);

                    if (fileLength > 0) {
                        int progress = (int) (total * 100 / fileLength);
                        updateNotificationProgress(progress);
                    }
                }

                // **SỬA LỖI 1: Xử lý trạng thái cuối cùng**
                if (isCancelled) {
                    file.delete();
                    Log.d(TAG, "Download cancelled, file deleted.");
                } else {
                    Log.d(TAG, "Download completed successfully.");
                    updateNotificationCompleted();
                }

            } catch (Exception e) {
                Log.e(TAG, "Download failed", e); // In chi tiết lỗi ra Logcat
                updateNotificationFailed();
            } finally {
                try {
                    if (output != null) output.close();
                    if (input != null) input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection != null) connection.disconnect();

                // **SỬA LỖI 2: Dừng service sau khi tác vụ hoàn thành**
                stopForeground(false); // Giữ lại notification sau khi xong
                stopSelf(); // Tự hủy service
            }
        });
        downloadThread.start();
    }

    private void pauseDownload() {
        if (remoteViews == null || notificationBuilder == null) return;
        isPaused = true;
        remoteViews.setImageViewResource(R.id.btn_pause_resume, android.R.drawable.ic_media_play);
        remoteViews.setOnClickPendingIntent(R.id.btn_pause_resume, createPendingIntent(ACTION_RESUME));
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        Toast.makeText(this, "Download Paused", Toast.LENGTH_SHORT).show();
    }

    private void resumeDownload() {
        if (remoteViews == null || notificationBuilder == null) return;
        isPaused = false;
        remoteViews.setImageViewResource(R.id.btn_pause_resume, android.R.drawable.ic_media_pause);
        remoteViews.setOnClickPendingIntent(R.id.btn_pause_resume, createPendingIntent(ACTION_PAUSE));
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        Toast.makeText(this, "Download Resumed", Toast.LENGTH_SHORT).show();
    }

    private void cancelDownload() {
        isCancelled = true;
        if (downloadThread != null) {
            downloadThread.interrupt();
        }
        stopForeground(true); // Dừng foreground và xóa notification ngay lập tức
        stopSelf();
        Toast.makeText(this, "Download Canceled", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Download Service Channel", NotificationManager.IMPORTANCE_LOW
            );
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification createNotification() {
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        remoteViews.setProgressBar(R.id.notification_progress, 100, 0, false);
        remoteViews.setTextViewText(R.id.notification_title, "Downloading file...");

        remoteViews.setOnClickPendingIntent(R.id.btn_pause_resume, createPendingIntent(ACTION_PAUSE));
        remoteViews.setOnClickPendingIntent(R.id.btn_cancel, createPendingIntent(ACTION_CANCEL));

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                // **SỬA LỖI 3: Dùng icon chuẩn cho download**
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setCustomContentView(remoteViews)
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        return notificationBuilder.build();
    }

    private PendingIntent createPendingIntent(String action) {
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private void updateNotificationProgress(int progress) {
        if (remoteViews == null || notificationBuilder == null) return;
        remoteViews.setProgressBar(R.id.notification_progress, 100, progress, false);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void updateNotificationCompleted() {
        if (remoteViews == null || notificationBuilder == null) return;
        remoteViews.setTextViewText(R.id.notification_title, "Download Completed");
        remoteViews.setProgressBar(R.id.notification_progress, 100, 100, false); // Hiện 100%
        notificationBuilder.setOngoing(false);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void updateNotificationFailed() {
        if (remoteViews == null || notificationBuilder == null) return;
        remoteViews.setTextViewText(R.id.notification_title, "Download Failed");
        remoteViews.setProgressBar(R.id.notification_progress, 0, 0, false);
        notificationBuilder.setOngoing(false);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed.");
        if (downloadThread != null) {
            downloadThread.interrupt();
        }
        notificationManager.cancel(NOTIFICATION_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}