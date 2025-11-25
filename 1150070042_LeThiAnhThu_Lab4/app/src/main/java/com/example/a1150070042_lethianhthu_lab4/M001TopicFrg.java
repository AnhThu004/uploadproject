package com.example.a1150070042_lethianhthu_lab4;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.io.IOException;

public class M001TopicFrg extends Fragment implements View.OnClickListener {
    private Context mContext;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); mContext = context; }
    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.m001_frg_topic, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View v) {
        LinearLayout lnMain = v.findViewById(R.id.ln_topic);
        lnMain.removeAllViews();
        String[] topicKeys = mContext.getResources().getStringArray(R.array.topic_keys);
        String[] photoIds = mContext.getResources().getStringArray(R.array.topic_photo_ids);

        if (topicKeys.length != photoIds.length) {
            Toast.makeText(mContext, "Lỗi dữ liệu: Số lượng Chủ đề và Ảnh không khớp!", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < topicKeys.length; i++) {
            String key = topicKeys[i];
            String photoId = photoIds[i];

            try {
                String fileName = photoId.toLowerCase().trim() + ".png";
                String topicName = formatTopicName(key);

                View vTopic = LayoutInflater.from(mContext).inflate(R.layout.item_topic, null);
                ImageView ivTopic = vTopic.findViewById(R.id.iv_topic);
                TextView tvTopic = vTopic.findViewById(R.id.tv_topic);
                ivTopic.setImageBitmap(BitmapFactory
                        .decodeStream(mContext.getAssets().open("photo/" + fileName)));

                tvTopic.setText(topicName);
                lnMain.addView(vTopic);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vTopic.getLayoutParams();
                params.bottomMargin = 40;
                vTopic.setLayoutParams(params);

                vTopic.setTag(key);
                vTopic.setOnClickListener(this);
            } catch (IOException e) {

                Toast.makeText(mContext, "Lỗi: Không tìm thấy file ảnh " + photoId + ".png", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String formatTopicName(String rawKey) {
        String topicName = rawKey.replace('_', ' ').replace('-', ' ');
        if (topicName.length() > 0) {
            topicName = topicName.substring(0, 1).toUpperCase() + topicName.substring(1);
        }
        return topicName;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).gotoM002Screen((String)v.getTag());
    }
}