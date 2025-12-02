package com.example.tmdt_lethianhthu_lab1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    // Khai báo các thành phần của Máy tính
    private EditText txtX, txtY;
    private TextView txtResult;
    private Button btnPlus, btnMinus, btnMultiply, btnDivide, btnModulus;

    // Khai báo các thành phần của Camera
    private Button btnCamera;
    private ImageView imgPhoto;

    // Request code để nhận kết quả từ Camera
    private static final int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
    }

    private void initControl() {
        // Ánh xạ các control của Máy tính
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtResult = findViewById(R.id.txtResult);

        // Ánh xạ 5 nút phép toán
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnModulus = findViewById(R.id.btnModulus);

        // Ánh xạ các control của Camera
        btnCamera = findViewById(R.id.btnCamera);
        imgPhoto = findViewById(R.id.imgPhoto);

        // Gán sự kiện OnClickListener chung cho 5 nút phép toán
        btnPlus.setOnClickListener(calculatorListener);
        btnMinus.setOnClickListener(calculatorListener);
        btnMultiply.setOnClickListener(calculatorListener);
        btnDivide.setOnClickListener(calculatorListener);
        btnModulus.setOnClickListener(calculatorListener);

        // Gán sự kiện cho nút Mở Camera
        btnCamera.setOnClickListener(cameraListener);
    }

    // Định nghĩa đối tượng OnClickListener để xử lý logic 5 phép toán
    private View.OnClickListener calculatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                // 1. Lấy dữ liệu
                double numX = Double.parseDouble(txtX.getText().toString());
                double numY = Double.parseDouble(txtY.getText().toString());
                double result = 0;
                String operator = "";

                // 2. Xử lý logic theo ID của nút được nhấn
                int id = v.getId();
                if (id == R.id.btnPlus) {
                    result = numX + numY;
                    operator = "Tổng";
                } else if (id == R.id.btnMinus) {
                    result = numX - numY;
                    operator = "Hiệu";
                } else if (id == R.id.btnMultiply) {
                    result = numX * numY;
                    operator = "Tích";
                } else if (id == R.id.btnDivide) {
                    if (numY == 0) {
                        Toast.makeText(MainActivity.this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                        txtResult.setText("Lỗi: Chia 0");
                        return; // Ngừng thực thi nếu chia cho 0
                    }
                    result = numX / numY;
                    operator = "Thương";
                } else if (id == R.id.btnModulus) {
                    result = numX % numY;
                    operator = "Dư";
                }

                // 3. Hiển thị kết quả
                txtResult.setText(operator + ": " + result);

            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu không nhập đủ số
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ 2 số", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Định nghĩa đối tượng OnClickListener để mở Camera
    private View.OnClickListener cameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Tạo Intent để khởi động ứng dụng Camera của hệ thống
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            // Khởi chạy Intent và chờ kết quả trả về
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    };

    /**
     * Phương thức nhận kết quả trả về sau khi gọi startActivityForResult().
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra đúng request code và kết quả thành công
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // Lấy ảnh Bitmap từ dữ liệu trả về (Extras)
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = (Bitmap) extras.get("data");

                // Hiển thị ảnh lên ImageView
                imgPhoto.setImageBitmap(photo);
            }
        }
    }
}