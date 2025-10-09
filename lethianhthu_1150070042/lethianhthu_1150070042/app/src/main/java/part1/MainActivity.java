package part1;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DbAdapter dbAdapter;
    private List<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part1_activity_main);

        // Khởi tạo DB
        dbAdapter = new DbAdapter(this);
        dbAdapter.open();

        // Xóa tất cả user cũ
        dbAdapter.deleteAllUsers();

        // Thêm 10 user mới
        for (int i = 0; i < 10; i++) {
            dbAdapter.createUser("Nguyễn Văn An " + i);
        }

        // Lấy danh sách người dùng
        users = getData();

        // Hiển thị lên ListView
        showData();

        // Nút mở Bài 2
        Button btnOpenPart2 = findViewById(R.id.btn_open_part2);
        btnOpenPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(part1.MainActivity.this, part2.MainActivity.class);
                startActivity(intent);
            }
        });

        dbAdapter.close(); // đóng kết nối
    }

    // Lấy dữ liệu từ DB
    private List<String> getData() {
        List<String> userList = new ArrayList<>();
        Cursor cursor = dbAdapter.getAllUsers();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_NAME));
                userList.add(name);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return userList;
    }

    // Hiển thị dữ liệu lên ListView
    private void showData() {
        ListView lvUser = findViewById(R.id.lv_user);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(
                this,
                R.layout.part1_item_user,
                R.id.tv_user,
                users
        );

        lvUser.setAdapter(userAdapter);
    }
}
