package cn.itcast.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.SiXin;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.Util.WriterThread;

public class ThirdlyActivity extends AppCompatActivity {
    String MESSAGE_DUIZHAN="3-4";
    Button duizhan;
    Handler whandler;
    GongGongZiYuan gongGongZiYuan;
    ReceiveListener receiveListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdly);
        ReceiveListener();
        initView();
        setDuizhan();
    }

    private void initView(){
        whandler= WriterThread.wHandler;
        duizhan=(Button) findViewById(R.id.three_duizhan);
        gongGongZiYuan=new GongGongZiYuan();
    }

    private void setDuizhan(){
        duizhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message();
                msg.obj=MESSAGE_DUIZHAN+ "_";
                whandler.sendMessage(msg);
            }
        });
    }

    private void ReceiveListener(){
        SocketClient.sInst.addListener( receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                String[] strings=data.split("/n");
                if(strings[0].equals(MESSAGE_DUIZHAN)){
                    Intent intent=new Intent(ThirdlyActivity.this,FourthActivity.class);
                    startActivity(intent);
                }else if(strings[0].equals("tuichuyouxi:")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ThirdlyActivity.this, strings[1], Toast.LENGTH_SHORT).show();
                        }
                    });
                    SocketClient.sInst.setEnd();
                    if(SocketClient.sInst!=null){
                        SocketClient.sInst.allDestoryListener();
                    }
                    SocketClient.sInst=null;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
//        Log.e("ThirdlyActivity","onBackPressed");
//        gongGongZiYuan.sendMsg("wanquantuichu:_");
//        GongGongZiYuan.notClientIsRun=true;
//        SocketClient.sInst=null;
        super.onBackPressed();
    }



    @Override
    protected void onStop() {
//        Log.e("ThirdlyActivity","onStop");
//        gongGongZiYuan.sendMsg("wanquantuichu:_");
//        GongGongZiYuan.notClientIsRun=true;
//        SocketClient.sInst=null;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("ThirdlyActivity","onDestory");
        gongGongZiYuan.sendMsg("wanquantuichu:_");

        super.onDestroy();
    }

}