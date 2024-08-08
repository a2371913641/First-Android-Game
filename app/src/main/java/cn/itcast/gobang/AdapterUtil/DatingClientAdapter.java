package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;

public class DatingClientAdapter extends BaseAdapter {
    List<Client> clientList;
    Context context;
    public DatingClientAdapter(Context context,List<Client> list){
        this.context=context;
        this.clientList=list;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View setConverView(int position,View convertView){
        if(convertView==null){
            View view=View.inflate(context, R.layout.layout_five_client_item,null);
            convertView=view;
        }
        TextView name=convertView.findViewById(R.id.five_dating_client_name);
        name.setText(clientList.get(position).getName());
//        LinearLayout layout=convertView.findViewById(R.id.five_dating_client_xuanze);
//        Log.e("DatingClientAdapter","isAddChild("+position+")="+isAddChild(position));
//        if(isAddChild(position)){
//            View view=View.inflate(context,R.layout.layout_five_client_item_xuanze,null);
//            layout.addView(view);
//        }else {
//            layout.removeAllViews();
//        }
        return convertView;
    }

    class DJ implements View.OnClickListener{
        int position;
        View convertView;

        public DJ(int position,View convertView){
            this.position=position;
            this.convertView=convertView;
        }
        @Override
        public void onClick(View v) {
//           modifyExpand(position);
           Log.e("DatingClientAdapter","=========================");
            LinearLayout layout=convertView.findViewById(R.id.five_dating_client_xuanze);
            if(layout.getChildCount()==0){
                View view=View.inflate(context,R.layout.layout_five_client_item_xuanze,null);
                layout.addView(view);
            }else {
                layout.removeAllViews();
            }
        }
    }


//    Map<Integer,Boolean> expandMap=new HashMap<>();
//    //返回是否应该膨胀
//    private boolean isAddChild(int position){
//        if(expandMap.containsKey(position)){
//            return expandMap.get(position);
//        }else {
//            return false;
//        }
//    }
//
//    //修改对应position的item的展开状态
//    private void modifyExpand(int position){
//        expandMap.put(position,!isAddChild(position));
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=setConverView(position,convertView);
        TextView name=convertView.findViewById(R.id.five_dating_client_name);
        name.setOnClickListener(new DJ(position,convertView));
        return convertView;
    }
}
