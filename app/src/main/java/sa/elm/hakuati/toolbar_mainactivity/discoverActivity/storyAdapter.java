package sa.elm.hakuati.toolbar_mainactivity.discoverActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sa.elm.hakuati.toolbar_mainactivity.mainActivity.Item;
import sa.elm.hakuati.toolbar_mainactivity.R;

public class storyAdapter extends RecyclerView.Adapter<storyAdapter.MyViewHolder> {
    private Context mContext;
    private List<Item> itemList;


    public class MyViewHolder extends RecyclerView.ViewHolder   {
        public TextView title, views,channelName;
        public ImageView image, channelImage,soundImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            views=itemView.findViewById(R.id.views);
            channelName=itemView.findViewById(R.id.channelname);
            image=itemView.findViewById(R.id.storyimage);
            channelImage=itemView.findViewById(R.id.channelimage);
            soundImage=itemView.findViewById(R.id.soundimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(mContext,exploreActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public storyAdapter(Context mContext, List<Item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    public storyAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activit_main_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.views.setText(item.getViews());
        holder.channelName.setText(item.getChannelName());
        // loading album cover using Glide library
        Glide.with(mContext).load(item.getImage()).into(holder.image);
        Glide.with(mContext).load(item.getChannelImage()).into(holder.channelImage);
        Glide.with(mContext).load(item.getSoundImage()).into(holder.soundImage);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
