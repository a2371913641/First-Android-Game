package cn.itcast.gobang.ServeActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.MainActivity;
import cn.itcast.gobang.ReceiveListener;
import cn.itcast.gobang.ThirdlyActivity;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.ClientAccount;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.IOReadWrite;
import cn.itcast.gobang.Util.SocketClient;


public class ServeMain {
    String TAG="ServerMain";
    static final String MESSAGE_OK = "OK";
    Context context;
    Activity MainActivity;
    GongGongZiYuan gongGongZiYuan =new GongGongZiYuan();
    IOReadWrite ioReadWrite;
    LoginListener loginListener = new LoginListener();

    class LoginListener implements ReceiveListener {
        Runnable optionsR = null;

        @Override
        public void onReceive(String data) {
            String[] strings=data.split("/n");
            Log.e("ServeMainActivity",data);
            if(data.equals(MESSAGE_OK)){
                if (optionsR != null) optionsR.run();
                Intent intent=new Intent(context, ThirdlyActivity.class);
                context.startActivity(intent);
                MainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if(strings[0].equals("denglu:")) {
                MainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(context, strings[1],Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if(strings[0].equals("Notdenglu:")){
                MainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, strings[1],Toast.LENGTH_SHORT).show();
                    }
                });


                SocketClient.getInst().setEnd();
                SocketClient.getInst().allDestoryListener();
            }

            if(strings[0].equals("setClient:")){
                GongGongZiYuan.client=new Client(strings[1],strings[2],strings[3],Integer.parseInt(strings[4]),Boolean.parseBoolean(strings[5]));
            }

        }
    }

    //监听器处理消息
    private void setReceiveListener(){
        SocketClient.getInst().addListener(loginListener);
    }

    public ServeMain(Context context,Activity activity){
        this.context=context;
        this.MainActivity=activity;
        ioReadWrite=new IOReadWrite(context);
    }

    public void connectServerAndSetReceiveListener(){
        connectServer();
        setReceiveListener();
    }

    private void connectServer(){
        Log.e(TAG,"connectServer()");
        Log.e(TAG,"SocketClient.getInst().getIsStart()="+SocketClient.getInst().getIsStart());
        if(SocketClient.getInst().getIsStart()!=0){
            SocketClient.getInst().setEnd();
            SocketClient.getInst().allDestoryListener();
        }


        SocketClient.getInst().setStart();

    }



    public void zhuCe(Context activity1,Class<?> activity2){
        Intent intent=new Intent(activity1,activity2);
        activity1.startActivity(intent);
    }

    public ClientAccount getLastAcount() {
        List<ClientAccount> al = getClientAccount();
        return al.get(al.size() - 1);
    }

    public List<ClientAccount> getClientAccount() {
        String s=getAllAccount();
        List<ClientAccount> clientAccounts=new ArrayList<>();
        if(s!=null) {
            String[] allAccount = s.split("/n");

            for (int i = 0; i < allAccount.length; i++) {
                Log.e("MAin","s["+i+"]"+allAccount[i]);
                ClientAccount clientAccount = new ClientAccount();
                clientAccount.setZhanghao(allAccount[i]);
                Log.e("getMiMa","zhanghao="+allAccount[i]+",MiMa="+getMiMa(allAccount[i]));
                clientAccount.setMima(getMiMa(allAccount[i]));
                clientAccounts.add(clientAccount);
            }

            Log.e("MainActivity","accounts后="+ clientAccounts.size()); // 4
        }
        return clientAccounts;
    }

    //请求登录
    public void autoLogin(String acount, String mima) {
        connectServerAndSetReceiveListener();
        sendLoginRequest(acount,mima);
    }


    public void getZhangHaoMiMaAndXuanXiangAndApplyLogin(String zhanghao, String mima, Boolean isJiZhuZhangHao, Boolean isJiZhuMiMa, Boolean isZiDongDenLu){

            connectServerAndSetReceiveListener();
        // todo 将监听器设计放入设计中， 重构监听器相关的代码
        loginListener.optionsR = new Runnable() {
            @Override
            public void run() {
                executeJizhuZhanghaoAndMima(zhanghao, mima, isJiZhuZhangHao, isJiZhuMiMa);
                baoCunGouxuankuangZhuangTai(isJiZhuZhangHao, isJiZhuMiMa, isZiDongDenLu);
            }
        };

        sendLoginRequest(zhanghao,mima);
    }


    //获取上一次登录时的勾选框状态
    public String getLastGouXuanKuangZhuangTai(){
        return ioReadWrite.readGouXuanKuang();
    }

    //当删除账号后，重新保存的账号
    public void resetBaoCunAccount(List<ClientAccount> accounts){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<accounts.size();i++){
            Log.e("Main","ziYuan.clientAccounts.get("+i+").getZhanghao()="+accounts.get(i).getZhanghao());
            sb.append(accounts.get(i).getZhanghao()+"/n");
        }
        Log.e("Main",sb.toString());
        ioReadWrite.writeResetBaoCunAccount(sb.toString());
    }


    //执行是保存账号密码和是否自动登录
    private void executeJizhuZhanghaoAndMima(String zhanghao, String mima,
                                             boolean isJiZhuZhangHao, boolean isJiZhuMiMa){
        if(isJiZhuZhangHao) {
            baocunZhanghao(zhanghao);
            if(isJiZhuMiMa){
                Log.e("baocunMiMa","zhanghao="+zhanghao+",mima="+mima);
               baocunMiMa(zhanghao,mima);
            }
        }
    }



    //请求登录
    private void sendLoginRequest(String zhanghao,String mima){
        Log.e("ServeMainActivity","sendLoginRequest");
        gongGongZiYuan.sendMsg("login:/n"+zhanghao+"/n"+mima+ "_");
    }

    public void baoCunGouxuankuangZhuangTai(Boolean isJiZhuZhangHao,Boolean isJiZhuMiMa,Boolean isZiDongDenLu){
        ioReadWrite.writeGouXuanKuangZhuangTai(fuXuanKuangZhuangTai(isJiZhuZhangHao,isJiZhuMiMa,isZiDongDenLu));

    }

    public void deleteMiMa(String zhanghao){
        ioReadWrite.deleteMiMa(zhanghao);
    }

    private String getAllAccount(){
        return ioReadWrite.readAllZhangHao();
    }

    private String getMiMa(String zhanghao){
        return ioReadWrite.readMiMa(zhanghao);
    }

    private void baocunZhanghao(String zhanghao){
        ioReadWrite.WriteZhangHao(zhanghao);
    }

    private void baocunMiMa(String zhanghao, String mima){
        ioReadWrite.WriteMiMa(zhanghao,mima);
    }

    private String fuXuanKuangZhuangTai(Boolean isJiZhuZhangHao,Boolean isJiZhuMiMa,Boolean isZiDongDenLu){
        int zhanghao=0;
        int mima=0;
        int denglu=0;
        if(isJiZhuZhangHao){
            zhanghao=1;
        }

        if(isJiZhuMiMa){
            mima=1;
        }

        if(isZiDongDenLu){
            denglu=1;
        }

        return zhanghao+"/n"+mima+"/n"+denglu;
    }
}
