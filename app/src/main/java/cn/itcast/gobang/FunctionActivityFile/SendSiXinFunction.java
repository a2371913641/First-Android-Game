package cn.itcast.gobang.FunctionActivityFile;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


    public SendSiXinFunction(Context context,GongGongZiYuan gongGongZiYuan,Activity activity){
        this.context=context;
        this.gongGongZiYuan=gongGongZiYuan;
        this.activity=activity;

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

                    Toast.makeText(context,"请输入内容！",Toast.LENGTH_SHORT).show();


                }else{
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    gongGongZiYuan.sendMsg("ClientSendSiXin:/n"+name+"/n"+sixin_editText.getText()+"/n"+formatter.format(date)+"_");
                    GongGongZiYuan.siXins.add(new SiXin("To",name,sixin_editText.getText().toString(),formatter.format(date)));
                    IOUtil io=new IOUtil();
                    io.outputFile(new File(context.getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),"To"+"/n"+name+"/n"+sixin_editText.getText().toString()+"/n"+formatter.format(date)+"/n",true);

                    Toast.makeText(context,"发送成功！",Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }
            }
        });


    }


}
