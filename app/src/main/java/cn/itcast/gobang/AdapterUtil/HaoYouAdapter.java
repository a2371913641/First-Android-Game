package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorLong;
import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;

public class HaoYouAdapter extends ShowClientListAdapter {
    int Layout=R.layout.layout_haoyou_xuanze;

    public HaoYouAdapter(Context context, List<Client> clients) {
        super(context, clients);
    }

    View showItem(int position, View converView){
        if(expandMap.get(position)==null){
            expandMap.put(position,false);
        }
        View xuanzeView=View.inflate(context,haoyou_xuanze,null);
        if(converView==null) {
            View view = View.inflate(context, R.layout.layout_haoyou_item,null);
            TextView name=(TextView) view.findViewById(R.id.four_haoyou_name);

            name.setText(clientList.get(position).getName());
            LinearLayout layout=view.findViewById(R.id.four_haoyou_xuanze);
            if(isExpand(position)&&clientList.get(position).getOnLine()){
                layout.addView(setXuanze(xuanzeView,position));
            }
            converView=view;
        }else {
            TextView name=(TextView) converView.findViewById(R.id.four_haoyou_name);
            name.setText(clientList.get(position).getName());
            LinearLayout layout=converView.findViewById(R.id.four_haoyou_xuanze);
            if(isExpand(position)&&layout.getChildCount()==0){
                layout.addView(setXuanze(xuanzeView,position));
            }else if(!isExpand(position)&&layout.getChildCount()!=0){
                layout.removeAllViews();
            }
        }
        if(!clientList.get(position).getOnLine()){
           converView.setBackgroundColor(context.getResources().getColor(R.color.grey));
        }else{
            converView.setBackgroundColor(context.getResources().getColor(R.color.lucency));
        }

        return converView;
    }

    public View setXuanze(View xuanzeView,int position){
        Button zhaoTa=xuanzeView.findViewById(R.id.four_haoyou_xuanze_zhaota);
        Button siLiao=xuanzeView.findViewById(R.id.four_haoyou_xuanze_siliao);
        Button siXin=xuanzeView.findViewById(R.id.four_haoyou_xuanze_sixin);

        zhaoTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gongGongZiYuan.sendMsg("ClientZiLiao:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });

        siLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiLiao:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });

        siXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiXin:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });

        if(!clientList.get(position).getOnLine()){
            zhaoTa.setVisibility(View.INVISIBLE);
            siLiao.setVisibility(View.INVISIBLE);
        }

        return xuanzeView;
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
