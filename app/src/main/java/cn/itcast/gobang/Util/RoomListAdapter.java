package cn.itcast.gobang.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.itcast.gobang.R;

public class RoomListAdapter extends BaseAdapter {
    List<Room> roomList;
    Context context;
    GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();

    public RoomListAdapter(Context context,List<Room> roomList){
        this.context=context;
        this.roomList=roomList;
    }
    @Override
    public int getCount() {
        return roomList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private View setConverView(int position, View convertView){
        if(convertView==null){
            View view=View.inflate(context, R.layout.layout_five_room_item,null);
            convertView=view;
        }
        TextView roomHaoMa=(TextView) convertView.findViewById(R.id.five_room_haoma_textview);
        TextView roomName=(TextView) convertView.findViewById(R.id.five_room_name_textview);
        TextView roonType=(TextView) convertView.findViewById(R.id.five_room_type);
        Button jinru=(Button) convertView.findViewById(R.id.five_room_jinru_button);
        Log.e("RoomListAdapter","room.haoma="+roomList.get(position).roomHaoMa);
        roomHaoMa.setText(""+roomList.get(position).roomHaoMa);
        roomName.setText(roomList.get(position).roomName);
        roonType.setText(roomList.get(position).roomType);
        jinru.setOnClickListener(new JinRu());
        return convertView;
    }

    class JinRu implements View.OnClickListener {
        int position;
        View view=View.inflate(context,R.layout.layout_admin_exit,null);
        String newAdmin;

        AlertDialog dialog=new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=view.findViewById(R.id.five_jinruroom_mima_exittext);
                        newAdmin=editText.getText().toString();
                        if(newAdmin.equals(roomList.get(position).roomAdmin)){
                            gongGongZiYuan.sendMsg("buttonJinru:/n"+roomList.get(position).getRoomHaoMa()+"_");
                        }else{
                            Toast.makeText(context, "密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create();

        @Override
        public void onClick(View v) {
            if(roomList.get(position).roomAdmin.equals("/?")){
                gongGongZiYuan.sendMsg("buttonJinru:/n"+roomList.get(position).getRoomHaoMa()+"_");
            }else {
                dialog.show();
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setConverView(position,convertView);
    }
}
