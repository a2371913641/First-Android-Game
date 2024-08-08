package cn.itcast.gobang.AdapterUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.WriterThread;

public class DaTingAdapter<Content> extends BaseAdapter {
    Context context;
    int[] datings;
    Handler whandler= WriterThread.wHandler;

    public DaTingAdapter(Context context, int[] datins){
        this.context=context;
        this.datings=datins;
    }

    @Override
    public int getCount() {
        return datings.length;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    class JinRuDaTin implements View.OnClickListener{

        int position;

        public JinRuDaTin(int position){
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            if(isFull(position)){
                isFullDialog(true,position);
            }else {
                Message msg = new Message();
                msg.obj = "jinrudating:/n" + position+ "_";
                whandler.sendMessage(msg);
                Log.e("FourActivity", "Msg.obj=" + msg.obj);
                DaTingAdapter.this.notifyDataSetChanged();
            }
        }
    }
    public void isFullDialog(boolean b,int position){
        if(b){
            AlertDialog.Builder fullDialog=new AlertDialog.Builder(context);
            fullDialog.setTitle("大厅"+position+"已爆满，请更换大厅！")
                    .setPositiveButton("确定",null)
                    .create()
                    .show();
        }
    }

    public boolean isFull(int position){
        return datings[position]>99;
    }
    public View showItem(int position,View convertView){
        if(convertView==null){
            View view=View.inflate(context, R.layout.layout_dating_item,null);
            convertView=view;
        }
        TextView datingrenshu=convertView.findViewById(R.id.dating_item_show_textview);
        datingrenshu.setText(datings[position]+"/100");
        return convertView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.展示条目
        //3.点击事件(在活动里写）
        convertView=showItem(position,convertView);
        Button jinruBtn=(Button)convertView.findViewById(R.id.four_dating_jinru_button);
        jinruBtn.setOnClickListener(new JinRuDaTin(position));
        return convertView;
    }


}
