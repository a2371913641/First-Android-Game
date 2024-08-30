package cn.itcast.gobang;

import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.util.ArrayList;

import java.util.List;

import cn.itcast.gobang.AdapterUtil.BiaoQingBaoAdapter;
import cn.itcast.gobang.AdapterUtil.DatingClientAdapter;
import cn.itcast.gobang.AdapterUtil.HaoYouAdapter;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.IOUtil;
import cn.itcast.gobang.Util.LiaoTianAdapter;
import cn.itcast.gobang.Util.LiaoTianXiaoXi;
import cn.itcast.gobang.Util.Room;
import cn.itcast.gobang.Util.RoomListAdapter;
import cn.itcast.gobang.Util.SiXin;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.Util.WriterThread;

public class FifthActivity extends FourFiveSixActivity {
    String XINXIANG="的信箱.txt";
    Intent oldintent;
    int datinghaoma;
    String yonghuName;
    List<LiaoTianXiaoXi> liaoTianXiaoXiList;
    List<Integer> biaoqingbaolist;
    List<Client> clients,haoyouList;
    Handler whandler;
    TextView datingName;
    Button liaotian_btn,haoyou_btn,yonghu_btn,liaotianSend_Btn,createRoom;
    EditText liaotianEditView;
    LinearLayout tihuan;
    RecyclerView biaoqingbaoRecyclerView;
    View liaotian_layout,haoyou_layout,yonghu_layout,biaoqingbao_layout;
    ListView liaotianListView,roomListView,datingClient;
    LiaoTianAdapter liaotianAdapter;
    ImageView biaoqingbao;
    AlertDialog.Builder biaoqingbaoDialog,createRoomDialog;
    BiaoQingBaoAdapter biaoQingBaoAdapter;
    GongGongZiYuan gongGongZiYuan;
    RoomListAdapter roomListAdapter;
    List<Room> roomList;
    DatingClientAdapter datingClientAdapter;
    ReceiveListener receiveListener;
    HaoYouAdapter haoYouAdapter;
    IOUtil io;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        oldintent=getIntent();
        datinghaoma=oldintent.getIntExtra("datinghaoma",100);
        yonghuName=oldintent.getStringExtra("yonghuname");

        initView();
        Log.e("fifthActivity","initView后.roomlist="+roomList.size());
        setBiaoQingBaoList();
        setListener();
        gongGongZiYuan.sendMsg("4-5:/n_");
        setCreateRoom();
        setTihuan();
        setLiaoTian();

    }


    private void initView(){
        io=new IOUtil();
        whandler= WriterThread.wHandler;
        Log.e("FifthActivity","onCreate.initView()");
        roomList=new ArrayList<>();
        clients=new ArrayList<>();
        haoyouList=new ArrayList<>();
        Log.e("fifthActivity","initView.roomlist="+roomList.size());
        gongGongZiYuan=new GongGongZiYuan();
        liaoTianXiaoXiList=new ArrayList<>();
        biaoqingbaolist=new ArrayList<>();
        liaotian_btn=(Button)findViewById(R.id.five_liaotian);
        haoyou_btn=(Button)findViewById(R.id.five_haoyou);
        yonghu_btn=(Button)findViewById(R.id.five_yonghu);
        createRoom=(Button)findViewById(R.id.five_create_room) ;
        datingName=(TextView)findViewById(R.id.five_datingname_textview);
        datingName.setText("大厅"+datinghaoma);
        roomListView=(ListView)findViewById(R.id.five_roon_listview);
        roomListAdapter=new RoomListAdapter(FifthActivity.this,roomList);
        roomListView.setAdapter(roomListAdapter);
        liaotian_layout=View.inflate(FifthActivity.this,R.layout.layout_five_dating_liaotian,null);
        haoyou_layout=View.inflate(FifthActivity.this,R.layout.layout_five_dating_haoyou,null);
        ListView haoyouListView=haoyou_layout.findViewById(R.id.five_haouyou_listview);
        haoYouAdapter=new HaoYouAdapter(this,haoyouList);
        haoyouListView.setAdapter(haoYouAdapter);
        yonghu_layout=View.inflate(FifthActivity.this,R.layout.layout_five_client,null);
        datingClient=(ListView) yonghu_layout.findViewById(R.id.dating_client);
        datingClientAdapter=new DatingClientAdapter(FifthActivity.this,clients);
        datingClient.setAdapter(datingClientAdapter);
        biaoqingbao_layout=View.inflate(FifthActivity.this,R.layout.layout_five_baioqingbao_dialog,null);
        tihuan=(LinearLayout) findViewById(R.id.five_tihuan);
        liaotianListView=(ListView) liaotian_layout.findViewById(R.id.five_liaotian_listview);
        liaotianAdapter=new LiaoTianAdapter(FifthActivity.this,R.layout.layout_five_dating_liaotian_item,liaoTianXiaoXiList);
        liaotianListView.setAdapter(liaotianAdapter);
        liaotianEditView=(EditText)liaotian_layout.findViewById(R.id.five_liaotian_edit_text);
        liaotianSend_Btn=(Button) liaotian_layout.findViewById(R.id.five_liaotian_send);
        biaoqingbao=(ImageView) liaotian_layout.findViewById(R.id.five_liaotian_biaoqingbao);
        biaoqingbaoRecyclerView=(RecyclerView)biaoqingbao_layout.findViewById(R.id.biaoqingbao_RecyclerList);
        biaoqingbaoDialog=new AlertDialog.Builder(this);
        biaoQingBaoAdapter=new BiaoQingBaoAdapter(this,biaoqingbaolist,"liaotianxiaoxi:");
        biaoqingbaoRecyclerView.setLayoutManager(new GridLayoutManager(this,8));
        biaoqingbaoRecyclerView.setAdapter(biaoQingBaoAdapter);
        setLiaoTianAdapter();
    }

    private void setLiaoTianAdapter(){
        liaotianAdapter.setClickSetSiLiao(new LiaoTianAdapter.SetSiLiao() {
            @Override
            public void setSiLiaoSendExit(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        liaotianEditView.setText(s);
                    }
                });
            }
        });
    }

    private void setCreateRoom(){
        View view=View.inflate(FifthActivity.this,R.layout.layout_five_create_room_dialog,null);
        EditText roomNameEdit=(EditText)view.findViewById(R.id.room_name_edittext);
        EditText roomAdminEdit=(EditText)view.findViewById(R.id.room_admin_edittext);
        RadioGroup radioGroup=(RadioGroup)view.findViewById(R.id.create_room_radiogroup);
        createRoomDialog=new AlertDialog.Builder(FifthActivity.this);
        AlertDialog createRoomDialog1=createRoomDialog.setView(view)
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String roomName=null;
                        String roomAdmin="/?";
                        String roomType=null;

                        for(int i=0;i<radioGroup.getChildCount();i++){
                            RadioButton rb=(RadioButton) radioGroup.getChildAt(i);
                            if(rb.isChecked()){
                                roomType=rb.getText().toString();
                                break;
                            }
                        }
                        roomName=roomNameEdit.getText().toString();
                        if(!roomAdminEdit.getText().toString().equals("")) {
                            roomAdmin = roomAdminEdit.getText().toString();
                        }


                        if(roomType == null||roomName.equals("")){
                            Toast.makeText(FifthActivity.this,"请选择房间类型！",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("FifthActivity","createRoom");
                            gongGongZiYuan.sendMsg("createRoom:/n"+roomName+"/n"+roomType+"/n"+roomAdmin+"_");
                        }

                    }
                })
                .create();
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNameEdit.setText(yonghuName+"的房间");
                createRoomDialog1.show();
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

    private void setListener(){
        SocketClient.sInst.addListener(receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                Log.e("FifthActivity","data==========="+data);
                String[] strings=data.split("/n");
                Log.e("FifthActivity","strings==========="+strings.length);
                if(("dating"+datinghaoma+":").equals(strings[0])){
                    liaoTianXiaoXiList.add(new LiaoTianXiaoXi(strings[1],Integer.parseInt(strings[2])));
                    for(LiaoTianXiaoXi xx:liaoTianXiaoXiList){
                        Log.e("FifthActivity","liaotian:"+xx.getWenzi()+xx.getBiaoqing());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            liaotianAdapter.notifyDataSetChanged();
                            liaotianListView.setSelection(liaotianAdapter.getCount()-1);
                        }
                    });

                }else if(strings[0].equals("5-6:")){
                    Intent intent=new Intent(FifthActivity.this, SixRoomActivity.class);
                    Log.e("FifthActivity","startActivity");
                    startActivity(intent);

                }else if(strings[0].equals("setRoomList:")){
                    Log.e("FifthActivity","setRoomList");
                    for(int i=roomList.size()-1;i>=0;i--){
                        roomList.remove(i);
                    }
                    Log.e("FifthActivity","roomList="+roomList.size());

                    for(int i=1;i<strings.length;i=i+4){
                        roomList.add(new Room(strings[i],strings[i+1],strings[i+2],Integer.parseInt(strings[i+3])));
                        Log.e("FifthActivity","roomList["+i+"]="+strings[i]+strings[i+1]+strings[i+2]+Integer.parseInt(strings[i+3]));
                    }
                    Log.e("FifthActivity","getServer,roomList="+roomList.size());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roomListAdapter.notifyDataSetChanged();
                        }
                    });
                }else if(strings[0].equals("datingClient:")){
                    Log.e("FifthActivity","datingClient:="+data);
                    for(int i=clients.size()-1;i>=0;i--){
                        clients.remove(i);
                    }
                    Log.e("FifthActivity","clients="+clients.size());

                    Log.e("FifthActivity","strings.size="+strings.length);
                    for(int i=1;i<strings.length;i=i+5){
                        Log.e("FifthActivity","i="+i);
                        clients.add(new Client(strings[i],strings[i+1],strings[i+2],Integer.parseInt(strings[i+3]),Boolean.parseBoolean(strings[i+4])));
                        Log.e("FifthActivity","client.name="+strings[i+1]+",client.onLine="+strings[i+4]);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            datingClientAdapter.notifyDataSetChanged();
                        }
                    });

                }else if(strings[0].equals("setHaoYouList:")){
                    for (int i = haoyouList.size() - 1; i >= 0; i--) {
                        haoyouList.remove(haoyouList.get(i));
                    }

                    for (int i = 1; i < strings.length; i = i + 5) {
                        haoyouList.add(new Client(strings[i], strings[i + 1], strings[i + 2], Integer.parseInt(strings[i + 3]), Boolean.parseBoolean(strings[i + 4])));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            haoYouAdapter.notifyDataSetChanged();
                        }
                    });
                }else if(strings[0].equals("ServerZiLiao:")) {
                    //"ServerZiLiao:/n+"+client.name+"/n"+client.onLine+"/n"+client.nowAtHall+"/n"
                    //+client.atRoom.roomHaoMa+"/n"+client.atRoom.roomName+"/n"+client.atRoom.roomType+"/n"+!client.atRoom.roomAdmin.equals(" ")+"/n"+nowAtHall+"_"

                    if(strings[8].equals(strings[3])) {
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(FifthActivity.this);
                        builder3.setTitle("找Ta");
                        builder3.setMessage("用户名称:" + strings[1] + "\n" + "是否在线:" + strings[2] + "\n" + "大厅名称:大厅" + strings[3] + "\n" + "房间号码:" + strings[4] +
                                "\n" + "房间名称:" + strings[5] + "\n房间模式:" + strings[6] + "\n有无密码:" + strings[7]);
                        builder3.setPositiveButton("进入房间", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (strings[4].equals("null")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FifthActivity.this);
                                    builder.setMessage("该玩家当前不在房间内，无法跟房！");
                                    builder.setNegativeButton("确定", null);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            builder.create().show();
                                        }
                                    });
                                } else {
                                    gongGongZiYuan.sendMsg("ClientFollowRoom:/n" + strings[4] + "_");
                                }
                            }
                        });
                        builder3.setNegativeButton("取消", null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder3.create().show();
                            }
                        });
                    }else{
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(FifthActivity.this);
                        builder3.setTitle("找Ta");
                        builder3.setMessage("用户名称:" + strings[1] + "\n" + "是否在线:" + strings[2] + "\n" + "大厅名称:大厅" + strings[3] + "\n" + "房间号码:" + strings[4] +
                                "\n" + "房间名称:" + strings[5] + "\n房间模式:" + strings[6] + "\n有无密码:" + strings[7]);
                        builder3.setNegativeButton("确认",null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder3.create().show();
                            }
                        });
                    }
                }else if(strings[0].equals("ServerSiLiao:")){
//                    String s="@"+strings[1]+":";
//                    liaotianEditView.setText(s);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tihuan.removeAllViews();
//                            setViewDefaultColor();
//                            tihuan.addView(liaotian_layout);
//                            liaotian_btn.setBackgroundResource(R.color.yellow1);
//                            LayoutParams lp = liaotian_layout.getLayoutParams();
//                            lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
//                            liaotian_layout.setLayoutParams(lp);
//                        }
//                    });
                }else if(strings[0].equals("ServerYaoQin:")){
                    AlertDialog.Builder yaoqingDialog=new AlertDialog.Builder(FifthActivity.this);
                    yaoqingDialog.setTitle("提示");
                    yaoqingDialog.setMessage(strings[1]+"邀请您进入"+"["+strings[2]+"]"+strings[3]+",\n"+"是否同意？");
                    yaoqingDialog.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gongGongZiYuan.sendMsg("ClientTwoRefuseYaoQin:/n"+strings[1]+"_");
                        }
                    });
                    yaoqingDialog.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gongGongZiYuan.sendMsg("ClientFollowRoom:/n" + strings[2] + "_");
                        }
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            yaoqingDialog.create().show();
                        }
                    });
                }else if(strings[0].equals("ServerSiXin:")) {
                    setSiXinDialog(strings[1]);
                }else if(strings[0].equals("ServerSendSiXin:")){
                        Log.e("FifctActivity","s="+strings[0]+strings[1]+strings[2]);
                        GongGongZiYuan.siXins.add(new SiXin("From",strings[1],strings[2],strings[3]));

                        io.outputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+XINXIANG).getAbsolutePath(),"From"+"/n"+strings[1]+"/n"+strings[2]+"/n"+strings[3]+"/n",true);

                }
            }
        });
    }


    private void setViewDefaultColor(){
        haoyou_btn.setBackgroundResource(R.color.blue);
        liaotian_btn.setBackgroundResource(R.color.blue);
        yonghu_btn.setBackgroundResource(R.color.blue);

    }
    private void setTihuan(){
        liaotian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuan.removeAllViews();
                setViewDefaultColor();
               liaotian_btn.setBackgroundResource(R.color.yellow1);
                tihuan.addView(liaotian_layout);
                LayoutParams lp = liaotian_layout.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                liaotian_layout.setLayoutParams(lp);
            }
        });

        haoyou_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                haoyou_btn.setBackgroundResource(R.color.yellow1);
                tihuan.removeAllViews();
                tihuan.addView(haoyou_layout);
            }
        });

        yonghu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewDefaultColor();
                yonghu_btn.setBackgroundResource(R.color.yellow1);
                tihuan.removeAllViews();
                tihuan.addView(yonghu_layout);
            }
        });

        tihuan.addView(liaotian_layout);
        liaotian_btn.setBackgroundResource(R.color.yellow1);
        liaotian_layout.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        liaotian_layout.setLayoutParams(liaotian_layout.getLayoutParams());
    }

    private void setLiaoTian(){
        liaotianSend_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=liaotianEditView.getText().toString();
                if(!data.equals("")){
                    msgSend("liaotianxiaoxi:/n"+data+"/n0"+"_");
                    Log.e("Send","liaotianxiaoxi:/n"+data+"/n0"+"_");
                    liaotianEditView.setText("");

                }
            }
        });

        AlertDialog alertDialog=biaoqingbaoDialog.setView(biaoqingbao_layout)
                .create();
        biaoqingbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void msgSend(String s){
        Message msg=new Message();
        msg.obj=s;
        whandler.sendMessage(msg);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SocketClient.sInst.allDestoryListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
       if(SocketClient.sInst.getListenerListSize()==0){
           setListener();
       }
    }

    @Override
    protected void onDestroy() {

        gongGongZiYuan.sendMsg("tuichudating:/n");
        if(SocketClient.sInst!=null){
            SocketClient.sInst.destroyLintener(receiveListener);
        }
        super.onDestroy();
    }
}