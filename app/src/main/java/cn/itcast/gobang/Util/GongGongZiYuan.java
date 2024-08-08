package cn.itcast.gobang.Util;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;


public class GongGongZiYuan {
    Handler whandler=WriterThread.wHandler;
    public static Client client;
    public ArrayList<ClientAccount> clientAccounts=new ArrayList<>();
    public static Room room;


    public GongGongZiYuan(){

    }

    public void sendMsg(String data){
        Message msg=new Message();
        msg.obj=data;
        whandler.sendMessage(msg);
    }
}
