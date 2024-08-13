package cn.itcast.gobang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.AdapterUtil.BiaoQingBaoAdapter;
import cn.itcast.gobang.AdapterUtil.HaoYouAdapter;
import cn.itcast.gobang.AdapterUtil.RoomClientRecycleAdapter;
import cn.itcast.gobang.AdapterUtil.YaoQinAdapter;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.LiaoTianAdapter;
import cn.itcast.gobang.Util.LiaoTianXiaoXi;
import cn.itcast.gobang.Util.Room;
import cn.itcast.gobang.Util.SocketClient;

public class SixRoomActivity extends AppCompatActivity {

    List<Client> clientList;
    List<Client> haoyouList;
    Button haoyou,liaotian,diange,yaoqing,sendLiaotianButton;
    ImageView biaoqingbaoButton;
    LinearLayout tihuanLayout;
    RoomClientRecycleAdapter roomClientRecycleAdapter;
    RecyclerView roomClientRecyclerView;
    GongGongZiYuan gongGongZiYuan;
    TextView roomNameTextView;
    View haoyouView,liaotianView,diangeView,yaoqingView,biaoqingbaoView;
    LiaoTianAdapter liaoTianAdapter;
    HaoYouAdapter haoYouAdapter;
    YaoQinAdapter yaoqingAdapter;
    List<LiaoTianXiaoXi> liaotianXiaoxiList;
    List<Integer> biaoqingbaolist;
    ListView liaotianListView,yaoqingListView;
    EditText liaotianEditText;
    AlertDialog.Builder biaoqingbaoDialog;
    RecyclerView biaoqingbaoRecyclerView;
    BiaoQingBaoAdapter biaoQingBaoAdapter;
    ReceiveListener receiveListener;
    List<Client> yaoqingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_room);
        setReceiveListener();
        initView();
        gongGongZiYuan.sendMsg("jinruRoom:/n_");
        setBiaoQingBaoList();
        setLiaotian();
        setTihuanLayout();


    }

    private void initView(){
        gongGongZiYuan=new GongGongZiYuan();
        clientList=new ArrayList<>();
        yaoqingList=new ArrayList<>();
        haoyouList=new ArrayList<>();
        biaoqingbaoDialog=new AlertDialog.Builder(SixRoomActivity.this);
        tihuanLayout=(LinearLayout)findViewById(R.id.six_tihuan_linearlayout);
        roomNameTextView=(TextView) findViewById(R.id.six_roonname_textview);
        roomClientRecycleAdapter=new RoomClientRecycleAdapter(SixRoomActivity.this,clientList);
        roomClientRecyclerView=findViewById(R.id.six_wanjia_recyclerview);
        roomClientRecyclerView.setLayoutManager(new GridLayoutManager(SixRoomActivity.this,3));
        roomClientRecyclerView.setAdapter(roomClientRecycleAdapter);
        haoyou=(Button) findViewById(R.id.six_haoyou_button);
        liaotian=(Button) findViewById(R.id.six_liaotian_button);
        diange=(Button) findViewById(R.id.six_diange_button);
        yaoqing=(Button) findViewById(R.id.six_yaoqing_button);
        liaotianXiaoxiList=new ArrayList<>();
        biaoqingbaoView=View.inflate(SixRoomActivity.this,R.layout.layout_five_baioqingbao_dialog,null);
        yaoqingView=View.inflate(SixRoomActivity.this,R.layout.layout_four_haoyou,null);
        yaoqingAdapter=new YaoQinAdapter(SixRoomActivity.this,yaoqingList);
        yaoqingListView=yaoqingView.findViewById(R.id.four_haoyou_listview);
        yaoqingListView.setAdapter(yaoqingAdapter);
        liaotianView=View.inflate(SixRoomActivity.this,R.layout.layout_five_dating_liaotian,null);
        liaoTianAdapter=new LiaoTianAdapter(SixRoomActivity.this,R.layout.layout_five_dating_liaotian_item,liaotianXiaoxiList);
        liaotianListView=liaotianView.findViewById(R.id.five_liaotian_listview);
        liaotianListView.setAdapter(liaoTianAdapter);
        liaotianEditText=liaotianView.findViewById(R.id.five_liaotian_edit_text);
        sendLiaotianButton=liaotianView.findViewById(R.id.five_liaotian_send);
        biaoqingbaoButton=liaotianView.findViewById(R.id.five_liaotian_biaoqingbao);
        biaoqingbaolist=new ArrayList<>();
        biaoQingBaoAdapter=new BiaoQingBaoAdapter(this,biaoqingbaolist,"InTheRoomliaotianxiaoxi:");
        biaoqingbaoRecyclerView=biaoqingbaoView.findViewById(R.id.biaoqingbao_RecyclerList);
        biaoqingbaoRecyclerView.setLayoutManager(new GridLayoutManager(this,8));
        biaoqingbaoRecyclerView.setAdapter(biaoQingBaoAdapter);
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

    private void setTihuanLayout(){
        haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        liaotian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuanLayout.removeAllViews();
                tihuanLayout.addView(liaotianView);
                liaotianView.getLayoutParams().height=LinearLayout.LayoutParams.MATCH_PARENT;
                liaotianView.setLayoutParams(liaotianView.getLayoutParams());

            }
        });

        diange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuanLayout.removeAllViews();
                tihuanLayout.addView(yaoqingView);
                yaoqingView.getLayoutParams().width=LinearLayout.LayoutParams.MATCH_PARENT;
                yaoqingView.setLayoutParams(yaoqingView.getLayoutParams());
            }
        });

        if(tihuanLayout.getChildCount()==0){
            tihuanLayout.addView(liaotianView);
            liaotianView.getLayoutParams().height=LinearLayout.LayoutParams.MATCH_PARENT;
            liaotianView.setLayoutParams(liaotianView.getLayoutParams());
        }
    }

    private void setLiaotian(){
        sendLiaotianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=liaotianEditText.getText().toString();
                if(!data.equals("")){
                    gongGongZiYuan.sendMsg("InTheRoomliaotianxiaoxi:/n"+data+"/n0"+"_");

                }
            }
        });

        AlertDialog alertDialog=biaoqingbaoDialog.setView(biaoqingbaoView)
                .create();
        biaoqingbaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void setReceiveListener(){
        SocketClient.sInst.addListener(receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                Log.e("SixRoomActivity","data="+data);
                String[] strings=data.split("/n");
                switch (strings[0]){
                    case "setInTheRoomClient:":
                        for(int i=clientList.size()-1;i>=0;i--){
                            clientList.remove(clientList.get(i));
                        }

                        for(int i=1;i<strings.length;i=i+5){
                            clientList.add(new Client(strings[i], strings[i+1], strings[i+2], Integer.parseInt(strings[i+3]),Boolean.parseBoolean(strings[i+4])));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                roomClientRecycleAdapter.notifyDataSetChanged();
                            }
                        });

                        break;

                    case "setRoom:":
                        GongGongZiYuan.room=new Room(strings[1],strings[2],strings[3],Integer.parseInt(strings[4]));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                roomNameTextView.setText("["+GongGongZiYuan.room.getRoomHaoMa()+"]"+GongGongZiYuan.room.getRoomName());
                            }
                        });
                        break;
                    case "InTheRoomliaotianxiaoxi:":
                        Log.e("Six","Strings[1]="+strings[1]+"Strings[2]="+strings[2]);
                        liaotianXiaoxiList.add(new LiaoTianXiaoXi(strings[1],Integer.parseInt(strings[2])));
                        for(LiaoTianXiaoXi xiaoXi:liaotianXiaoxiList){
                            Log.e("Six","ltxxi="+xiaoXi.getWenzi()+xiaoXi.getBiaoqing());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("Six","UI");
                                liaoTianAdapter.notifyDataSetChanged();
                                liaotianListView.setSelection(liaoTianAdapter.getCount()-1);
                                Toast.makeText(SixRoomActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case"setyaoqingList:":
                        for(int i=yaoqingList.size()-1;i>=0;i--){
                            yaoqingList.remove(i);
                        }
                        Log.e("Six","yaoqingList="+yaoqingList.size());

                        Log.e("Six","strings.size="+strings.length);
                        for(int i=1;i<strings.length;i=i+4){
                            yaoqingList.add(new Client(strings[i],strings[i+1],strings[i+2],Integer.parseInt(strings[i+3]),Boolean.parseBoolean(strings[i+4])));
                        }

                        for(Client client:yaoqingList){
                            Log.e("Six","name="+client.getName());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                yaoqingAdapter.notifyDataSetChanged();
                            }
                        });

                        break;

                    case"setHaoyou:":

                    default:
                        Log.e("SixRoomActivity","default:data="+data);
                }

            }
        });
    }



    @Override
    protected void onDestroy() {

        gongGongZiYuan.sendMsg("tuichuRoom:_");
        if(SocketClient.sInst!=null){
            SocketClient.sInst.destroyLintener(receiveListener);
        }
        super.onDestroy();
    }
}
