package cn.itcast.gobang;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.itcast.gobang.MyView.BlackChessPiecesView;
import cn.itcast.gobang.MyView.Chessboard;
import cn.itcast.gobang.MyView.WhiteChessPiecesView;
import cn.itcast.gobang.ServeActivity.ServeWZQ;
import cn.itcast.gobang.Util.ChessBoardListener;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.SocketClient;


public class WZQGameActivity extends AppCompatActivity {

    String TAG = "WZQGameActivity";
    Paint whitePaint;
    int i = 0;
    Handler handler;
    Chessboard chessboard;
    ServeWZQ serveWZQ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzqgame);
        initView();
        serveWZQ=new ServeWZQ(WZQGameActivity.this,chessboard,handler);
    }

    private void initView() {
        handler=new Handler();
        setWhitePaint();
        chessboard=(Chessboard) findViewById(R.id.chessboard);

    }



    private void setWhitePaint() {
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);
        whitePaint.setStyle(Paint.Style.FILL);
        // 文本水平居左对齐
        whitePaint.setTextAlign(Paint.Align.LEFT);
        whitePaint.setStrokeJoin(Paint.Join.ROUND);
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        serveWZQ.outGame();
        super.onPause();
    }
}