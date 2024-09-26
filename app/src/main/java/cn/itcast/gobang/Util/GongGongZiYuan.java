package cn.itcast.gobang.Util;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.SixRoomActivity;


public class GongGongZiYuan {
    String XINXIANG="的信箱.txt";
    Handler whandler=WriterThread.wHandler;
    public static Client client;
    public static List<SiXin> siXins=new ArrayList<>();
    public static Room room;



    public GongGongZiYuan(){

    }

    public void sendMsg(String data){
        Message msg=new Message();
        msg.obj=data;
        whandler.sendMessage(msg);
    }



}
