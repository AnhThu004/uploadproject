package com.example.a1150070042_lethianhthu_lab4;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFrg(new M000SplashFrg());
    }

    private void showFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ln_main, frg, frg.getClass().getName())
                .commit();
    }

    public void gotoM001Screen() {
        showFrg(new M001TopicFrg());
    }

    public void gotoM002Screen(String topicKey) {
        M002StoryFrg frg = new M002StoryFrg();
        frg.setTopicKey(topicKey);
        showFrg(frg);
    }

    public void gotoM003Screen(ArrayList<StoryEntity> listStory, StoryEntity story) {
        M003DetailStoryFrg frg = new M003DetailStoryFrg();
        frg.setListStory(listStory);
        frg.setCurrentStory(story);
        showFrg(frg);
    }
}