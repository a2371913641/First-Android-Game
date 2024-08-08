package cn.itcast.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.Util.WriterThread;

public class SecondActivity extends AppCompatActivity {

    EditText zhanghao,name,mima,queren;
    CheckBox boy,girl;
    Button back,zhuce;
    Handler whandler;
    GongGongZiYuan gongGongZiYuan;
    ReceiveListener receiveListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        setZhuce();
        Back();
    }

    private void initView(){
        whandler= WriterThread.wHandler;
        zhanghao=(EditText) findViewById(R.id.two_nuber);
        mima=(EditText) findViewById(R.id.two_admin);
        name=(EditText) findViewById(R.id.two_name);
        queren=(EditText) findViewById(R.id.two_queren_admin);
        boy=(CheckBox) findViewById(R.id.two_Boy);
        girl=(CheckBox) findViewById(R.id.two_Girl);
        back=(Button) findViewById(R.id.button_two_Back);
        zhuce=(Button) findViewById(R.id.button_two_Register);

    }

    private void setZhuce(){
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SocketClient.sInst==null){
                    SocketClient.sInst=new SocketClient();
                    SocketClient.sInst.start();

                }
                setReceiveListener();
                if(zhanghao.getText().length()>6&&mima.getText().length()>5&&name.getText().length()>1&&(boy.isChecked()||girl.isChecked())){
                    if(mima.getText().toString().equals(queren.getText().toString())){
                       if(boy.isChecked()){
                           Message msg=new Message();
                           msg.what=2;
                           msg.obj="zhuce:/n"+name.getText().toString()+"/n"+zhanghao.getText().toString()+"/n"+mima.getText().toString()+"/n"+"boy"+"/n"+R.mipmap.nan+ "_";
                           whandler.sendMessage(msg);
                       }else if(girl.isChecked()){
                           Message msg=new Message();
                           msg.what=2;
                           msg.obj="zhuce:/n"+name.getText().toString()+"/n"+zhanghao.getText().toString()+"/n"+mima.getText().toString()+"/n"+"girl"+"/n"+R.mipmap.nv+ "_";
                           whandler.sendMessage(msg);
                       }
                    }else{
                        Toast.makeText(SecondActivity.this,"两次密码不相同！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SecondActivity.this,"账号必须7为数以上，密码6位数以上，昵称必须两位以上，请选择性别！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setReceiveListener(){
        SocketClient.sInst.addListener(receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] strings=data.split("/n");
                        if(strings[0].equals("zhucejieguo:")){
                            Toast.makeText(SecondActivity.this,strings[1],Toast.LENGTH_SHORT).show();
                        }
                        SocketClient.sInst.setEnd();
                        SocketClient.sInst=null;
                    }
                });
            }
        });
    }

    private void Back(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(SocketClient.sInst!=null){
            SocketClient.sInst.destroyLintener(receiveListener);
        }
        super.onDestroy();
    }
}