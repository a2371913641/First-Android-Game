package cn.itcast.gobang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.itcast.gobang.AdapterUtil.DaTingAdapter;
import cn.itcast.gobang.AdapterUtil.XinXiangAdapter;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.AdapterUtil.HaoYouAdapter;
import cn.itcast.gobang.Util.IOUtil;
import cn.itcast.gobang.Util.SiXin;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.Util.WriterThread;

public class FourthActivity extends AppCompatActivity {
    String CREATE_ACTIVITY_OK="fourlyActivityOK";
    String XINXIANG="的信箱.txt";
    Button haoyou,ziliao,xinxiang,dadang,jiazu,ziliaoHuanyijian,ziliaoZaixianjiangli;
    ImageButton ziliaoXinbie;
    TextView ziliaoJinyanzhi,ziliaoName;
    Handler whandler= WriterThread.wHandler;
    LinearLayout tiHuan;
    IOUtil io;
    ListView haoyouListView,datingListView,xinxiangListView;
    XinXiangAdapter xinXiangAdapter;
    DaTingAdapter daTingAdapter;
    GongGongZiYuan gongGongZiYuan;
    List<Client> haoyouList;
    int[] dating={0,0,0,0,0,0,0,0};
    View ziliaoView,haoyouView,dadiangView,xinxiangView,jiazuView,sixinDialogView;
    ReceiveListener receiveListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        addListener();
        initView();
        setSiXin();
        setTiHuan();
        sendMessage(CREATE_ACTIVITY_OK+ "_");
        setHuiFu();
        setXinxiang();
    }

    private void initView(){
        io=new IOUtil();
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
        xinxiangListView=xinxiangView.findViewById(R.id.four_xinxiang_ListView);
        xinXiangAdapter=new XinXiangAdapter(FourthActivity.this,GongGongZiYuan.siXins);
        xinxiangListView.setAdapter(xinXiangAdapter);
        ziliaoJinyanzhi=(TextView) ziliaoView.findViewById(R.id.four_ziliao_jingyanzhi);
        ziliaoName=(TextView) ziliaoView.findViewById(R.id.four_ziliao_name);
        ziliaoHuanyijian=(Button) ziliaoView.findViewById(R.id.four_ziliao_huanyijian);
        ziliaoXinbie=(ImageButton) ziliaoView.findViewById(R.id.four_ziliao_xingbie);
        ziliaoZaixianjiangli=(Button) ziliaoView.findViewById(R.id.four_ziliao_onlinejiangli);
        haoyouListView=(ListView) haoyouView.findViewById(R.id.four_haoyou_listview);
        gongGongZiYuan=new GongGongZiYuan();
    }

    private void setSiXin(){
        String s=io.inputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath());
        if(!s.isEmpty()){
            String[] strings=s.split("/n");
            Log.e("Four","s="+s);
            for(int i=0;i<strings.length;i=i+4){
                GongGongZiYuan.siXins.add(new SiXin(strings[i],strings[i+1],strings[i+2],strings[i+3]));

            }
        }
    }

    private void setSelection(){
        xinXiangAdapter.setListViewSelection(new XinXiangAdapter.ListViewSelection() {
            @Override
            public void setSelection() {
                xinxiangListView.setSelection(GongGongZiYuan.siXins.size()-1);
            }
        });
    }

    private void setHuiFu(){
        xinXiangAdapter.setSetHuiFu(new XinXiangAdapter.SetHuiFu() {
            @Override
            public void setHuiFuButton(int postion) {
                Log.e("Four","setClickHuiFu");
                AlertDialog.Builder SiXinDialog=new AlertDialog.Builder(FourthActivity.this);
                SiXinDialog.setView(setSixinDialogView(View.inflate(FourthActivity.this,R.layout.layout_sixin_dialogview,null),
                        GongGongZiYuan.siXins.get(postion).getName()));
                SiXinDialog.create().show();
            }
        });
    }

    private void setXinxiang(){
        xinXiangAdapter.setSetXinxiang(new XinXiangAdapter.SetXinXiangs() {
            @Override
            public void setXinXiangFile() {
                setXinXiangFile();
            }
        });
    }

    private void setViewDefaultColor(){
        ziliao.setBackgroundResource(R.color.blue);
        haoyou.setBackgroundResource(R.color.blue);
        dadang.setBackgroundResource(R.color.blue);
        xinxiang.setBackgroundResource(R.color.blue);
        jiazu.setBackgroundResource(R.color.blue);
    }

    private void setTiHuan(){
        ziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                ziliao.setBackgroundResource(R.color.yellow1);
                tiHuan.removeAllViews();
                tiHuan.addView(ziliaoView);
            }
        });

        haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                haoyou.setBackgroundResource(R.color.yellow1);
                tiHuan.removeAllViews();
                tiHuan.addView(haoyouView);
            }
        });

        dadang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                dadang.setBackgroundResource(R.color.yellow1);
                tiHuan.removeAllViews();
                tiHuan.addView(dadiangView);
            }
        });

        xinxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                xinxiang.setBackgroundResource(R.color.yellow1);
                tiHuan.removeAllViews();
                tiHuan.addView(xinxiangView);
            }
        });

        jiazu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                jiazu.setBackgroundResource(R.color.yellow1);
                tiHuan.removeAllViews();
                tiHuan.addView(jiazuView);
            }
        });

        ziliao.setBackgroundResource(R.color.yellow1);
        tiHuan.addView(ziliaoView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                xinXiangAdapter.notifyDataSetChanged();
                xinxiangListView.setSelection(GongGongZiYuan.siXins.size()-1);
            }
        });
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
                        Log.e("Four","jinruDating--------");
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

                    case "ServerZiLiao:":
                        //"ServerZiLiao:/n+"+client.name+"/n"+client.onLine+"/n"+client.nowAtHall+"/n"
                        //+client.atRoom.roomHaoMa+"/n"+client.atRoom.roomName+"/n"+client.atRoom.roomType+"/n"+!client.atRoom.roomAdmin.equals(" ")+"_"
                        AlertDialog.Builder builder3=new AlertDialog.Builder(FourthActivity.this);
                        builder3.setTitle("找Ta");
                        builder3.setMessage("用户名称:"+strings[1]+"\n"+"是否在线:"+strings[2]+"\n"+"大厅名称:大厅"+strings[3]+"\n"+"房间号码:"+strings[4]+
                                "\n"+"房间名称:"+strings[5]+"\n房间模式:"+strings[6]+"\n有无密码:"+strings[7]);
                        builder3.setPositiveButton("进入Ta所在大厅", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(strings[3].equals("null")){
                                    AlertDialog.Builder builder=new AlertDialog.Builder(FourthActivity.this);
                                    builder.setMessage("Ta不在任何大厅内，无法跟随！");
                                    builder.setNegativeButton("确认",null);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            builder.create().show();
                                        }
                                    });
                                }else{
                                    gongGongZiYuan.sendMsg("jinrudating:/n"+strings[3]+"_");
                                }
                            }
                        });
                        builder3.setNegativeButton("取消",null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder3.create().show();
                            }
                        });
                        break;
                    case"ServerSiXin:":
                        AlertDialog.Builder sixinDialog=new AlertDialog.Builder(FourthActivity.this);
                        sixinDialog.setView(setSixinDialogView(View.inflate(FourthActivity.this,R.layout.layout_sixin_dialogview,null),strings[1]));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sixinDialog.create().show();
                            }
                        });
                        break;
                    case"ServerSendSiXin:":
                        Log.e("Four","s="+strings[0]+strings[1]+strings[2]);
                        GongGongZiYuan.siXins.add(new SiXin("From",strings[1],strings[2],strings[3]));
                        io.outputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),"From"+"/n"+strings[1]+"/n"+strings[2]+"/n"+strings[3]+"/n",true);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                xinXiangAdapter.notifyDataSetChanged();
                                xinxiangListView.setSelection(GongGongZiYuan.siXins.size()-1);
                            }
                        });
                        break;

                }
            }
        });
    }

    private View setSixinDialogView(View sixinDialogView,String name){
        Button sixin_qvxiao_button,sixin_send_button;
        EditText sixin_editText;
        TextView nameXinXiang=(TextView)sixinDialogView.findViewById(R.id.sixin_title);
        sixin_send_button=(Button) sixinDialogView.findViewById(R.id.sixin_dialog_send_button);
        sixin_editText=(EditText) sixinDialogView.findViewById(R.id.dialog_sixin_content_editText);
        sixin_qvxiao_button=(Button) sixinDialogView.findViewById(R.id.sixin_dialog_qvxiao_button);
        nameXinXiang.setText("发送私信给:"+name);
        sixin_qvxiao_button.setOnClickListener(null);
        sixin_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sixin_editText.getText().toString().equals("")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FourthActivity.this,"请输入内容！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    gongGongZiYuan.sendMsg("ClientSendSiXin:/n"+name+"/n"+sixin_editText.getText()+"/n"+formatter.format(date)+"_");
                    GongGongZiYuan.siXins.add(new SiXin("To",name,sixin_editText.getText().toString(),formatter.format(date)));
                    io.outputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),"To"+"/n"+name+"/n"+sixin_editText.getText().toString()+"/n"+formatter.format(date)+"/n",true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    xinXiangAdapter.notifyDataSetChanged();
                                    xinxiangListView.setSelection(GongGongZiYuan.siXins.size()-1);
                                }
                            });
                            Toast.makeText(FourthActivity.this,"发送成功！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return sixinDialogView;
    }

    /**
     * Dispatch onPause() to fragments.
     */

    private void setXinXiang(){
        if(GongGongZiYuan.siXins.size()!=0){
            IOUtil io=new IOUtil();
            for(SiXin siXin:GongGongZiYuan.siXins){
                io.outputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),
                        siXin.getFromOrTo()+"/n"+siXin.getName()+"/n"+siXin.getContent()+"/n"+siXin.getTime()+"/n"
                        ,false);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(GongGongZiYuan.siXins.size()!=0){
            for(SiXin siXin:GongGongZiYuan.siXins){
                io.outputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),
                        siXin.getFromOrTo()+"/n"+siXin.getName()+"/n"+siXin.getContent()+"/n"+siXin.getTime()+"/n"
                        ,false);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                xinXiangAdapter.notifyDataSetChanged();
                xinxiangListView.setSelection(GongGongZiYuan.siXins.size()-1);
            }
        });
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