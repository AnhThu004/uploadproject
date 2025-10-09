package part2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ListView lvContacts;
    private ArrayAdapter<String> adapter;
    private List<Contact> contactList = new ArrayList<>();
    private List<String> displayList = new ArrayList<>(); // text để hiển thị (Tên - SĐT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // dùng layout part2_activity_main.xml
        setContentView(R.layout.part2_activity_main);

        db = new DatabaseHandler(this);
        lvContacts = findViewById(R.id.lv_contacts);

        // (Tuỳ chọn) Thêm dữ liệu mẫu nếu DB đang trống
        if (db.getAllContacts().isEmpty()) {
            db.addContact(new Contact("Ravi", "9100000000"));
            db.addContact(new Contact("Srinivas", "9199999999"));
            db.addContact(new Contact("Tommy", "9522222222"));
            db.addContact(new Contact("Karthik", "9533333333"));
        }

        loadDataToList();

        // dùng item layout part2_item_contact.xml
        adapter = new ArrayAdapter<>(this, R.layout.part2_item_contact, R.id.tv_contact, displayList);
        lvContacts.setAdapter(adapter);

        // Long click để xoá khỏi DB
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact toDelete = contactList.get(position);
                db.deleteContact(toDelete);
                Toast.makeText(MainActivity.this, "Đã xoá: " + toDelete.getName(), Toast.LENGTH_SHORT).show();
                loadDataToList();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // In ra Logcat để kiểm tra
        for (Contact c : contactList) {
            Log.e("Contact", "Id: " + c.getId() + ", Name: " + c.getName() + ", Phone: " + c.getPhoneNumber());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataToList();
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    private void loadDataToList() {
        contactList = db.getAllContacts();
        displayList.clear();
        for (Contact c : contactList) {
            displayList.add(c.getName() + " - " + c.getPhoneNumber());
        }
    }
}
