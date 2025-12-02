package com.example.a1150070042_lethianhthu_lab5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class M000LoginFragment extends Fragment implements View.OnClickListener {

    // Khai báo các thành phần giao diện
    private EditText edtEmail, edtPass;
    // Khai báo Context để tương tác với Activity và hệ thống
    private Context mContext;

    /**
     * Phương thức được gọi khi Fragment được đính kèm vào Activity.
     * Dùng để lấy tham chiếu của Context (là MainActivity).
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Phương thức tạo View (giao diện) cho Fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Ánh xạ layout XML của màn hình đăng nhập
        View rootView = inflater.inflate(R.layout.m000_frg_login, container, false);
        // Gọi phương thức ánh xạ các View con và thiết lập sự kiện
        initView(rootView);
        return rootView;
    }

    /**
     * Ánh xạ các thành phần giao diện và thiết lập sự kiện nghe (Listener).
     */
    private void initView(View v) {
        // Ánh xạ trường Email và Password
        edtEmail = v.findViewById(R.id.edt_email);
        edtPass = v.findViewById(R.id.edt_pass);

        // Thiết lập sự kiện click cho nút Đăng nhập
        v.findViewById(R.id.tv_login).setOnClickListener(this);
        // Thiết lập sự kiện click cho nút/text chuyển sang Đăng ký
        v.findViewById(R.id.tv_register).setOnClickListener(this);
    }

    /**
     * Xử lý sự kiện click cho các View đã đăng ký.
     */
    @Override
    public void onClick(View v) {
        // Thêm hiệu ứng animation khi người dùng click
        v.startAnimation(AnimationUtils.loadAnimation(mContext,
                androidx.appcompat.R.anim.abc_fade_in));

        if (v.getId() == R.id.tv_login) {
            // Nếu click nút Đăng nhập, gọi hàm login
            login(edtEmail.getText().toString(), edtPass.getText().toString());
        } else if (v.getId() == R.id.tv_register) {
            // Nếu click nút Đăng ký, gọi hàm chuyển màn hình
            gotoRegisterScreen();
        }
    }

    /**
     * Xử lý logic Đăng nhập: Kiểm tra dữ liệu nhập vào với dữ liệu lưu trữ.
     */
    private void login(String mail, String pass) {
        // Kiểm tra trường rỗng
        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(mContext,"Empty value", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khởi tạo SharedPreferences để đọc dữ liệu
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF,
                Context.MODE_PRIVATE);

        // ĐỌC DỮ LIỆU: Lấy mật khẩu đã lưu trữ, sử dụng email làm KEY
        String savedPass = pref.getString(mail, null);

        // Kiểm tra tài khoản có tồn tại không
        if (savedPass == null) {
            Toast.makeText(mContext,"Email is not existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        // KIỂM TRA MẬT KHẨU
        if (!pass.equals(savedPass)) {
            Toast.makeText(mContext,"Password is not correct!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đăng nhập thành công
        Toast.makeText(mContext,"Login account successfully!", Toast.LENGTH_SHORT).show();

    }

    /**
     * Chuyển sang màn hình Đăng ký (M001RegisterFragment).
     * Gọi phương thức điều phối Fragment từ MainActivity.
     */
    private void gotoRegisterScreen() {
        // Kiểm tra Context có phải là MainActivity không
        if (mContext instanceof MainActivity) {
            // Ép kiểu Context sang MainActivity và gọi hàm chuyển màn hình
            ((MainActivity) mContext).gotoRegisterScreen();
        }
    }
}