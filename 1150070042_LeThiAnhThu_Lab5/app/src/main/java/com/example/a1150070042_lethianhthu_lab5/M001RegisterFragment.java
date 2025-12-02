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


public class M001RegisterFragment extends Fragment implements View.OnClickListener {

    // Khai báo các thành phần giao diện: Email, Mật khẩu, Nhập lại Mật khẩu
    private EditText edtEmail, edtPass, edtRepass;
    // Khai báo Context để tương tác với Activity
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
        // Ánh xạ layout XML của màn hình đăng ký
        View rootView = inflater.inflate(R.layout.m001_frg_register, container, false);
        // Gọi phương thức ánh xạ các View con và thiết lập sự kiện
        initView(rootView);
        return rootView;
    }

    /**
     * Ánh xạ các thành phần giao diện và thiết lập sự kiện nghe (Listener).
     */
    private void initView(View v) {
        // Ánh xạ các trường nhập liệu
        edtEmail = v.findViewById(R.id.edt_email);
        edtPass = v.findViewById(R.id.edt_pass);
        edtRepass = v.findViewById(R.id.edt_re_pass);

        // Thiết lập sự kiện click cho nút Đăng ký
        v.findViewById(R.id.tv_register).setOnClickListener(this);
        // Thiết lập sự kiện click cho nút Quay lại
        v.findViewById(R.id.iv_back).setOnClickListener(this);
    }

    /**
     * Xử lý sự kiện click cho các View đã đăng ký.
     */
    @Override
    public void onClick(View v) {
        // Thêm hiệu ứng animation khi người dùng click
        v.startAnimation(AnimationUtils.loadAnimation(mContext,
                androidx.appcompat.R.anim.abc_fade_in));

        if (v.getId() == R.id.iv_back) {
            // Nếu click nút Quay lại, chuyển về màn hình Login
            gotoLoginScreen();
        } else if (v.getId() == R.id.tv_register) {
            // Nếu click nút Đăng ký, gọi hàm register với dữ liệu nhập
            register(edtEmail.getText().toString(),
                    edtPass.getText().toString(),
                    edtRepass.getText().toString());
        }
    }

    /**
     * Xử lý logic Đăng ký: Kiểm tra dữ liệu và lưu vào SharedPreferences.
     */
    private void register(String mail, String pass, String repass) {
        // Kiểm tra trường rỗng
        if (mail.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
            Toast.makeText(mContext,"Empty value", Toast.LENGTH_SHORT).show();
            return;
        }

        // KIỂM TRA: Mật khẩu nhập lại có khớp không
        if (!pass.equals(repass)) {
            Toast.makeText(mContext,"Password is not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khởi tạo SharedPreferences để đọc/ghi dữ liệu
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF,
                Context.MODE_PRIVATE);

        // KIỂM TRA: Email đã tồn tại chưa (đọc dữ liệu với email làm KEY)
        String savedPass = pref.getString(mail, null);
        if (savedPass != null) {
            Toast.makeText(mContext,"Email is existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        //GHI DỮ LIỆU: Lưu tài khoản mới (email là KEY, password là VALUE)
        pref.edit().putString(mail, pass).apply(); // apply() thực hiện lưu trữ bất đồng bộ

        // Đăng ký thành công và chuyển màn hình
        Toast.makeText(mContext,"Register account successfully!", Toast.LENGTH_SHORT).show();
        gotoLoginScreen();
    }

    /**
     * Chuyển về màn hình Đăng nhập (M000LoginFragment).
     * Gọi phương thức điều phối Fragment từ MainActivity.
     */
    private void gotoLoginScreen() {
        // Kiểm tra Context có phải là MainActivity không
        if (mContext instanceof MainActivity) {
            // Ép kiểu Context sang MainActivity và gọi hàm chuyển màn hình
            ((MainActivity) mContext).gotoLoginScreen();
        }
    }
}