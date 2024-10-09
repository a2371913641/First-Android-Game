package cn.itcast.gobang.ServeActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.time.Instant;

import cn.itcast.gobang.MyView.Chessboard;
import cn.itcast.gobang.ReceiveListener;
import cn.itcast.gobang.Util.ChessBoardListener;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.SocketClient;
import cn.itcast.gobang.WZQGameActivity;

public class ServeWZQ {
    String TAG="ServerWZQ";
    Context context;
    Chessboard chessboard;
    Handler WZQGaneActivityHandler;
    ChessBoardListener chessBoardListener;
    GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();

    public ServeWZQ(Context context, Chessboard chessboard,Handler handler){
        this.context=context;
        this.chessboard=chessboard;
        this.WZQGaneActivityHandler=handler;
        setChessBoardListener();
        setWZQListener(new WZQListener());
    }

    private void setChessBoardListener(){
        chessboard.setChessBoardListener(chessBoardListener=new ChessBoardListener() {
            @Override
            public void onReceive(String s) {
                gongGongZiYuan.sendMsg("ClientPlayChess:/n"+s+"_");
                StringBuilder sb=new StringBuilder();
                sb.append("\n");
                for(int i=0;i<chessboard.chessBoardPieces.length;i++){
                    for(int j=0;j<chessboard.chessBoardPieces[i].length;j++){
                        sb.append(chessboard.chessBoardPieces[i][j]);
                    }
                    sb.append("\n");
                }
                Log.e(TAG,"棋盘："+sb.toString());
                Log.e(TAG,"callTheGame()="+callTheGame(chessboard.chessBoardPieces));
                if(callTheGame(chessboard.chessBoardPieces)){
                    Log.e(TAG,"callTheGame()="+callTheGame(chessboard.chessBoardPieces));
                    WZQGaneActivityHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    gongGongZiYuan.sendMsg("ClientGameOver:/n"+GongGongZiYuan.client.getName()+"_");

                }
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

                case "ServerGameOver:":
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Game Over");
                    builder.setTitle(strings[1]);
                    builder.setPositiveButton("确认",null);
                    Log.e(TAG,"未Handler.post");
                    WZQGaneActivityHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG,"进Handler.post");
                            builder.create().show();
                            Toast.makeText(context,strings[1],Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    }

    private Boolean callTheGame(int[][] chessBoardPieces){
        int number=1;
        for(int i=0;i<chessBoardPieces.length;i++){
            for(int j=0;j<chessBoardPieces[i].length-1;j++){
                if(chessBoardPieces[i][j]==chessBoardPieces[i][j+1]&&chessBoardPieces[i][j]!=-1){
                    number++;
                    if(number>=5){
                        return true;
                    }
                }
            }
            number=1;
        }

        for(int j=0;j<chessBoardPieces.length;j++){
            for(int i=0;i<chessBoardPieces.length-1;i++){
                if(chessBoardPieces[i][j]==chessBoardPieces[i+1][j]&&chessBoardPieces[i][j]!=-1){
                    number++;
                    if(number>=5){
                        return true;
                    }
                }
            }
            number=1;
        }

        //从右往左斜

        for(int x=0;x<chessBoardPieces.length-1;x++){
            for(int y=0,a=x;y<x&&a>0;y++,a--){
                if(a<chessBoardPieces.length-1&&y>=0&&y<chessBoardPieces.length-1){
                    if(chessBoardPieces[a][y]==chessBoardPieces[a-1][y+1]&&chessBoardPieces[a][y]!=-1){
                        number++;
                        Log.e(TAG,"内1number="+number+"  "+chessBoardPieces[a][y]);
                        if(number>=5){
                            return true;
                        }
                    }
                }
            }
            number=1;
        }

        for(int x=1;x<chessBoardPieces.length-1;x++){
            for (int a=x,y=chessBoardPieces.length-1;a<chessBoardPieces.length-1&&y>x;a++,y--){
                if(a>=1&&a<chessBoardPieces.length-1&&y<=chessBoardPieces.length-1&&y>x){
                    if(chessBoardPieces[a][y]==chessBoardPieces[a+1][y-1]&&chessBoardPieces[a][y]!=-1){
                        number++;
                        Log.e(TAG,"内1number="+number+"  "+chessBoardPieces[a][y]);
                        if(number>=5){
                            return true;
                        }
                    }
                }
            }
            number=1;
        }

        //从左往右斜

        for(int y=chessBoardPieces.length-1;y>=0;y--){
            for(int x=0,a=y;a<chessBoardPieces.length-1;x++,a++){
                    if(chessBoardPieces[x][a]==chessBoardPieces[x+1][a+1]&&chessBoardPieces[x][a]!=-1){
                        number++;
                        if(number>=5){
                            return true;
                        }
                }
            }
            number=1;
        }

        for(int x=1;x<chessBoardPieces.length-1;x++){
            for(int a=x,y=0;a<chessBoardPieces.length-1&&y<chessBoardPieces.length-1;a++,y++){
                if(chessBoardPieces[a][y]==chessBoardPieces[a+1][y+1]&&chessBoardPieces[a][y]!=-1){
                    number++;
                    if(number>=5){
                        return true;
                    }
                }
            }
            number=1;
        }

        Log.e(TAG,"外number="+number);

        return false;
    }

}
