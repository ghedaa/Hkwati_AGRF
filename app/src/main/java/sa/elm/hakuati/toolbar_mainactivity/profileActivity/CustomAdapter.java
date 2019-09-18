package sa.elm.hakuati.toolbar_mainactivity.profileActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sa.elm.hakuati.toolbar_mainactivity.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<CustomPojo> list_members=new ArrayList<>();
    private final LayoutInflater inflater;
    View view;
    MyViewHolder holder;
    private Context context;





    public CustomAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    //This method inflates view present in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.profile_vistor_row, parent, false);
        holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CustomPojo list_items=list_members.get(position);
        holder.user_name.setText(list_items.getName());
        holder.content.setText(list_items.getContent());

   Glide.with(context).load(list_items.getImg()).into(holder.icon);

    }

    //Setting the arraylist
    public void setListContent(ArrayList<CustomPojo> list_members){
        this.list_members=list_members;
      notifyItemRangeChanged(0,list_members.size());

    }

    @Override
    public int getItemCount() {
        return list_members.size();
    }




    public void removeAt(int position) {
        list_members.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, list_members.size());
    }



    //Step1
    public interface StudentAdapterListener {
        void onStudentItemClick(String name);
    }

    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name,content;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);

            user_name=(TextView)itemView.findViewById(R.id.title1);
            content=(TextView)itemView.findViewById(R.id.title2);
            icon=(ImageView)itemView.findViewById(R.id.icon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }

            });
        }

    }

}

