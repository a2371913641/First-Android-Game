package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorLong;

import java.util.HashMap;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;

public class HaoYouAdapter extends BaseAdapter {
    Context context;
    List<Client> clientList;
    public HaoYouAdapter(Context context,List<Client> clients){
        this.context=context;
        this.clientList=clients;

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
        return position;
    }

    class IL implements View.OnClickListener {
        int position;
        IL(int p ) {
            position = p;
        }
        @Override
        public void onClick(View v) {
            modifyExpand(position);
            HaoYouAdapter.this.notifyDataSetChanged();
        }
    }


    HashMap<Integer, Boolean> expandMap = new HashMap<>();
    // 返回position对应的item是否展开
    boolean isExpand(int position) {
        if (expandMap.containsKey(position)) {
            return expandMap.get(position);
        } else {
            return false;
        }
    }

    // 修改position对应的item的展开状态
    void modifyExpand(int position) {
        expandMap.put(position,!isExpand(position));
    }

    View showItem(int position,View converView){
        if(expandMap.get(position)==null){
            expandMap.put(position,false);
        }
        View xuanzeView=View.inflate(context,R.layout.layout_haoyou_xuanze,null);
        if(converView==null) {
            View view = View.inflate(context, R.layout.layout_haoyou_item,null);
            TextView name=(TextView) view.findViewById(R.id.four_haoyou_name);

            name.setText(clientList.get(position).getName());
            LinearLayout layout=view.findViewById(R.id.four_haoyou_xuanze);
            if(isExpand(position)){
                layout.addView(xuanzeView);
            }
            converView=view;
        }else {
            TextView name=(TextView) converView.findViewById(R.id.four_haoyou_name);
            name.setText(clientList.get(position).getName());
            LinearLayout layout=converView.findViewById(R.id.four_haoyou_xuanze);
            if(isExpand(position)&&layout.getChildCount()==0){
                layout.addView(xuanzeView);
            }else if(!isExpand(position)&&layout.getChildCount()!=0){
                layout.removeAllViews();
            }
        }
        if(!clientList.get(position).getOnLine()){
           converView.setBackgroundColor(context.getResources().getColor(R.color.grey));
        }

        return converView;
    }

    @Override // position = 5
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. 展示 - 根据条目状态展示
        // 2. 点击事件 - 更新条目状态 -  更新position对应的这个条目是否展开

        // 1. 展示haoyou_item
        // 2. 设置TextView
        // 3. 根据点击次数设置是否动态添加view
       convertView = showItem(position, convertView);
       convertView.setOnClickListener(new IL(position));
       return convertView;
    }
}
