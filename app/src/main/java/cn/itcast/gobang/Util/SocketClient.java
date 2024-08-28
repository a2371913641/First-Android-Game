package cn.itcast.gobang.Util;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.ReceiveListener;


public class SocketClient extends Thread {
    Socket socket;
    public ReaderThread readerThread;
    public WriterThread writerThread;
    public static SocketClient sInst=null;
    List<ReceiveListener> listenerList=new ArrayList<>();
    public SocketClient( ){

    }

    public void setEnd() {
        readerThread.setJieshu(true);
        writerThread.setJieshu(true);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            //172.22.243.12
            //192.168.100.88
            //192.168.137.81
            //172.22.60.128
            //192.168.53.56
            socket = new Socket("192.168.100.88", 8088);
            Log.e("SocketClient","衣联网");
            readerThread=new ReaderThread(socket,listenerList);
            readerThread.start();
            writerThread =new WriterThread(socket);
            writerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListener(ReceiveListener l){
        listenerList.add(l);
    }
    public void destroyLintener(ReceiveListener l){
        Log.e("SocketClient","remove"+(listenerList.size()-1));
        listenerList.remove(l);
    }

    public void allDestoryListener(){
        for(int i=listenerList.size()-1;i>=0;i=i-1){
            listenerList.remove(i);
        }
    }

    public int getListenerListSize(){
        return listenerList.size();
    }



}