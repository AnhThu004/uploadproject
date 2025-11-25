package com.example.a1150070042_lethianhthu_lab4;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;

public class M003DetailStoryFrg extends Fragment {
    private Context mContext;
    private ArrayList<StoryEntity> listStory;
    private StoryEntity currentStory;

    public void setListStory(ArrayList<StoryEntity> listStory) { this.listStory = listStory; }
    public void setCurrentStory(StoryEntity currentStory) { this.currentStory = currentStory; }

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); mContext = context; }
    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.m003_frg_detail_story, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View v) {
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(v1 -> backToM002Screen());
        ((TextView) v.findViewById(R.id.tv_name)).setText(currentStory.getTopicName());

        ViewPager vp = v.findViewById(R.id.vp_story);
        DetailStoryAdapter adapter = new DetailStoryAdapter(listStory, mContext);
        vp.setAdapter(adapter);
        vp.setCurrentItem(listStory.indexOf(currentStory), true);
    }

    private void backToM002Screen() {
        ((MainActivity) getActivity()).gotoM002Screen(currentStory.getTopicName());
    }
}
