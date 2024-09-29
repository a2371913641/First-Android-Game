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
     for(int i=4;i<chessBoardPieces.length;i++){
         for(int x=i,y=0;x>0&&y<chessBoardPieces.length-1;x--,y++){
             if(chessBoardPieces[x][y]==chessBoardPieces[x-1][y=1]&&chessBoardPieces[x][y]!=-1){
                 number++;
                 Log.e(TAG,"number="+number);
                 if(number>=5){
                     return true;
                 }
             }
         }
         number=1;
     }

     for(int j=0;j<5-1;j++){
         for(int x=chessBoardPieces.length-1,y=j;x>0&&y<5-1;x--,y++){
             if(chessBoardPieces[x][y]==chessBoardPieces[x-1][y+1]&&chessBoardPieces[x][y]!=-1){
                 number++;
                 Log.e(TAG,"number="+number);
                 if(number>=5){
                     return true;
                 }
             }
         }
         number=1;
     }
        //从左往右斜




        return false;
    }

}
