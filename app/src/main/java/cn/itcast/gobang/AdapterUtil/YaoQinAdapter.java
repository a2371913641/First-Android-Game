package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
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

public class YaoQinAdapter extends BaseAdapter {
    Context context;
    List<Client> names;
    View xuanzeView;
    GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();
    public YaoQinAdapter(Context context,List<Client> names){
        this.context=context;
        this.names=names;

    }

    @Override
    public int getCount() {
        return names.size();
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
            Log.e("Six","点击");
            YaoQinAdapter.this.notifyDataSetChanged();
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
        xuanzeView=View.inflate(context,R.layout.layout_yaoqing_xuanze,null);
        if(converView==null) {
            View view = View.inflate(context, R.layout.layout_haoyou_item,null);
            TextView name=(TextView) view.findViewById(R.id.four_haoyou_name);
//            name.getLayoutParams().width=LinearLayout.LayoutParams.MATCH_PARENT;
//            name.setLayoutParams(name.getLayoutParams());
            name.setText(names.get(position).getName());
            LinearLayout layout=view.findViewById(R.id.four_haoyou_xuanze);
            if(isExpand(position)){
                layout.addView(setXuanZeView(xuanzeView,position));
            }
            converView=view;
        }else {
            TextView name=(TextView) converView.findViewById(R.id.four_haoyou_name);
            name.setText(names.get(position).getName());
            LinearLayout layout=converView.findViewById(R.id.four_haoyou_xuanze);
            if(isExpand(position)&&layout.getChildCount()==0){
                layout.addView(setXuanZeView(xuanzeView,position));
            }else if(!isExpand(position)&&layout.getChildCount()!=0){
                layout.removeAllViews();
            }
        }
        return converView;
    }

    private View setXuanZeView(View xuanzeView,int position){

        Button siLiao=xuanzeView.findViewById(R.id.yaoqing_xuanze_siliao);
        Button siXin=xuanzeView.findViewById(R.id.yaoqing_xuanze_sixin);
        Button yaoQin=xuanzeView.findViewById(R.id.yaoqing_xuanze_yaoqing);

        siLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiLiao:/n"+names.get(position).getZhanghao()+"_");
            }
        });

        yaoQin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientYaoQin:/n"+names.get(position).getZhanghao()+"_");
            }
        });

        siXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiXin:/n"+names.get(position).getZhanghao()+"_");
            }
        });



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
