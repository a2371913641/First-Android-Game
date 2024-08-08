package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.media.session.MediaController;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;

public class RoomClientRecycleAdapter extends RecyclerView.Adapter<RoomClientRecycleAdapter.RoomClientHolder> {
    Context context;
    List<Client> clientList;

    public RoomClientRecycleAdapter(Context context,List<Client> clients){
        this.context=context;
        this.clientList=clients;
    }


    class RoomClientHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView clientImageView;
        TextView nameTextView;
        public RoomClientHolder(@NonNull View itemView) {
            super(itemView);
            this.view=itemView;
            this.clientImageView=(ImageView) view.findViewById(R.id.six_room_client_Image);
            this.nameTextView=(TextView) view.findViewById(R.id.six_room_client_name);
        }
    }

    @Override
    public RoomClientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.layout_six_room_client,null);
        RoomClientHolder holder=new RoomClientHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomClientHolder holder, int position) {
        holder.nameTextView.setText(clientList.get(position).getName());
        holder.clientImageView.setImageResource(clientList.get(position).getImage());
        Log.e("Six","clientList.get(position).getImage()="+clientList.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return clientList.size();
    }


}
