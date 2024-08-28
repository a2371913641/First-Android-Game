package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;

public class ShowClientListAdapter extends BaseAdapter {
    Context context;
    List<Client> clientList;
    GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();
    int haoyou_xuanze=R.layout.layout_haoyou_xuanze;
    public ShowClientListAdapter(Context context, List<Client> clients){
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
            ShowClientListAdapter.this.notifyDataSetChanged();
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

    View showItem(int position, View converView) {
        return null;
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
