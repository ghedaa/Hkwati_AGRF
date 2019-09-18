package sa.elm.hakuati.toolbar_mainactivity.mainActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sa.elm.hakuati.toolbar_mainactivity.R;
import sa.elm.hakuati.toolbar_mainactivity.audioRecord.RecordingActivity;
import sa.elm.hakuati.toolbar_mainactivity.discoverActivity.exploreActivity;
import sa.elm.hakuati.toolbar_mainactivity.innerStroeyActivity.InnerStoryActivity;
import sa.elm.hakuati.toolbar_mainactivity.innerStroeyActivity.Record;
import sa.elm.hakuati.toolbar_mainactivity.storyActivity.StoryActivity;
import sa.elm.hakuati.toolbar_mainactivity.pesonalProfile.PersonalProfileActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private storyAdapter adapter;
    private List<Item> itemList;
    private static CircularImageView  channelimage;
    private ImageView music1,music2,music3,bird;
    TextView textAppbar;
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent3 = new Intent(MainActivity.this, RecordingActivity.class); // from where? and to the distanation
                    startActivity(intent3); // to start another activity
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(MainActivity.this, MainActivity.class); // from where? and to the distanation
                    startActivity(intent2); // to start another activity
                    return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(MainActivity.this, exploreActivity.class); // from where? and to the distanation
                    startActivity(intent); // to start another activity
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // mTextMessage = findViewById(R.id.message);
       navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


       toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        //toolbar.bringToFront();
       //getSupportActionBar().setIcon(R.drawable.animal_elp);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();
        adapter = new storyAdapter(this, itemList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareItems();


        relativeLayout = findViewById(R.id.real);
        channelimage = findViewById(R.id.channelimage);

        relativeLayout.bringToFront();



        music1 = findViewById(R.id.music1);music2 = findViewById(R.id.music2);music3 = findViewById(R.id.music3);bird = findViewById(R.id.bird);
        textAppbar = findViewById(R.id.textapp);

        new Handler().postDelayed(new Runnable() { @Override public void run() {
            music1.setVisibility(View.VISIBLE); }}, 2000);
        new Handler().postDelayed(new Runnable() { @Override public void run() { music2.setVisibility(View.VISIBLE); }}, 3000);
        new Handler().postDelayed(new Runnable() { @Override public void run() { music3.setVisibility(View.VISIBLE); }}, 4000);
        new Handler().postDelayed(new Runnable() { @Override public void run() { textAppbar.setVisibility(View.VISIBLE); }}, 5000);


// Profile Intent:

        channelimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PersonalProfileActivity.class); // from where? and to the distanation
                startActivity(intent); // to start another activity
            }
        });



      //  initCollapsingToolbar();

    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);


        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                   channelimage.setVisibility(View.GONE);
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                   channelimage.setVisibility(View.VISIBLE);

                } else if (isShow) {
                  //  channelimage.setVisibility(View.VISIBLE);

                    collapsingToolbar.setTitle(" ");
                    isShow = false;

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mene, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;

            }

            @Override
            public boolean onQueryTextChange(String query) {

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }//end dpToPx

    private void prepareItems() {
        int[] covers = new int[]{
                R.drawable.story_one,
                R.drawable.story_two,
                R.drawable.story_three,
                R.drawable.story_four,
        };


        int[] pictures = new int[]{

                R.drawable.animal_elp,
                R.drawable.animal_p,
                R.drawable.animal_r,
                R.drawable.animal_z,
                R.drawable.animal_t,


        };

        int views = R.drawable.ic_play_circle_outline_black_24dp;


        Item a1 = new Item("أخي زيد", covers[0], pictures[0],  "12 ألف ", "غيداء العجاجي",views);
        itemList.add(a1);

        Item a2 = new Item("ليس بعد !", covers[1], pictures[1],  "10 آلاف ", "فاطمة القحطاني",views);
        itemList.add(a2);

        Item a3 = new Item("جود ودراجتها الجديدة", covers[2], pictures[2],"2 ألف ", "دار سلوى للنشر",views);
        itemList.add(a3);

        Item a4 = new Item("انبتهي يا جود", covers[3], pictures[3], "40 ألف ", "دار الوطن",views);
        itemList.add(a4);

        Item a5 = new Item("أخي زيد", covers[0], pictures[4],  "12 ألف ", "غيداء العجاجي",views);
        itemList.add(a5);

        Item a6 = new Item("ليس بعد !", covers[1], pictures[1],  "10 آلاف ", "فاطمة القحطاني",views);
        itemList.add(a6);

        Item a7 = new Item("جود ودراجتها الجديدة", covers[2], pictures[2],"2 ألف ", "دار سلوى للنشر",views);
        itemList.add(a7);

        Item a8 = new Item("انبتهي يا جود", covers[3], pictures[0], "40 ألف ", "دار الوطن",views);
        itemList.add(a8);


    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom

            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }//end inner class GridSpacingItemDecoration



}




