package cn.itcast.gobang.Util;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.SixRoomActivity;


public class GongGongZiYuan {
    String XINXIANG="的信箱.txt";
    Handler whandler=WriterThread.wHandler;
    public static Client client;
    public ArrayList<ClientAccount> clientAccounts=new ArrayList<>();
    public static List<SiXin> siXins=new ArrayList<>();
    public static Room room;



    public GongGongZiYuan(){

    }

    public void sendMsg(String data){
        Message msg=new Message();
        msg.obj=data;
        whandler.sendMessage(msg);
    }

    public void setSixinDialogView(Context context, View sixinDialogView, AlertDialog alertDialog, String name){
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
                    sendMsg("ClientSendSiXin:/n"+name+"/n"+sixin_editText.getText()+"/n"+formatter.format(date)+"_");
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
