package cn.itcast.gobang.Util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.LiaoTianXiaoXi;

public class LiaoTianAdapter extends BaseAdapter {
    Context context;
    List<LiaoTianXiaoXi> liaoTianXiaoXiList;
    private SetSiLiao setSiLiao;
    int layout;
    public LiaoTianAdapter(Context context, int layout,List<LiaoTianXiaoXi> list){
        this.context=context;
        this.layout=layout;
        liaoTianXiaoXiList=list;
    }

    public void setClickSetSiLiao(SetSiLiao setSiLiao){
        this.setSiLiao=setSiLiao;
    }

    public int getCount() {
        return liaoTianXiaoXiList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    private View setConverView(int position,View convertView){
        if(convertView==null){
            View view=View.inflate(context,layout,null);
            convertView=view;
        }
        TextView textView=convertView.findViewById(R.id.five_liaotian_item_textview);
        ImageView imageView=convertView.findViewById(R.id.five_liaotian_item_imageview);
        textView.setTextColor(context.getResources().getColor(R.color.white));

        if(!liaoTianXiaoXiList.get(position).wenzi.equals(" ")&&liaoTianXiaoXiList.get(position).wenzi!=null) {
            if(liaoTianXiaoXiList.get(position).getWenzi().substring(0,3).equals("[私]")){
                //"[私]"+clientClass.name+":"+strings1[2]
                Log.e("Six","wenzi="+liaoTianXiaoXiList.get(position).wenzi+"Image="+liaoTianXiaoXiList.get(position).biaoqing);
                textView.setText(liaoTianXiaoXiList.get(position).wenzi);
                textView.setTextColor(context.getResources().getColor(R.color.green));
                textView.setVisibility(View.VISIBLE);
            }else{
                textView.setText(liaoTianXiaoXiList.get(position).wenzi);
                textView.setVisibility(View.VISIBLE);
            }
        }else{
            textView.setText("");
            textView.setVisibility(View.GONE);
        }
        if(liaoTianXiaoXiList.get(position).biaoqing!=0) {

            imageView.setImageResource(liaoTianXiaoXiList.get(position).biaoqing);
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.GONE);
        }

        String[] strings=liaoTianXiaoXiList.get(position).getWenzi().split(":");
        if(!strings[0].substring(3).equals(GongGongZiYuan.client.name)){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] strings=liaoTianXiaoXiList.get(position).getWenzi().split(":");
                    String s="@"+strings[0].substring(3)+":";
                    setSiLiao.setSiLiaoSendExit(s);
                }
            });
        }
        return convertView;
    }



     public interface SetSiLiao{
             void setSiLiaoSendExit(String s);
     }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setConverView(position,convertView);
    }
}
