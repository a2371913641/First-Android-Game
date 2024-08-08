package cn.itcast.gobang.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WriterThread extends Thread{
    static final int MainActivityMsg=1;
    static final int ThirdlyActivity=2;

    Socket socket;
    volatile Boolean jieshu=false;
    static volatile String data=null;
    public static Handler wHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                data=msg.obj.toString();
        }
    };
    public WriterThread(Socket socket){
        super();
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            OutputStream os=socket.getOutputStream();
            while (true){
                if(data!=null) {
                    Log.e("Writer",data.toString());
                    os.write(data.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    data=null;
                }
                if(jieshu){
                    break;
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        Log.e("Writer","结束");
    }

    public void setJieshu(Boolean jieshu){
        this.jieshu=jieshu;
    }
}
