package cn.itcast.gobang;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.AdapterUtil.ShowAccountDialogAdapter;
import cn.itcast.gobang.ServeActivity.ServeMain;
import cn.itcast.gobang.Util.ClientAccount;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.SocketClient;

public class MainActivity extends AppCompatActivity {


    EditText nuberEditText, adminEditText;
    CheckBox jizhumima,zidongdenglu,jizhuzhanghao;
    Button logOn,zhuce,button_showAccount;
    ShowAccountDialogAdapter showAccountDialogAdapter;
    static AlertDialog.Builder dialog_showAccount;
    GongGongZiYuan gongGongZiYuan;
    ReceiveListener receiveListener;
    ServeMain serveMain;
    List<ClientAccount> clientAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gongGongZiYuan = new GongGongZiYuan();
        setContentView(R.layout.activity_main);
        serveMain=new ServeMain(MainActivity.this,MainActivity.this);
        clientAccounts=new ArrayList<>();
        initView();
        huiFuGouXuanKuang(serveMain.getLastGouXuanKuangZhuangTai());
        executeFuXuanKuang();
        ZhuCe();
        logOn();
        showAccount();
    }


    /**
     * 1. 打开应用
     * 2. 获取所有账号密码
     * 3. 获取选项，设置复选框
     * 4. （如果）设置账号密码 （如果）登录
     * 5. （手动输入账号密码）
     * 6. （勾选框）
     * 7. 登录
     *
     * 1. 记住流程，设计先写流程
     * 2. 设计跟着流程走
     * 3. 代码跟着设计走
     * 4.Runnable是类，它new出来的东西是对象，
     */



    private void initView(){
        gongGongZiYuan=new GongGongZiYuan();
        nuberEditText =(EditText) findViewById(R.id.onenuber);
        adminEditText =(EditText) findViewById(R.id.oneadmin);
        jizhumima=(CheckBox) findViewById(R.id.onejizhumima);
        jizhuzhanghao=(CheckBox)findViewById(R.id.onejizhuzhanghao);
        logOn=(Button) findViewById(R.id.button_oneLogOn);
        zhuce=(Button) findViewById(R.id.button_onezhuce);
        button_showAccount=(Button)findViewById(R.id.button_oneShowAccount);
        zidongdenglu=(CheckBox) findViewById(R.id.onezidongdenglu);
        dialog_showAccount=new AlertDialog.Builder(this);
        showAccountDialogAdapter =new ShowAccountDialogAdapter(MainActivity.this,
                R.layout.show_account_dialog_item,
                nuberEditText, adminEditText);
    }

    private void ZhuCe(){
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               serveMain.zhuCe(MainActivity.this,SecondActivity.class);
            }
        });
    }

    private void huiFuGouXuanKuang(String s){
        String[] strings=s.split("/n");
        if(strings[0].equals("1")){
            jizhuzhanghao.setChecked(true);
        }
        if(strings[1].equals("1")){
            jizhumima.setChecked(true);
        }
        if(strings[2].equals("1")){
            zidongdenglu.setChecked(true);
        }

    }

    private void logOn() {
        logOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serveMain.getZhangHaoMiMaAndXuanXiangAndApplyLogin(nuberEditText.getText().toString(),
                        adminEditText.getText().toString(),jizhuzhanghao.isChecked(),
                        jizhumima.isChecked(),zidongdenglu.isChecked());
            }
        });
    }



    private void showAccount(){
        button_showAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1=View.inflate(MainActivity.this,R.layout.show_account_dialog,null);
                ListView listView_account=view1.findViewById(R.id.dialog_listview);
                listView_account.setAdapter(showAccountDialogAdapter);
                dialog_showAccount.setTitle("选择账号")
                        .setView(view1)
                        .setPositiveButton("确定",null)
                        .create();
                dialog_showAccount.show();
            }
        });
    }

    //执行复选框
    private void executeFuXuanKuang() {
        ClientAccount a = serveMain.getLastAcount();
        if (jizhuzhanghao.isChecked()) {
            nuberEditText.setText(a.getZhanghao());
            if (jizhumima.isChecked()) {
                adminEditText.setText(a.getMima());
                if (zidongdenglu.isChecked()) {
                    serveMain.getZhangHaoMiMaAndXuanXiangAndApplyLogin(nuberEditText.getText().toString(),
                            adminEditText.getText().toString(), jizhuzhanghao.isChecked(),
                            jizhumima.isChecked(),zidongdenglu.isChecked());
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        showAccountDialogAdapter.notifyDataSetChanged();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }






    @Override
    protected void onDestroy() {
        Log.e("Main","onDestroy()");
        if(SocketClient.getInst()!=null) {
            gongGongZiYuan.sendMsg("wanquantuichu:_");
            SocketClient.getInst().destroyLintener(receiveListener);
        }
        super.onDestroy();
    }






}