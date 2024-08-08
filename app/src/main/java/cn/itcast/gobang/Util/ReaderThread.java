package cn.itcast.gobang.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import cn.itcast.gobang.ReceiveListener;


public class ReaderThread extends Thread {
    Socket socket;
    volatile boolean jieshu=false;
    List<ReceiveListener> listenerList;


    static final int MainActivityMsg=1;
    static final int ThirdlyActivity=2;
    public ReaderThread(Socket socket,  List<ReceiveListener> listenerList) {
        super();
        this.socket = socket;

        this.listenerList=listenerList;
    }

    @Override
    public void run() {
        try {

            InputStream is = socket.getInputStream();
            byte[] buff = new byte[1024];
            do {
                int len = is.read(buff);
                if (len > 0) {
                    Message msg = new Message();
                    String s = new String(buff, 0, len);
                    String[] strings = s.split("_");
                    Log.e("SocketClient", new String(buff, 0, len));
                    for (String s1 : strings) {
                        Log.e("SocketClient", "s1=" + s1);
                        sendMsg(s1);
                    }
                }
            } while (!jieshu);


        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Reader","结束");

    }

    private void sendMsg(String s){
        for(ReceiveListener listener:listenerList){
            listener.onReceive(s);
        }
    }

    public void setJieshu(Boolean jieshu){
        this.jieshu=jieshu;
    }
}