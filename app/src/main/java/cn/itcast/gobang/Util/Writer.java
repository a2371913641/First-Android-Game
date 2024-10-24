package cn.itcast.gobang.Util;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Writer{
    String TAG="Writer";
    Socket socket;
    volatile Boolean jieshu=false;
    static volatile String data=null;
    public  Handler wHandler;
    HandlerThread  handlerThread;
    SocketClient socketClient;
    // 负责发送
    // 主动触发, 调用一个方法来发送
    public Writer(SocketClient socketClient){
        super();
        this.socketClient = socketClient;
        handlerThread=new HandlerThread("huoqvxiaoxi");
        handlerThread.start();
        wHandler=new Handler(handlerThread.getLooper());
        Log.e(TAG,"wHandler="+wHandler.hashCode());
    }

    public void setData(String d) throws IOException {
        wHandler.post(new Runnable() {
            @Override
            public void run() {
                if (socket == null) {
                    socket = socketClient.initSocket();
                    Log.e(TAG,"socketClient="+socketClient.hashCode()+",socket="+socket.hashCode());
                }
                if (socket == null) {
                    // 弹窗提示网络未连接
                    return;
                }
                try {
                    OutputStream os = socket.getOutputStream();
                    os.write(d.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                } catch (IOException e) {

                }
            }
        });
    }


    // 1. 判空并等待
    // 2. 设计好流程， 保证它不是空
//

    public void setJieshu(Boolean jieshu){
        this.jieshu=jieshu;
    }
}