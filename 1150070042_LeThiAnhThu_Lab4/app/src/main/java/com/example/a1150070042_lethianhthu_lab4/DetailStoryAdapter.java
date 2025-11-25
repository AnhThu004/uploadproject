package com.example.a1150070042_lethianhthu_lab4;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class DetailStoryAdapter extends PagerAdapter {
    private final List<StoryEntity> listStory;
    private final Context mContext;

    public DetailStoryAdapter(List<StoryEntity> listStory, Context mContext) {
        this.listStory = listStory;
        this.mContext = mContext;
    }

    @NonNull @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail_story, container, false);
        StoryEntity item = listStory.get(position);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvContent = view.findViewById(R.id.tv_content);

        tvName.setText(item.getName());
        tvContent.setText(item.getContent());

        container.addView(view);
        return view;
    }

    @Override public int getCount() { return listStory.size(); }
    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) { return view.equals(object); }
    @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}