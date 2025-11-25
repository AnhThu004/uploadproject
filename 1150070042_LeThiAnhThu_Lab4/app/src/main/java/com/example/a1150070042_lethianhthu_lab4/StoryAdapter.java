package com.example.a1150070042_lethianhthu_lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private final List<StoryEntity> listStory;
    private final Context mContext;

    public StoryAdapter(List<StoryEntity> listStory, Context mContext) {
        this.listStory = listStory;
        this.mContext = mContext;
    }

    @NonNull @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        StoryEntity item = listStory.get(position);
        holder.tvName.setText(item.getName());
        holder.tvContent.setText(item.getContent());
        holder.itemView.setTag(item);

        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> ((MainActivity) mContext)
                .gotoM003Screen((ArrayList<StoryEntity>) listStory, (StoryEntity) v.getTag()));
    }

    @Override public int getItemCount() { return listStory.size(); }

    static class StoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvContent;
        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_story_name);
            tvContent = itemView.findViewById(R.id.tv_story_content);
        }
    }
}