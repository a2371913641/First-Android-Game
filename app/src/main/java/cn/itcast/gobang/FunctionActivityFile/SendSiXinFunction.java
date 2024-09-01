package cn.itcast.gobang.FunctionActivityFile;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.itcast.gobang.AdapterUtil.XinXiangAdapter;
import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.IOUtil;
import cn.itcast.gobang.Util.SiXin;

public class SendSiXinFunction {
    String XINXIANG="的信箱.txt";
    IOUtil io=new IOUtil();
    Context context;
    GongGongZiYuan gongGongZiYuan;
    Activity activity;
    XinXiangAdapter xinXiangAdapter;
    ListView xinxiangListView;


    public SendSiXinFunction(Context context,GongGongZiYuan gongGongZiYuan,Activity activity,XinXiangAdapter xinXiangAdapter,ListView xinxiangListView){
        this.context=context;
        this.gongGongZiYuan=gongGongZiYuan;
        this.activity=activity;
        this.xinXiangAdapter=xinXiangAdapter;
        this.xinxiangListView=xinxiangListView;
    }


    public void setSiXinDialog(String adverseName){
        View view=View.inflate(context, R.layout.layout_sixin_dialogview,null);
        AlertDialog.Builder sixinDialogBuilder=new AlertDialog.Builder(context);
        sixinDialogBuilder.setView(view);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sixinDialogBuilder.create();
                AlertDialog alertDialog=sixinDialogBuilder.show();
                setSixinDialogView(view,alertDialog,adverseName);
            }
        });
    }

    public void jieshouSiXIn(String adverseName, String content, String time){
        GongGongZiYuan.siXins.add(new SiXin("From",adverseName,content,time));
        io.outputFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),"From"+"/n"+adverseName+"/n"+content+"/n"+time+"/n",true);
    }

    public void setSixinDialogView(View sixinDialogView, AlertDialog alertDialog, String name){
        Button sixin_qvxiao_button,sixin_send_button;
        EditText sixin_editText;
        TextView nameXinXiang=(TextView)sixinDialogView.findViewById(R.id.sixin_title);
        sixin_send_button=(Button) sixinDialogView.findViewById(R.id.sixin_dialog_send_button);
        sixin_editText=(EditText) sixinDialogView.findViewById(R.id.dialog_sixin_content_editText);
        sixin_qvxiao_button=(Button) sixinDialogView.findViewById(R.id.sixin_dialog_qvxiao_button);
        nameXinXiang.setText("发送私信给:"+name);
        sixin_qvxiao_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        sixin_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sixin_editText.getText().toString().equals("")){

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"请输入内容！",Toast.LENGTH_SHORT).show();
                        }
                    });


                }else{
                    Date date = new Date();
                    String time=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
                    sendSiXinToServer(name,sixin_editText.getText().toString(),time);
                    GongGongZiYuan.siXins.add(new SiXin("To",name,sixin_editText.getText().toString(),time));
                    updateXinxiangAdapter();
                    io.outputFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),"To"+"/n"+name+"/n"+sixin_editText.getText().toString()+"/n"+time+"/n",true);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"发送成功！",Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.dismiss();

                }
            }
        });
    }

    private void sendSiXinToServer(String name,String content,String time){

        gongGongZiYuan.sendMsg("ClientSendSiXin:/n"+name+"/n"+content+"/n"+time+"_");
    }


    public void updateXinxiangAdapter(){
        if(xinXiangAdapter!=null){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("SendSiXinFunction","xinXiangAdapter.notifyDataSetChanged()");
                    xinXiangAdapter.notifyDataSetChanged();
                    for(int i=0;i<xinXiangAdapter.siXins.size();i++) {
                        Log.e("SendSiXinFunction","SiXin["+i+"]="+xinXiangAdapter.siXins.get(i).getContent());
                    }
                    xinxiangListView.setSelection(GongGongZiYuan.siXins.size()-1);
                }
            });
        }
    }

    //从信箱文件中读出私信放入SiXin列表
    public void setSiXin(){
        String s=io.inputFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath());
        if(!s.isEmpty()){
            String[] strings=s.split("/n");

            for(int i=GongGongZiYuan.siXins.size()-1;i>=0;i--){
                GongGongZiYuan.siXins.remove(GongGongZiYuan.siXins.get(i));
            }
            for(int i=0;i<strings.length;i=i+4){
                GongGongZiYuan.siXins.add(new SiXin(strings[i],strings[i+1],strings[i+2],strings[i+3]));
            }
        }
    }

    //将xinxiangList全部读入File
    public void setXinXiangFile(){
        if(GongGongZiYuan.siXins.size()!=0){
            IOUtil io=new IOUtil();
            io.deleteFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath());
            for(SiXin siXin:GongGongZiYuan.siXins){
                io.outputFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),
                        siXin.getFromOrTo()+"/n"+siXin.getName()+"/n"+siXin.getContent()+"/n"+siXin.getTime()+"/n"
                        ,true);
            }
        }else{
            io.deleteFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath());
        }
        Log.e("SendSiXinFunction","xinxiang="+io.inputFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath()));
    }


}
