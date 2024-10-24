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
    String TAG="ReaderThread";
    Socket socket;
    SocketClient socketClient;
    volatile boolean jieshu=false;
    List<ReceiveListener> listenerList;


    static final int MainActivityMsg=1;
    static final int ThirdlyActivity=2;
    public ReaderThread(SocketClient socketClient,  List<ReceiveListener> listenerList) {
        super();
        this.socketClient = socketClient;
        this.listenerList=listenerList;
        Log.e(TAG,"ReaderThread");
    }

    @Override
    public void run() {
        try {
            //该处再次判空（网络异常）
            if(socket==null) {
                this.socket = socketClient.initSocket();
                Log.e(TAG, "socketClient="+socketClient.hashCode()+",socket=" + socket.hashCode());
            }

            InputStream is = socket.getInputStream();
            byte[] buff = new byte[1024];
            do {
                int len = is.read(buff);
                if (len > 0) {
                    String s = new String(buff, 0, len);
                    String[] strings = s.split("_");
                    Log.e("SocketClient", new String(buff, 0, len));
                    for (String s1 : strings) {
                        Log.e("SocketClient", "s1=" + s1);
                        sendServerMsg(s1);
                    }
                }
            } while (!jieshu);


        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Reader","结束");

    }

    private void sendServerMsg(String s){
        Log.e(TAG,"s="+s);
        for(ReceiveListener listener:listenerList){
            listener.onReceive(s);
            Log.e(TAG,"listener="+listener.hashCode());
        }
    }



    public void setJieshu(Boolean jieshu){
        this.jieshu=jieshu;
    }
}