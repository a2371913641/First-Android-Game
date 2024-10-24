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


public class ThirdlyActivity extends AppCompatActivity {
    String MESSAGE_DUIZHAN="3-4";
    Button duizhan;
    GongGongZiYuan gongGongZiYuan;
    ReceiveListener receiveListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdly);
//        ReceiveListener();
        initView();
        setDuizhan();
    }

    private void initView(){
        duizhan=(Button) findViewById(R.id.three_duizhan);
        gongGongZiYuan=new GongGongZiYuan();
    }

    private void setDuizhan(){
        duizhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              gongGongZiYuan.sendMsg(MESSAGE_DUIZHAN+ "_");
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ReceiveListener();
        Log.e("Thirdly","onResume");
    }

    private void ReceiveListener(){
        SocketClient.getInst().addListener( receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                String[] strings=data.split("/n");
                if(strings[0].equals(MESSAGE_DUIZHAN)){
                    Intent intent=new Intent(ThirdlyActivity.this,FourthActivity.class);
                    startActivity(intent);
                    Log.e("Thirdly","intent3-4");
                }else if(strings[0].equals("tuichuyouxi:")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ThirdlyActivity.this, strings[1], Toast.LENGTH_SHORT).show();
                        }
                    });
                    SocketClient.getInst().setEnd();
                    SocketClient.getInst().allDestoryListener();

                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("ThirdlyActivity","onDestory");
        gongGongZiYuan.sendMsg("wanquantuichu:_");

        super.onDestroy();
    }

}