package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.WriterThread;

public class BiaoQingBaoAdapter extends RecyclerView.Adapter <BiaoQingBaoAdapter.BiaoQingHolder> {
    Context context;
    Handler whandler= WriterThread.wHandler;
    List list;
    String s;



    public BiaoQingBaoAdapter(Context context, List list,String s){
        this.context=context;
        this.list=list;
        this.s=s;
    }

    class BiaoQingHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        View view;
        public BiaoQingHolder(@NonNull View itemView) {
            super(itemView);
            this.view=itemView;
            imageView=(ImageView) view.findViewById(R.id.biaoqingbao_item);
        }
    }

    @Override
    public BiaoQingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.layout_five_baioqingbao_item,null);
        BiaoQingHolder holder=new BiaoQingHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Message msg=new Message();
                msg.obj=s+"/n"+" /n"+list.get(position)+"_";
                whandler.sendMessage(msg);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull BiaoQingHolder holder, int position) {
        holder.imageView.setImageResource((Integer) list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
