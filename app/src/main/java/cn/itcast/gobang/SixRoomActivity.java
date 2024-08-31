package cn.itcast.gobang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import cn.itcast.gobang.FunctionActivityFile.HaoYouListUpdate;
import cn.itcast.gobang.FunctionActivityFile.SendSiXinFunction;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.IOUtil;
import cn.itcast.gobang.Util.LiaoTianAdapter;
import cn.itcast.gobang.Util.LiaoTianXiaoXi;
import cn.itcast.gobang.Util.Room;
import cn.itcast.gobang.Util.SocketClient;

public class SixRoomActivity extends AppCompatActivity {
    IOUtil io;
    List<Client> clientList;
    List<Client> haoyouList;
    Boolean isFalse;
    Button haoyou,liaotian,diange,yaoqing,sendLiaotianButton;
    ImageView biaoqingbaoButton;
    LinearLayout tihuanLayout;
    RoomClientRecycleAdapter roomClientRecycleAdapter;
    RecyclerView roomClientRecyclerView;
    public GongGongZiYuan gongGongZiYuan;
    TextView roomNameTextView;
    View haoyouView,liaotianView,diangeView,yaoqingView,biaoqingbaoView;
    LiaoTianAdapter liaoTianAdapter;
    HaoYouAdapter haoYouAdapter;
    YaoQinAdapter yaoqingAdapter;
    List<LiaoTianXiaoXi> liaotianXiaoxiList;
    List<Integer> biaoqingbaolist;
    ListView liaotianListView,yaoqingListView,haoyouListView;
    EditText liaotianEditText;
    AlertDialog.Builder biaoqingbaoDialog,tuichuRoomDialog;
    RecyclerView biaoqingbaoRecyclerView;
    BiaoQingBaoAdapter biaoQingBaoAdapter;
    ReceiveListener receiveListener;
    List<Client> yaoqingList;
//    WindowManager windowManager;

    Dialog clickImageDialog;
    SendSiXinFunction sendSiXinFunction;
    HaoYouListUpdate haoYouListUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_room);
        initView();
        sendSiXinFunction=new SendSiXinFunction(SixRoomActivity.this,gongGongZiYuan,SixRoomActivity.this);
        haoYouListUpdate=new HaoYouListUpdate(haoyouList,SixRoomActivity.this);
        gongGongZiYuan.sendMsg("jinruRoom:/n_");
        setBiaoQingBaoList();
        setLiaotian();
        setTihuanLayout();
        ImageClick();

    }

    private void initView(){
        io=new IOUtil();
//        windowManager=getWindowManager();
        gongGongZiYuan=new GongGongZiYuan();
        clientList=new ArrayList<>();
        yaoqingList=new ArrayList<>();
        haoyouList=new ArrayList<>();
        haoYouAdapter=new HaoYouAdapter(this,haoyouList);
        haoyouView=View.inflate(this,R.layout.layout_four_haoyou,null);
        haoyouListView=haoyouView.findViewById(R.id.four_haoyou_listview);
        haoyouListView.setAdapter(haoYouAdapter);
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
        clickImageDialog=new Dialog(SixRoomActivity.this);
        setLiaoTianAdapter();
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

    private void setLiaoTianAdapter(){
        liaoTianAdapter.setClickSetSiLiao(new LiaoTianAdapter.SetSiLiao() {
            @Override
            public void setSiLiaoSendExit(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        liaotianEditText.setText(s);
                    }
                });
            }
        });
    }

    private void setViewDefaultColor(){
        haoyou.setBackgroundResource(R.color.blue);
        liaotian.setBackgroundResource(R.color.blue);
        yaoqing.setBackgroundResource(R.color.blue);
        diange.setBackgroundResource(R.color.blue );
    }

    private void setTihuanLayout(){
        haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuanLayout.removeAllViews();
                setViewDefaultColor();
                haoyou.setBackgroundResource(R.color.yellow1);
                tihuanLayout.addView(haoyouView);
                haoyouView.getLayoutParams().height=LinearLayout.LayoutParams.MATCH_PARENT;
                haoyouView.setLayoutParams(haoyouView.getLayoutParams());
            }
        });

        liaotian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuanLayout.removeAllViews();
                setViewDefaultColor();
                liaotian.setBackgroundResource(R.color.yellow1);
                tihuanLayout.addView(liaotianView);
                liaotianView.getLayoutParams().height=LinearLayout.LayoutParams.MATCH_PARENT;
                liaotianView.setLayoutParams(liaotianView.getLayoutParams());

            }
        });

        diange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuanLayout.removeAllViews();
                setViewDefaultColor();
                diange.setBackgroundResource(R.color.yellow1);

            }
        });

        yaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tihuanLayout.removeAllViews();
                setViewDefaultColor();
                yaoqing.setBackgroundResource(R.color.yellow1);
                tihuanLayout.addView(yaoqingView);
                yaoqingView.getLayoutParams().width=LinearLayout.LayoutParams.MATCH_PARENT;
                yaoqingView.setLayoutParams(yaoqingView.getLayoutParams());
            }
        });

        if(tihuanLayout.getChildCount()==0){
            tihuanLayout.addView(liaotianView);
            liaotian.setBackgroundResource(R.color.yellow1);
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

                    gongGongZiYuan.sendMsg("InTheRoomliaotianxiaoxi:/n"+data+"/n0_");
                    liaotianEditText.setText("");
                    Log.e("Six","data="+data);
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
                            liaotianXiaoxiList.add(new LiaoTianXiaoXi(strings[1],Integer.parseInt(strings[2])));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                liaoTianAdapter.notifyDataSetChanged();
                                liaotianListView.setSelection(liaoTianAdapter.getCount()-1);
                                Toast.makeText(SixRoomActivity.this, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case"setyaoqingList:":
                        Log.e("Six","setyaoqingList:");
                        for(int i=yaoqingList.size()-1;i>=0;i--){
                            yaoqingList.remove(i);
                        }

                        for(int i=1;i<strings.length;i=i+5){
                            yaoqingList.add(new Client(strings[i],strings[i+1],strings[i+2],Integer.parseInt(strings[i+3]),Boolean.parseBoolean(strings[i+4])));
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                yaoqingAdapter.notifyDataSetChanged();
                            }
                        });

                        break;

                    case"setHaoYouList:":
                        haoYouListUpdate.DeleteAll();
                        haoYouListUpdate.updateHaoYouList(strings);
                        haoYouListUpdate.updateUIHaoYouList(haoYouAdapter);
                        break;
                    case"serverAddFriend:":
                        AlertDialog.Builder builder=new AlertDialog.Builder(SixRoomActivity.this);
                        builder.setTitle(strings[1]+"请求添加您为好友！");
                        builder.setMessage("您是否同意？");
                        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gongGongZiYuan.sendMsg("ClientTwoAgree:/n"+strings[2]+"_");
                                Log.e("Six","ClientTwoAgree:/n"+strings[2]+"_");
                            }
                        });

                        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gongGongZiYuan.sendMsg("ClientTwoRefuse:/n"+strings[2]+"_");
                            }
                        });
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               builder.create();
                               builder.show();
                           }
                       });
                        break;

                    case"ServerTwoAgree:":
                        AlertDialog.Builder builder2=new AlertDialog.Builder(SixRoomActivity.this);
                        builder2.setMessage("对方已同意你的好友申请！");
                        gongGongZiYuan.sendMsg("ClientRogerTwoAgree:/n"+strings[1]+"_");
                        builder2.setNeutralButton("确定",null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder2.create();
                                builder2.show();
                            }
                        });
                        break;

                    case"ServerTwoRefuse:":
                        AlertDialog.Builder builder1=new AlertDialog.Builder(SixRoomActivity.this);
                        builder1.setMessage("对方拒绝了你的好友申请！");
                        builder1.setNeutralButton("确定",null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder1.create();
                                builder1.show();
                            }
                        });
                        break;

                    case "ServerZiLiao:":
                        //"ServerZiLiao:/n+"+client.name+"/n"+client.onLine+"/n"+client.nowAtHall+"/n"
                        //+client.atRoom.roomHaoMa+"/n"+client.atRoom.roomName+"/n"+client.atRoom.roomType+"/n"+!client.atRoom.roomAdmin.equals(" ")+"_"
                        AlertDialog.Builder builder3=new AlertDialog.Builder(SixRoomActivity.this);
                        builder3.setTitle("找Ta");
                        builder3.setMessage("用户名称:"+strings[1]+"\n"+"是否在线:"+strings[2]+"\n"+"大厅名称:大厅"+strings[3]+"\n"+"房间号码:"+strings[4]+
                                "\n"+"房间名称:"+strings[5]+"\n房间模式:"+strings[6]+"\n有无密码:"+strings[7]);
                        builder3.setNegativeButton("确认",null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder3.create().show();
                            }
                        });
                        break;

                    case"ServerSiLiao:":
                        String s="@"+strings[1]+":";
                        liaotianEditText.setText(s);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tihuanLayout.removeAllViews();
                                setViewDefaultColor();
                                tihuanLayout.addView(liaotianView);
                                liaotian.setBackgroundResource(R.color.yellow1);
                                liaotianView.getLayoutParams().height=LinearLayout.LayoutParams.MATCH_PARENT;
                                liaotianView.setLayoutParams(liaotianView.getLayoutParams());
                            }
                        });
                        break;
                    case "ServerTwoRefuseYaoQin:":
                        AlertDialog.Builder refuseYaoQingdialog=new AlertDialog.Builder(SixRoomActivity.this);
                        refuseYaoQingdialog.setTitle("提示");
                        refuseYaoQingdialog.setMessage(strings[1]+"拒绝了你的邀请！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refuseYaoQingdialog.create().show();
                            }
                        });
                        break;
                    case"ServerSiXin:":
                        sendSiXinFunction.setSiXinDialog(strings[1]);
                        break;
                    case"ServerSendSiXin:":
                        Log.e("Four","s="+strings[0]+strings[1]+strings[2]);
                        sendSiXinFunction.jieshouSiXIn(strings[1],strings[2],strings[3]);
                        break;
                    default:
                        Log.e("SixRoomActivity","default:data="+data);
                }

            }
        });
    }






    private void ImageClick(){
        roomClientRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                roomClientRecycleAdapter.setOnclick(new RoomClientRecycleAdapter.ClickInterface() {
                    @Override
                    public void onImageClick(int position) {
                        Log.e("Six","Click");
                        clickImageDialog(View.inflate(SixRoomActivity.this,R.layout.layout_room_client_clickitem,null),position);
                    }
                });
            }
        });
    }

    private Boolean returnIsFalse(){
        if(isFalse==null || !isFalse){
            isFalse=true;
        }else{
            isFalse=false;
        }
        Log.e("Six","isFalse="+isFalse);
        return isFalse;
    }

//    private void floatingWindow(int position){
//        View view= View.inflate(SixRoomActivity.this,R.layout.layout_room_client_clickitem,null);
//        WindowManager.LayoutParams windowParam= new WindowManager.LayoutParams();
//        windowParam.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        windowParam.width=WindowManager.LayoutParams.WRAP_CONTENT;
//        if(returnIsFalse()){
//            windowManager.addView(view,windowParam);
//            Log.e("Six","add");
//        }else{
//            Log.e("Six","remove");
//            windowManager.removeViewImmediate(view);
//        }
//    }

    private void clickImage(int position){

        if(returnIsFalse()){

            Log.e("Six","add");
        }else{
            Log.e("Six","remove");

        }
    }

    private void clickImageDialog(View clickImageDialogView,int position){
        Button addFriendButton=clickImageDialogView.findViewById(R.id.addFriend);
        Button siLiaoButton=clickImageDialogView.findViewById(R.id.siLiao);
        Button siXinButton=clickImageDialogView.findViewById(R.id.sixroom_client_image_dialog_sixin);

       if(GongGongZiYuan.client.getZhanghao().equals(clientList.get(position).getZhanghao())){
           Log.e("Six","=========");

           clickImageDialog.setContentView(clickImageDialogView);
           Window dialogWindow=clickImageDialog.getWindow();
           WindowManager.LayoutParams lp=dialogWindow.getAttributes();
           dialogWindow.setGravity(Gravity.LEFT|Gravity.TOP);
           Log.e("Six","position="+position);
           lp.x=300*(position+1);
           lp.y=680*(position/3);
           lp.height=270;
           lp.width=470;
           dialogWindow.setAttributes(lp);
           setDialogClick(clickImageDialogView,position);
           clickImageDialog.create();
           clickImageDialog.show();
           siLiaoButton.setVisibility(View.GONE);
           addFriendButton.setVisibility(View.GONE);
           siXinButton.setVisibility(View.GONE);
       }else{
           Log.e("Six","!!!!!!!====");
           clickImageDialog.setContentView(clickImageDialogView);
           Window dialogWindow=clickImageDialog.getWindow();
           WindowManager.LayoutParams lp=dialogWindow.getAttributes();
           dialogWindow.setGravity(Gravity.LEFT|Gravity.TOP);
           Log.e("Six","position="+position);
           lp.x=300*(position+1);
           lp.y=680*(position/3);
           lp.height=820;
           lp.width=470;
           setDialogClick(clickImageDialogView,position);
           dialogWindow.setAttributes(lp);
           clickImageDialog.create();
           clickImageDialog.show();
       }

    }

//    private View setViewLucency(int[] ints){
//        addFriendButton.setVisibility(View.VISIBLE);
//        siLiaoButton.setVisibility(View.VISIBLE);
//        qinglvzhengshuButton.setVisibility(View.VISIBLE);
//        for(int i=0;i<ints.length;i++){
//            switch(ints[i]){
//                case 1:addFriendButton.setVisibility(View.INVISIBLE);
//                break;
//            }
//        }
//        return dialogView;
//    }

    private void setDialogClick(View clickImageDialogView,int position){
        Button addFriendButton=clickImageDialogView.findViewById(R.id.addFriend);
        Button siLiaoButton=clickImageDialogView.findViewById(R.id.siLiao);
        Button siXinButton=clickImageDialogView.findViewById(R.id.sixroom_client_image_dialog_sixin);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientAddFriend:/n"+position+"_");
            }
        });

        siLiaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liaotianEditText.setText("@"+clientList.get(position).getName()+":");
            }
        });

        siXinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiXin:/n"+clientList.get(position).getZhanghao()+"_");
            }
        });
    }

//    private void setTuichuRoomDialog(){
//        tuichuRoomDialog=new AlertDialog.Builder(SixRoomActivity.this);
//        tuichuRoomDialog.setTitle("提示");
//        tuichuRoomDialog.setMessage("退出房间并返回大厅？");
//        tuichuRoomDialog.setNegativeButton("取消",null);
//        tuichuRoomDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                gongGongZiYuan.sendMsg("tuichuRoom:_");
//            }
//        });
//        tuichuRoomDialog.create();
//    }


//
    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity#onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setReceiveListener();

    }


    @Override
    protected void onPause() {
        super.onPause();
        SocketClient.sInst.allDestoryListener();
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
