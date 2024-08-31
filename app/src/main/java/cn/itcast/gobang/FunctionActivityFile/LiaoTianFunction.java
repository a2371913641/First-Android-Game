package cn.itcast.gobang.FunctionActivityFile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.AdapterUtil.BiaoQingBaoAdapter;
import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.LiaoTianAdapter;
import cn.itcast.gobang.Util.LiaoTianXiaoXi;

public class LiaoTianFunction {
    Context context;
    GongGongZiYuan gongGongZiYuan;
    List<Integer> biaoqingbaolist;
    View biaoqingbaoLayout;
    RecyclerView biaoqingbaoRecyclerView;
    AlertDialog.Builder biaoqingbaoDialog;
    BiaoQingBaoAdapter biaoQingBaoAdapter;
    LiaoTianAdapter liaoTianAdapter;
    View biaoqingbao,liaotian_layout;
    List<LiaoTianXiaoXi> liaoTianXiaoXiList;
    public LiaoTianFunction(Context context){
        this.context=context;
        gongGongZiYuan=new GongGongZiYuan();
        biaoqingbaolist=new ArrayList<>();
        setBiaoQingBaoList();
        biaoqingbaoLayout=View.inflate(context,R.layout.layout_five_baioqingbao_dialog,null);
        biaoqingbaoRecyclerView=(RecyclerView)biaoqingbaoLayout.findViewById(R.id.biaoqingbao_RecyclerList);
        liaotian_layout=View.inflate(context,R.layout.layout_five_dating_liaotian,null);
        biaoqingbao=(ImageView) liaotian_layout.findViewById(R.id.five_liaotian_biaoqingbao);
        biaoqingbaoDialog=new AlertDialog.Builder(context);
        biaoQingBaoAdapter=new BiaoQingBaoAdapter(context,biaoqingbaolist,"liaotianxiaoxi:");
        liaoTianXiaoXiList=new ArrayList<>();
        liaoTianAdapter=new LiaoTianAdapter(context,R.layout.layout_five_dating_liaotian_item,liaoTianXiaoXiList);
    }

    public void setSendLiaoTianButton(Button liaotianSend_Btn, EditText liaotianEditView) {
        liaotianSend_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = liaotianEditView.getText().toString();
                if (!data.equals("")) {
                    gongGongZiYuan.sendMsg("liaotianxiaoxi:/n"+data+"/n0"+"_");
                    liaotianEditView.setText("");

                }
            }
        });

        AlertDialog alertDialog=biaoqingbaoDialog.setView(biaoqingbaoLayout)
                .create();
        biaoqingbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void setBiaoQingBaoList(){
        for(int i=0;i<4;i++){
            biaoqingbaolist.add(R.mipmap.biaoqing1);
            biaoqingbaolist.add(R.mipmap.biaoqing2);
            biaoqingbaolist.add(R.mipmap.biaoqing3);
            biaoqingbaolist.add(R.mipmap.biaoqing4);
            biaoqingbaolist.add(R.mipmap.biaoqing5);
            biaoqingbaolist.add(R.mipmap.biaoqing6);
            biaoqingbaolist.add(R.mipmap.biaoqing7);
            biaoqingbaolist.add(R.mipmap.biaoqing8);

        }
    }
}
