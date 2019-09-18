package sa.elm.hakuati.toolbar_mainactivity.discoverActivity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sa.elm.hakuati.toolbar_mainactivity.mainActivity.Item;
import sa.elm.hakuati.toolbar_mainactivity.R;


public class AllStories extends Fragment {
    private RecyclerView recyclerView;
    private smallerStoryAdapter adapter;
    private List<Item> itemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_all_stories, container, false);

    recyclerView = view.findViewById(R.id.recycler_view);
    itemList = new ArrayList<>();
    adapter = new smallerStoryAdapter(getActivity(),itemList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(10), false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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






        adapter.notifyDataSetChanged();
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

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
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
