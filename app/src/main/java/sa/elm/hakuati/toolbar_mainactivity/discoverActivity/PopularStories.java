package sa.elm.hakuati.toolbar_mainactivity.discoverActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

import sa.elm.hakuati.toolbar_mainactivity.mainActivity.Item;
import sa.elm.hakuati.toolbar_mainactivity.R;

public class PopularStories extends Fragment {


    private RecyclerView recyclerView;
    private smallerStoryAdapter adapter;
    private List<Item> itemList;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_popular_stories, container, false);
        final FragmentActivity fragmentBelongActivity = getActivity();

    recyclerView = view.findViewById(R.id.recycler_view);
    itemList = new ArrayList<>();
    adapter = new smallerStoryAdapter(fragmentBelongActivity,itemList);
    mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(adapter);
    itemList();

        return view;
    }

    private void itemList() {
        int[] covers = new int[]{
                R.drawable.story_one,
                R.drawable.story_two,
                R.drawable.story_three,
                R.drawable.story_four,
        };


        Item a =new Item("انبتهي يا جود",covers[0]);
        itemList.add(a);
        a =new Item("انبتهي يا جود",covers[1]);
        itemList.add(a);
        a =new Item("انبتهي يا جود",covers[2]);
        itemList.add(a);
        a =new Item("انبتهي يا جود",covers[3]);
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



}
