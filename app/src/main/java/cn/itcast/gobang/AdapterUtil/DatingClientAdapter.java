package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;

public class DatingClientAdapter extends BaseAdapter {
    List<Client> clientList;
    Context context;
    GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();
    public DatingClientAdapter(Context context,List<Client> list){
        this.context=context;
        this.clientList=list;
    }

    @Override
    public int getCount() {
        int result = clientList.size();
        Log.e("DatingClientAdapter", "size is " + result);
        return result;
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
        if(!positionMap.containsKey(position)){
            positionMap.put(position,false);
        }


        convertView= View.inflate(context, R.layout.layout_five_client_item,null);
        LinearLayout layout=convertView.findViewById(R.id.five_dating_client_xuanze);
        TextView name=convertView.findViewById(R.id.five_dating_client_name);
        name.setText(clientList.get(position).getName());
        if(isShow(position)&&layout.getChildCount()==0){
            Log.e("DatingClientAdapter", "addView to " + layout.hashCode() + " : position " + convertView.hashCode());
            Log.e("DatingClientAdapter",positionMap.get(position)+"layout.getChildCount="+layout.getChildCount());

            layout.addView(setXuanZeView(getXuanzeView(layout),position));
        }else if(!isShow(position)&&layout.getChildCount()!=0){
            Log.e("DatingClientAdapter",positionMap.get(position)+"layout.getChildCount="+layout.getChildCount());
            layout.removeAllViews();
        }
        return convertView;
        /**
         * 1
         * 2
         * 3
         *      xuanze
         * 4
         * 5
         *      xuanze
         */
    }

    HashMap<LinearLayout, View> xuanzeViews = new HashMap<>();
    View getXuanzeView(LinearLayout layout) {
        if(xuanzeViews.containsKey(layout)){
            return xuanzeViews.get(layout);
        }else {
            xuanzeViews.put(layout,View.inflate(context,R.layout.layout_five_client_item_xuanze,null));
            return xuanzeViews.get(layout);
        }
    }

    public Map<Integer,Boolean> positionMap=new HashMap<>();

    private Boolean isShow(int position){
        if(positionMap.containsKey(position)){
            return positionMap.get(position);
        }else{
            return false;
        }
    }
    class DJ implements View.OnClickListener{
        int position;

        public DJ(int position){
            this.position=position;
        }
        @Override
        public void onClick(View v) {
            Log.e("DatingClientAdapter","onClick");
            changePostion(position);
            DatingClientAdapter.this.notifyDataSetChanged();
        }
    }

    private void changePostion(int position){
        positionMap.put(position,!isShow(position));
    }

    private View setXuanZeView(View xuanzeView, int position){
        Button zhaoTa=xuanzeView.findViewById(R.id.five_dating_client_xuanze_zhaota);
        Button siLiao=xuanzeView.findViewById(R.id.five_dating_client_xuanze_siliao);
        Button siXin=xuanzeView.findViewById(R.id.five_dating_client_xuanze_sixin);
        Button addFiend=xuanzeView.findViewById(R.id.five_dating_client_xuanze_addFriend);
        Button ziliao=xuanzeView.findViewById(R.id.five_dating_client_xuanze_ziliao);

        siXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiXin:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });
        siLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiLiao:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });

        zhaoTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientZiLiao:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });

        if(clientList.get(position).getZhanghao().equals(GongGongZiYuan.client.getZhanghao())){
            siLiao.setVisibility(View.INVISIBLE);
            siXin.setVisibility(View.INVISIBLE);
            addFiend.setVisibility(View.INVISIBLE);
        }
        return xuanzeView;
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
        convertView.setOnClickListener(new DJ(position));

        return convertView;
    }
}
