package cn.itcast.gobang.ServeActivity;

import android.content.Context;
import android.os.Handler;

import java.time.Instant;

import cn.itcast.gobang.MyView.Chessboard;
import cn.itcast.gobang.ReceiveListener;
import cn.itcast.gobang.Util.ChessBoardListener;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.SocketClient;

public class ServeWZQ {
    Context context;
    Chessboard chessboard;
    Handler WZQGaneActivityHander;
    ChessBoardListener chessBoardListener;
    GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();

    public ServeWZQ(Context context, Chessboard chessboard,Handler handler){
        this.context=context;
        this.chessboard=chessboard;
        this.WZQGaneActivityHander=handler;
        setChessBoardListener();
        setWZQListener(new WZQListener());
    }

    private void setChessBoardListener(){
        chessboard.setChessBoardListener(chessBoardListener=new ChessBoardListener() {
            @Override
            public void onReceive(String s) {
                gongGongZiYuan.sendMsg("ClientPlayChess:/n"+s+"_");
            }
        });
    }

    private void setWZQListener(WZQListener wzqListener){
        SocketClient.sInst.addListener(wzqListener);
    }

    class WZQListener implements ReceiveListener{

        @Override
        public void onReceive(String data) {
            String[] strings=data.split("/n");
            switch (strings[0]){
                case "ServerPlayChess:":
                    chessboard.drawRivalChessPiesces(Integer.parseInt(strings[1]),Integer.parseInt(strings[2]),Integer.parseInt(strings[3]));
                    break;
            }
        }
    }

}
