package cn.itcast.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.AdapterUtil.DaTingAdapter;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.AdapterUtil.HaoYouAdapter;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.Util.WriterThread;

public class FourthActivity extends AppCompatActivity {
    String CREATE_ACTIVITY_OK="fourlyActivityOK";
    Button haoyou,ziliao,xinxiang,dadang,jiazu,ziliaoHuanyijian,ziliaoZaixianjiangli;
    ImageButton ziliaoXinbie;
    TextView ziliaoJinyanzhi,ziliaoName;
    Handler whandler= WriterThread.wHandler;
    LinearLayout tiHuan;
    ListView haoyouListView,datingListView;
    DaTingAdapter daTingAdapter;
    GongGongZiYuan gongGongZiYuan;
    List<Client> haoyouList;
    int[] dating={0,0,0,0,0,0,0,0};
    View ziliaoView,haoyouView,dadiangView,xinxiangView,jiazuView;
    ReceiveListener receiveListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        addListener();
        initView();
        setTiHuan();
        sendMessage(CREATE_ACTIVITY_OK+ "_");
    }

    private void initView(){
        haoyouList=new ArrayList<Client>();
        haoyou=(Button) findViewById(R.id.four_haoyou);
        ziliao=(Button) findViewById(R.id.four_ziliao);
        xinxiang=(Button) findViewById(R.id.four_xinxiang);
        dadang=(Button)findViewById(R.id.four_dadang);
        jiazu=(Button) findViewById(R.id.four_jiazu);
        datingListView=(ListView) findViewById(R.id.four_listView);
        daTingAdapter=new DaTingAdapter(FourthActivity.this,dating);
        datingListView.setAdapter(daTingAdapter);
        tiHuan=(LinearLayout) findViewById(R.id.four_tihuan);
        ziliaoView=View.inflate(FourthActivity.this,R.layout.layout_four_ziliao,null);
        haoyouView=View.inflate(FourthActivity.this,R.layout.layout_four_haoyou,null);
        dadiangView=View.inflate(FourthActivity.this,R.layout.layout_four_dadang,null);
        xinxiangView=View.inflate(FourthActivity.this,R.layout.layout_four_xinxiang,null);
        jiazuView=View.inflate(FourthActivity.this,R.layout.layout_four_jiazu,null);
        ziliaoJinyanzhi=(TextView) ziliaoView.findViewById(R.id.four_ziliao_jingyanzhi);
        ziliaoName=(TextView) ziliaoView.findViewById(R.id.four_ziliao_name);
        ziliaoHuanyijian=(Button) ziliaoView.findViewById(R.id.four_ziliao_huanyijian);
        ziliaoXinbie=(ImageButton) ziliaoView.findViewById(R.id.four_ziliao_xingbie);
        ziliaoZaixianjiangli=(Button) ziliaoView.findViewById(R.id.four_ziliao_onlinejiangli);
        haoyouListView=(ListView) haoyouView.findViewById(R.id.four_haoyou_listview);
        gongGongZiYuan=new GongGongZiYuan();

    }


    private void setTiHuan(){
        ziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiHuan.removeAllViews();
                tiHuan.addView(ziliaoView);
            }
        });

        haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiHuan.removeAllViews();
                tiHuan.addView(haoyouView);
            }
        });

        dadang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiHuan.removeAllViews();
                tiHuan.addView(dadiangView);
            }
        });

        xinxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiHuan.removeAllViews();
                tiHuan.addView(xinxiangView);
            }
        });

        jiazu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiHuan.removeAllViews();
                tiHuan.addView(jiazuView);
            }
        });

        tiHuan.addView(ziliaoView);
    }

    private void setZiliao() {

    }

    private void sendMessage(String data){
        Message msg=new Message();
        msg.obj=data;
        whandler.sendMessage(msg);
    }

    private void addListener(){
        SocketClient.sInst.addListener(receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                String[] strings=data.split("/n");
                Log.e("FourthActivity",strings[0]);
                Log.e("FourthActivity","data="+data);
                switch (strings[0]) {
                    case"ziliao:":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ziliaoXinbie.setImageResource(GongGongZiYuan.client.getImage());
                            ziliaoName.setText(GongGongZiYuan.client.getName());
                        }
                    });
                    break;

                    case "setHaoYouList:":
                        for(int i=haoyouList.size()-1;i>=0;i--){
                            haoyouList.remove(haoyouList.get(i));
                        }

                        for(int i=1;i<strings.length;i=i+5){
                            haoyouList.add(new Client(strings[i],strings[i+1],strings[i+2],Integer.parseInt(strings[i+3]),Boolean.parseBoolean(strings[i+4])));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                haoyouListView.setAdapter(new HaoYouAdapter(FourthActivity.this, haoyouList));
                            }
                        });
                        break;
                    case "jinrudating:":
                        Intent intent = new Intent(FourthActivity.this, FifthActivity.class);
                        intent.putExtra("datinghaoma",Integer.parseInt(strings[1]));
                        intent.putExtra("yonghuname",ziliaoName.getText());
                        startActivity(intent);
                        break;
                    case "dating:":
                        for (int i = 1; i < strings.length; i++) {
                            dating[i - 1] = Integer.parseInt(strings[i]);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                daTingAdapter.notifyDataSetChanged();
                            }
                        });

                        break;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Perform any final cleanup before an activity is destroyed.  This can
     * happen either because the activity is finishing (someone called
     * {@link #finish} on it), or because the system is temporarily destroying
     * this instance of the activity to save space.  You can distinguish
     * between these two scenarios with the {@link #isFinishing} method.
     *
     * <p><em>Note: do not count on this method being called as a place for
     * saving data! For example, if an activity is editing data in a content
     * provider, those edits should be committed in either {@link #onPause} or
     * {@link #onSaveInstanceState}, not here.</em> This method is usually implemented to
     * free resources like threads that are associated with an activity, so
     * that a destroyed activity does not leave such things around while the
     * rest of its application is still running.  There are situations where
     * the system will simply kill the activity's hosting process without
     * calling this method (or any others) in it, so it should not be used to
     * do things that are intended to remain around after the process goes
     * away.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onPause
     * @see #onStop
     * @see #finish
     * @see #isFinishing
     */
    @Override
    protected void onDestroy() {
        if(SocketClient.sInst!=null){
            SocketClient.sInst.destroyLintener(receiveListener);
        }
        super.onDestroy();
    }
}