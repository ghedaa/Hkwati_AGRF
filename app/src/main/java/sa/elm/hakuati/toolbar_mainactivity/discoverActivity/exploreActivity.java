package sa.elm.hakuati.toolbar_mainactivity.discoverActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import sa.elm.hakuati.toolbar_mainactivity.R;

public class exploreActivity extends AppCompatActivity {
    private fragmentadapter adapter;
    private fragmentadapter adapter2;
    private ViewPager viewPager;
    private ViewPager viewPager2;
    private AllStories allStories;
    private PopularStories popularStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_activity_main);

        allStories=new AllStories();
        popularStories=new PopularStories();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager2= (ViewPager) findViewById(R.id.viewPager2);
        adapter = new fragmentadapter(getSupportFragmentManager());
        adapter2=new  fragmentadapter(getSupportFragmentManager());
        adapter2.addFragment(allStories);
        adapter.addFragment(popularStories);
        viewPager.setAdapter(adapter);
        viewPager2.setAdapter(adapter2);

    }
}
