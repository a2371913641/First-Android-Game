package cn.itcast.gobang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import cn.itcast.gobang.AdapterUtil.DialogAdapter;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.ClientAccount;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.IOUtil;
import cn.itcast.gobang.Util.SiXin;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.Util.WriterThread;

public class MainActivity extends AppCompatActivity {
    public static final String ACOUNT_LIST = "账号名单";
    public static final String CHECK_BOX_ZHUANGTAI="勾选框状态";
    private GongGongZiYuan ziYuan;

    static final String MESSAGE_OK = "OK";

    String Edit_zhanghaoMima="baocunzhanghao.txt";

    String data=null;//接手服务器发来的消息
    EditText nuber,admin;
    IOUtil ioUtil=new IOUtil();
    CheckBox jizhumima,zidongdenglu,jizhuzhanghao;
    Button logOn,zhuce,button_showAccount;
    Handler whandler= WriterThread.wHandler;
    DialogAdapter dialogAdapter;
    static AlertDialog.Builder dialog_showAccount;
    GongGongZiYuan gongGongZiYuan;
    ReceiveListener receiveListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ziYuan = new GongGongZiYuan();
        setContentView(R.layout.activity_main);
        setClientAccount();
        initView();
        ZhuCe();
        logOn();
        showAccount();
        huifuFuXuanKuang();
        setZidongdenglu();

    }



    private void setReceiveListener(){
        SocketClient.sInst.addListener(receiveListener=new ReceiveListener() {
            @Override
            public void onReceive(String data) {
                String[] strings=data.split("/n");
                if(data.equals(MESSAGE_OK)){
                    if(jizhuzhanghao.isChecked()) {
                        ioUtil.outputFile(new File(getFilesDir(), ACOUNT_LIST).getAbsolutePath(),nuber.getText().toString()+"/n",true);
                    }

                    if(jizhumima.isChecked()&&data.equals(MESSAGE_OK)){
                        ioUtil.createFile(new File(getFilesDir(),nuber.getText().toString()+"密码").getAbsolutePath());
                        ioUtil.outputFile(new File(getFilesDir(),nuber.getText().toString()+"密码").getAbsolutePath(),admin.getText().toString(),false);
                        ziYuan.clientAccounts.add(new ClientAccount(nuber.getText().toString(),admin.getText().toString()));
                        File baocunzhanghao=new File(getFilesDir(),Edit_zhanghaoMima);
                        if(!baocunzhanghao.exists()){
                            ioUtil.createFile(baocunzhanghao.getAbsolutePath());
                        }
                        ioUtil.outputFile(baocunzhanghao.getAbsolutePath(),
                                nuber.getText().toString()+"/n"+admin.getText().toString(),
                                false);
                    }

                    File gouxuankuangzhuangtai=new File(getFilesDir(),CHECK_BOX_ZHUANGTAI);
                    if(!gouxuankuangzhuangtai.exists()){
                        ioUtil.createFile(gouxuankuangzhuangtai.getAbsolutePath());
                    }

                    ioUtil.outputFile(gouxuankuangzhuangtai.getAbsolutePath(),fuXuanKuangZhuangTai(),false);
                    Intent intent=new Intent(MainActivity.this,ThirdlyActivity.class);
                    startActivity(intent);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                if(strings[0].equals("denglu:")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            IOUtil io=new IOUtil();
//                            io.deleteFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+"的信箱.txt").getAbsolutePath());
                            Log.e("Main","xinxiang="+ ioUtil.inputFile(new File(getFilesDir(),GongGongZiYuan.client.getName()+"的信箱.txt").getAbsolutePath()));
                            Toast.makeText(MainActivity.this, strings[1],Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if(strings[0].equals("Notdenglu:")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, strings[1],Toast.LENGTH_SHORT).show();
                        }
                    });


                    SocketClient.sInst.setEnd();
                    SocketClient.sInst=null;
                }

                if(strings[0].equals("setClient:")){
                    GongGongZiYuan.client=new Client(strings[1],strings[2],strings[3],Integer.parseInt(strings[4]),Boolean.parseBoolean(strings[5]));
                }

            }
        });
    }




    private void initView(){
        gongGongZiYuan=new GongGongZiYuan();
        nuber=(EditText) findViewById(R.id.onenuber);
        admin=(EditText) findViewById(R.id.oneadmin);
        jizhumima=(CheckBox) findViewById(R.id.onejizhumima);
        jizhuzhanghao=(CheckBox)findViewById(R.id.onejizhuzhanghao);
        logOn=(Button) findViewById(R.id.button_oneLogOn);
        zhuce=(Button) findViewById(R.id.button_onezhuce);
        button_showAccount=(Button)findViewById(R.id.button_oneShowAccount);
        zidongdenglu=(CheckBox) findViewById(R.id.onezidongdenglu);
        dialog_showAccount=new AlertDialog.Builder(this);
        dialogAdapter=new DialogAdapter(MainActivity.this,
                R.layout.show_account_dialog_item, ziYuan.clientAccounts,
                nuber,admin);
    }

    private void ZhuCe(){
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logOn() {
        logOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SocketClient.sInst!=null){
                    Log.e("Main",SocketClient.sInst.toString());
                    SocketClient.sInst.setEnd();
                    SocketClient.sInst.allDestoryListener();
                    SocketClient.sInst = null;
                }else {
                    SocketClient.sInst=new SocketClient();
                    Log.e("Main","SocketClient==null:"+SocketClient.sInst.toString());
                    Log.e("Main","sInst="+SocketClient.sInst.toString());
                    SocketClient.sInst.start();
                }
                setReceiveListener();
                String zhanghao = nuber.getText().toString();
                String mima = admin.getText().toString();
                Log.e("Main","zhanghao="+zhanghao+",mima="+mima);
                Message msg=new Message();
                msg.what=1;
                msg.obj="login:/n"+zhanghao+"/n"+mima+ "_";
                Log.e("Main","msg.obj"+msg.obj.toString());
                whandler.sendMessage(msg);
            }
        });
    }

//    public void storeAccount(){//储存账号密码
//        if(jizhuzhanghao.isChecked()&&data.equals("OK")) {
//            ioUtil.outputFile(new File(getFilesDir(),"账号名单").getAbsolutePath(),nuber.getText().toString()+"/n");
//        }
//
//        if(jizhumima.isChecked()&&data.equals("OK")){
//            ioUtil.createFile(new File(getFilesDir(),nuber.getText().toString()+"密码").getAbsolutePath());
//            ioUtil.outputFile(new File(getFilesDir(),nuber.getText().toString()+"密码").getAbsolutePath(),admin.getText().toString());
//        }
//    }

    private void showAccount(){
        button_showAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1=View.inflate(MainActivity.this,R.layout.show_account_dialog,null);
                ListView listView_account=view1.findViewById(R.id.dialog_listview);
                listView_account.setAdapter(dialogAdapter);
                dialog_showAccount.setTitle("选择账号")
                        .setView(view1)
                        .setPositiveButton("确定",null)
                        .create();
                dialog_showAccount.show();
            }
        });
    }

    public void setClientAccount() {
        String s=ioUtil.inputFile(new File(getFilesDir(), ACOUNT_LIST).getAbsolutePath());
        Log.e("MainActivity","s="+s);
        Log.e("MainActivity","accounts前="+ziYuan.clientAccounts.size()); // 1
        if(s!=null) {
            String[] allAccount = s.split("/n");
            Log.e("Main","s[]"+allAccount);

            for (int i = 0; i < allAccount.length; i++) {
                Log.e("MAin","s["+i+"]"+allAccount[i]);
                ClientAccount clientAccount = new ClientAccount();
                clientAccount.setZhanghao(allAccount[i]);

                clientAccount.setMima(ioUtil.inputFile(new File(getFilesDir(), allAccount[i] + "密码").getAbsolutePath()));
                ziYuan.clientAccounts.add(clientAccount);
            }

            Log.e("MainActivity","accounts后="+ziYuan.clientAccounts.size()); // 4
        }
    }

    private void setZidongdenglu(){
        String s=ioUtil.inputFile(new File(getFilesDir(),Edit_zhanghaoMima).getAbsolutePath());
        if(s!=null) {
            String[] strings=s.split("/n");
            Log.e("MainActivity","strings="+strings.length);
            ClientAccount clientAccount = new ClientAccount();
                clientAccount.setZhanghao(strings[0]);
            if(jizhuzhanghao.isChecked()) {
                    nuber.setText(clientAccount.getZhanghao());
                }
            if(strings.length>1) {
                clientAccount.setMima(strings[1]);
                if (jizhumima.isChecked()) {
                    admin.setText(clientAccount.getMima());
                }
            }

            if (zidongdenglu.isChecked()&&nuber.getText()!=null&&admin.getText()!=null) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = nuber.getText().toString() + "/n" + admin.getText().toString()+ "_";
                Log.e("Main", "msg.obj" + msg.obj.toString());
                whandler.sendMessage(msg);
            }
        }
    }

    private void huifuFuXuanKuang(){
        File file=new File(getFilesDir(),CHECK_BOX_ZHUANGTAI);
        if(file.exists()){
            String[] s=ioUtil.inputFile(file.getAbsolutePath()).split("/n");
            if(s[0].equals("1")){
                jizhuzhanghao.setChecked(true);
            }
            if(s[1].equals("1")){
                jizhumima.setChecked(true);
            }

            if(s[2].equals("1")){
                zidongdenglu.setChecked(true);
            }
        }
    }

        @Override
    public void onResume(){
        super.onResume();
        dialogAdapter.notifyDataSetChanged();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        Log.e("Main","onPause()");
        Log.e("Main","onPause()：accounts="+ziYuan.clientAccounts.size());
        super.onPause();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<ziYuan.clientAccounts.size();i++){
            Log.e("Main","ziYuan.clientAccounts.get("+i+").getZhanghao()="+ziYuan.clientAccounts.get(i).getZhanghao());
            sb.append(ziYuan.clientAccounts.get(i).getZhanghao()+"/n");
        }
        Log.e("Main",sb.toString());
        ioUtil.outputFile(new File(getFilesDir(), ACOUNT_LIST).getAbsolutePath(),sb.toString(),false);
    }




    private String fuXuanKuangZhuangTai(){
        int zhanghao=0;
        int mima=0;
        int denglu=0;
        if(jizhuzhanghao.isChecked()){
            zhanghao=1;
        }

        if(jizhumima.isChecked()){
            mima=1;
        }

        if(zidongdenglu.isChecked()){
            denglu=1;
        }

        return zhanghao+"/n"+mima+"/n"+denglu;
    }

    @Override
    protected void onDestroy() {
        Log.e("Main","onDestroy()");
        if(SocketClient.sInst!=null) {
            SocketClient.sInst.destroyLintener(receiveListener);
        }
        super.onDestroy();
    }
}