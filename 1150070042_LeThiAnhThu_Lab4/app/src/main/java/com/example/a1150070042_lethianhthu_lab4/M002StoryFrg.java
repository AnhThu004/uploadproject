package com.example.a1150070042_lethianhthu_lab4;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class M002StoryFrg extends Fragment {
    private Context mContext;
    private final ArrayList<StoryEntity> listStory = new ArrayList<>();
    private String topicKey;
    private String topicName;

    public void setTopicKey(String topicKey) {
        this.topicKey = topicKey;
        this.topicName = formatTopicName(topicKey);
    }

    private String formatTopicName(String rawKey) {
        String name = rawKey.replace('_', ' ').replace('-', ' ');
        if (name.length() > 0) {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name;
    }

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); mContext = context; }
    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.m002_frg_story, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View v) {
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(v1 -> backToM001Screen());
        ((TextView) v.findViewById(R.id.tv_name)).setText(topicName);

        RecyclerView rcv = v.findViewById(R.id.rcv_story);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));

        loadDataFromStrings(topicKey);

        StoryAdapter adapter = new StoryAdapter(listStory, mContext);
        rcv.setAdapter(adapter);
    }

    private void loadDataFromStrings(String key) {
        String packageName = mContext.getPackageName();

        int nameResId = mContext.getResources().getIdentifier(
                key + "_names", "array", packageName);

        int contentResId = mContext.getResources().getIdentifier(
                key + "_contents", "array", packageName);

        if (nameResId == 0 || contentResId == 0) {
            Toast.makeText(mContext, "Lỗi: Không tìm thấy dữ liệu truyện trong strings.xml cho key: " + key, Toast.LENGTH_LONG).show();
            return;
        }

        String[] names = mContext.getResources().getStringArray(nameResId);
        String[] contents = mContext.getResources().getStringArray(contentResId);

        if (names.length != contents.length) {
            Toast.makeText(mContext, "Lỗi: Số lượng Tên và Nội dung không khớp!", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < names.length; i++) {
            listStory.add(new StoryEntity(topicName, names[i], contents[i]));
        }
    }

    private void backToM001Screen() {
        ((MainActivity) getActivity()).gotoM001Screen();
    }
}