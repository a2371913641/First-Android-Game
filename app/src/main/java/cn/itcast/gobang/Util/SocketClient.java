package cn.itcast.gobang.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.ReceiveListener;


public class SocketClient {
    String TAG="SocketClient";
    Socket socket;
    private ReaderThread readerThread;
    private Writer writer;
    private volatile static SocketClient sInst=null;
    List<ReceiveListener> listenerList=new ArrayList<>();
    //isStart==0--未开始    isStart==1--开始
    private int isStart=0;
    // 1. socketClient newSocket
    // 2. writeThread ↑  3. readThread  ↑
    //
    // 23 -> 1
    // synchronized
    // 23 -> newSocket

    public static SocketClient getInst(){
        if(sInst==null) {
            synchronized (SocketClient.class) {
                if (sInst == null) {
                    sInst = new SocketClient();
                }
            }
        }
        return sInst;
    }

    public int getIsStart(){
        return isStart;
    }

    public Writer getWriter(){
        return this.writer;
    }
    public void setStart(){
        readerThread=new ReaderThread(this,listenerList);
        readerThread.start();
        writer =new Writer(this);
        isStart=1;
        Log.e(TAG,"开始");
    }

    public synchronized  Socket initSocket() {
        Log.e(TAG,"initSocket0000000000");
        if (this.socket == null) {

            try {
                //172.22.243.12
                //192.168.100.88
                //192.168.137.81
                //172.22.60.128
                //192.168.53.56
                socket = new Socket("192.168.0.102", 8088);
                Log.e("SocketClient", "衣联网1,socket=" + socket.hashCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG,"initSocket11111111111");
        return socket;
    }

    public void setEnd() {
        readerThread.setJieshu(true);
        writer.setJieshu(true);
        isStart=0;
//        Looper.loop();
        try {
            socket.close();
            socket=null;
            Log.e(TAG,"setEnd()");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addListener(ReceiveListener l){
        listenerList.add(l);
        Log.e(TAG,"listener.hashCode="+l.hashCode());
    }
    public void destroyLintener(ReceiveListener l){
        Log.e("SocketClient","remove"+(listenerList.size()-1));
        listenerList.remove(l);
    }

    public void allDestoryListener(){
        Log.e(TAG,"allDestoryListener");
        for(int i=listenerList.size()-1;i>=0;i=i-1){
            listenerList.remove(i);
        }
    }

    public int getListenerListSize(){
        for(int i=0;i<listenerList.size();i++){
            Log.e(TAG,"getListenerListSize["+i+"]="+listenerList.get(i).hashCode());
        }
        return listenerList.size();
    }



}