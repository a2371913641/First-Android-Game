package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;

public class RoomClientRecycleAdapter extends RecyclerView.Adapter<RoomClientRecycleAdapter.RoomClientHolder> {
    String TAG="RoomClientRecycleAdapter";
    Context context;
    List<Client> clientList;
    private ClickInterface clickInterface;


    //点击事件
    public void setOnclick(ClickInterface clickInterface){
        this.clickInterface=clickInterface;
    }

    public interface ClickInterface{
        void onImageClick(int position);
    }

    //点击事件

    public RoomClientRecycleAdapter(Context context,List<Client> clients){
        this.context=context;
        this.clientList=clients;
    }


    static class RoomClientHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView clientImageView;
        TextView nameTextView,clicntState,setTeam;
        public RoomClientHolder(@NonNull View itemView) {
            super(itemView);
            this.view=itemView;
            this.clientImageView=(ImageView) view.findViewById(R.id.six_room_client_Image);
            this.nameTextView=(TextView) view.findViewById(R.id.six_room_client_name);
            this.clicntState=(TextView) view.findViewById(R.id.six_room_client_state);
            this.setTeam=(TextView) view.findViewById(R.id.six_room_client_team);
        }
    }

    @Override
    public RoomClientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.layout_six_room_client,null);
        RoomClientHolder holder= new RoomClientHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomClientHolder holder, int position) {
//        Client nowClient=null;
//        for(Client client:clientList) {
//        if(client.getSeat()==position)
//        }
        holder.nameTextView.setText(clientList.get(position).getName());
        holder.clientImageView.setImageResource(clientList.get(position).getXinbieImage());
        holder.clicntState.setText(clientList.get(position).getClientState());
        holder.setTeam.setText(clientList.get(position).getTeam());
        Log.e(TAG,"外getTeam()="+clientList.get(position).getTeam());
//        判断字符串是否为空用长度
        if(clientList.get(position).getClientState()!=null){
            Log.e(TAG,"clicntState.getText().length()="+holder.clicntState.getText().length()+";clicntState.getText()="+holder.clicntState.getText());
            holder.clicntState.setBackgroundColor(context.getResources().getColor(R.color.yellow1));
        } else {
            //如果是null,则取消背景颜色
            holder.clicntState.setBackgroundColor(context.getResources().getColor(R.color.lucency));
        }

        if(clientList.get(position).getTeam()!=null) {
            Log.e(TAG,"getTeam="+clientList.get(position).getTeam());
            switch (clientList.get(position).getTeam()) {
                case "红队":
                    holder.setTeam.setBackgroundColor(context.getResources().getColor(R.color.red));
                    break;
                case "蓝队":
                    holder.setTeam.setBackgroundColor(context.getResources().getColor(R.color.blue));
                    break;
                case "黄队":
                    holder.setTeam.setBackgroundColor(context.getResources().getColor(R.color.yellow1));
                    break;
            }
        }else{
            Log.e(TAG,"getTeam==null");
            //如果是null,则取消背景颜色
            holder.setTeam.setBackgroundColor(context.getResources().getColor(R.color.lucency));
        }

        Log.e("Six","clientList.get(position).getImage()="+clientList.get(position).getXinbieImage());
        holder.clientImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ClientAdapter","OnClick");
                if(clickInterface!=null){
                    Log.e("ClientAdapter","clickInterface!=null");
                    clickInterface.onImageClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return clientList.size();
    }


}
