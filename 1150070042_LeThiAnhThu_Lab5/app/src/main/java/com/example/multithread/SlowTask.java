package com.example.multithread;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference; // Thêm import QUAN TRỌNG này

// Bỏ qua cảnh báo lỗi thời (deprecated)
@SuppressWarnings("deprecation")
public class SlowTask extends AsyncTask<String, Integer, String> {

    // SỬA: Dùng WeakReference cho tham chiếu UI để tránh Memory Leak
    private final WeakReference<Context> contextRef;
    private final WeakReference<TextView> tvStatusRef;
    private ProgressDialog progressDialog;

    public SlowTask(Context context, TextView tvStatus) {
        this.contextRef = new WeakReference<>(context);
        this.tvStatusRef = new WeakReference<>(tvStatus);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Context context = contextRef.get();
        TextView tvStatus = tvStatusRef.get();

        if (context == null || tvStatus == null) return;

        // 1. Hiển thị ProgressDialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Some SLOW job is being done. Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tvStatus.setText("Status: Slow Job starting...");
    }

    @Override
    protected String doInBackground(String... params) {
        int max = 5;
        for (int i = 1; i <= max; i++) {
            try {
                Thread.sleep(2000); // Công việc nặng (2 giây)
            } catch (InterruptedException e) {
                // Nếu bị ngắt (cancel)
                Thread.currentThread().interrupt();
                return "Cancelled";
            }
            // 2. Gửi tiến trình
            publishProgress(i);
        }
        return "Finished! Max value reached: 5";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        TextView tvStatus = tvStatusRef.get();
        if (tvStatus != null) {
            // 3. Cập nhật UI theo tiến trình
            tvStatus.setText("Status: Slow Job running... Value = " + values[0]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Context context = contextRef.get();
        TextView tvStatus = tvStatusRef.get();

        // 4. Kết thúc: Đóng Dialog
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        // Chỉ cập nhật UI nếu Activity/TextView còn tồn tại
        if (tvStatus != null && context != null) {
            tvStatus.setText("Status: " + result);
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}