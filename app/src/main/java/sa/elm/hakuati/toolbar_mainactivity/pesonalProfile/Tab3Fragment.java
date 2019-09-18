package sa.elm.hakuati.toolbar_mainactivity.pesonalProfile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import sa.elm.hakuati.toolbar_mainactivity.discoverActivity.AllStories;
import sa.elm.hakuati.toolbar_mainactivity.discoverActivity.PopularStories;
import sa.elm.hakuati.toolbar_mainactivity.discoverActivity.fragmentadapter;
import sa.elm.hakuati.toolbar_mainactivity.mainActivity.Item;
import sa.elm.hakuati.toolbar_mainactivity.R;
import sa.elm.hakuati.toolbar_mainactivity.discoverActivity.smallerStoryAdapter;

public class Tab3Fragment extends Fragment {
    private Button playList;
    private RecyclerView recyclerView;
    private smallerStoryAdapter adapter;
    private List<Item> itemList;
    private RecyclerView.LayoutManager mLayoutManager;
    private ViewPager viewPager;
    private fragmentadapter adapterV;
    private TextView playlistTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_personal_play_lis_frag, container, false);
        final FragmentActivity fragmentBelongActivity = getActivity();

        recyclerView = view.findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();
        adapter = new smallerStoryAdapter(fragmentBelongActivity, itemList);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        itemList();
        playlistTitle = view.findViewById(R.id.secondPlayList);


        playList= view.findViewById(R.id.addPlayList);
        playList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ادخل اسم القائمة");
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("ادخل اسم القائمة");
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText messageIN = new EditText(getActivity());
                layout.addView(messageIN);
                alert.setView(layout);
                // Set up the buttons
                alert.setPositiveButton("اضف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playlistTitle.setText(messageIN.getText().toString()+"kkkk");
                    }
                });
                alert.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();

            }
        });



        return view;
    }

    private void itemList() {
        int[] covers = new int[]{
                R.drawable.story_one,
                R.drawable.story_two,
                R.drawable.story_three,
                R.drawable.story_four,
        };


        Item a = new Item("انبتهي يا جود", covers[0]);
        itemList.add(a);
        a = new Item("انبتهي يا جود", covers[1]);
        itemList.add(a);
        a = new Item("انبتهي يا جود", covers[2]);
        itemList.add(a);
        a = new Item("انبتهي يا جود", covers[3]);
        itemList.add(a);
        Item a1 = new Item("أخي زيد", covers[0]);
        itemList.add(a1);
        a1 = new Item("ليس بعد !", covers[1]);
        itemList.add(a1);
        a1 = new Item("جود ودراجتها الجديدة", covers[2]);
        itemList.add(a1);
        a1 = new Item("انبتهي يا جود", covers[3]);
        itemList.add(a1);

    }

    private void showDialog(){


    }


}