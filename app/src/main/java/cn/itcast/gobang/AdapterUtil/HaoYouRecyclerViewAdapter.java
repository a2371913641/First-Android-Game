package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.gobang.R;

public class HaoYouRecyclerViewAdapter extends RecyclerView.Adapter<HaoYouRecyclerViewAdapter.ViewHolder>{
    List haoyoulist;
    Context context;
    int position;
    Map<Integer,Integer> jishuqi=new HashMap<>();
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView name;
        LinearLayout layout;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.itemView=view;
            name=(TextView) view.findViewById(R.id.four_haoyou_name);
            layout=(LinearLayout)view.findViewById(R.id.four_tihuan);
        }
    }

    public HaoYouRecyclerViewAdapter(Context context,List list){
        this.context=context;
        this.haoyoulist=list;
    }

    @Override
    public HaoYouRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.layout_haoyou_item,null);
        final ViewHolder holder=new ViewHolder(view);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jishuqi.put(position,jishuqi.get(position)+1);
                if(jishuqi.get(position)%2==0&&jishuqi.get(position)!=0){
                    holder.layout.removeAllViews();
                }else if(jishuqi.get(position)%2==1){
                    View xuanze=View.inflate(context,R.layout.layout_haoyou_xuanze,null);
                    Log.e("RecyclerAdapter",holder.layout.toString());
                    holder.layout.addView(xuanze);
                }
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull HaoYouRecyclerViewAdapter.ViewHolder holder, int position) {
        this.position=position;
        holder.name.setText(haoyoulist.get(position).toString());
        jishuqi.put(position,0);
        if(jishuqi.get(position)%2==0&&jishuqi.get(position)!=0){
            holder.layout.removeAllViews();
        }else if(jishuqi.get(position)%2==1){
            View xuanze=View.inflate(context,R.layout.layout_haoyou_xuanze,null);
            holder.layout.addView(xuanze);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return haoyoulist.size();
    }


}