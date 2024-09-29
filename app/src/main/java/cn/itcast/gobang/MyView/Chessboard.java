package cn.itcast.gobang.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

import cn.itcast.gobang.Util.ChessBoardListener;
import cn.itcast.gobang.Util.PiecesInformarion;
import cn.itcast.gobang.WZQGameActivity;

public class Chessboard extends View {
    Context context;
    String TAG="Chessboard";
    Paint blackPaint,whitePaint;
    Canvas canvas;
    int i = 0;
    boolean Apply=true;
    float[] drawChessBoard=new float[88];
    int min,max;
    HashMap<Integer, PiecesInformarion> chessPieces=new HashMap<>();
    ChessBoardListener chessBoardListener;
    //棋盘内的棋子布局
   public int[][] chessBoardPieces=new int[11][11];

   public void setChessBoardListener(ChessBoardListener chessBoardListener){
       this.chessBoardListener=chessBoardListener;
   }


    public Chessboard(Context context) {
        super(context);
        this.context=context;
        init();
        setChessBoard();
    }

    public Chessboard(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context=context;
        init();
        setChessBoard();
    }

    private void init(){
        blackPaint=new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(true);
        blackPaint.setStyle(Paint.Style.FILL);
        blackPaint.setStrokeJoin(Paint.Join.ROUND);
        whitePaint=new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setStrokeJoin(Paint.Join.ROUND);
    }



    //-1--null
    //0--白
    //1--黑
    private void setChessBoard(){
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                chessBoardPieces[i][j]=-1;
            }
        }
    }


    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;
        super.onDraw(canvas);
        float h=getHeight();
        float w=getWidth();
        min=(int)Math.min(h,w);
        max=(int)Math.max(h,w);
        Log.e(TAG,"min="+min+",max="+max);
        onDrawChessBoard();
        canvas.drawLines(drawChessBoard,blackPaint);




        for(int j=0;j<chessPieces.size();j++){
            if ((chessPieces.get(j)).getColor()==1) {
                canvas.drawCircle(chessPieces.get(j).getX(),chessPieces.get(j).getY(), 30, blackPaint);
                Log.e(TAG,"blackPaint");
            } else {
                canvas.drawCircle(chessPieces.get(j).getX(),chessPieces.get(j).getY(),30, whitePaint);
                Log.e(TAG," whitePaint");
            }
        }


    }

    private void onDrawChessBoard(){

        //横线
        for(int i=0;i<44;i=i+4){
            drawChessBoard[i]=(max-min)/2;
            drawChessBoard[i+1]=0+min/10*(i/4);
            drawChessBoard[i+2]=(max-min)/2+min;
            drawChessBoard[i+3]=0+min/10*(i/4);
        }

        //竖线
        for(int i=44;i<88;i=i+4){
            drawChessBoard[i]=(max-min)/2+(i-44)/4*min/10;
            drawChessBoard[i+1]=0;
            drawChessBoard[i+2]=(max-min)/2+(i-44)/4*min/10;
            drawChessBoard[i+3]=min;
        }
    }


    //默认ture黑子，false白子
    private int checkPieces(int count) {
        if (count % 2 == 0) {
            return 1;
        } else {
            return 0;
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        invalidate();

        //触屏实时位置

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                        if (event.getX() >= (max - min) / 2 && (event.getX() <= (max - min) / 2 + min)) {
                            Log.e(TAG, (max - min) / 2 + "<=x<=" + ((max - min) / 2 + min));
                            if(Apply) {
                                Log.e(TAG,"Apply="+Apply);
                                resetChessPieces((int) event.getX(), (int) event.getY(), checkPieces(i));
                            }else{
                                Toast.makeText(context,"该对方下棋",Toast.LENGTH_SHORT).show();
                            }
                        }
            }

        return super.onTouchEvent(event);
    }


    private void resetChessPieces(int x,int y,int piecesColor){
        Log.e(TAG,"resetChessPieces");
        int resetX,resetY;
        if(((x-(max-min)/2)%(min/10))>=(min/20)){
            resetX= (((x-(max-min)/2)/(min/10))*(min/10)+(max-min)/2+(min/10));
        }else{
            resetX= (((x-(max-min)/2)/(min/10))*(min/10)+(max-min)/2);
        }

        if(y%(min/10)>=(min/20)){
            resetY=((y/(min/10))*(min/10)+(min/10));
        }else{
            resetY=((y/(min/10))*(min/10));
        }


        if( chessBoardPieces[((resetX-(max-min)/2)/(min/10))][(resetY/(min/10))]==-1) {

            if (piecesColor==1) {
                chessBoardPieces[ ((resetX - (max-min)/2) / (min/10))][ (resetY / (min/10))] = 1;
                chessPieces.put(i, new PiecesInformarion(1,resetX, resetY));
                chessBoardListener.onReceive("1/n"+((resetX - (max-min)/2) / (min/10))+"/n"+(resetY / (min/10)));
                Log.e(TAG,"chessBoardPieces["+ ((resetX - (max-min)/2) / (min/10))+"]["+ (resetY / (min/10))+"]="+1);
            } else {
                chessBoardPieces[ ((resetX - (max-min)/2) / (min/10))][ (resetY / (min/10))] = 0;
                chessPieces.put(i, new PiecesInformarion(0,resetX, resetY));
                chessBoardListener.onReceive("0/n"+((resetX - (max-min)/2) / (min/10))+"/n"+(resetY / (min/10)));
                Log.e(TAG,"chessBoardPieces["+ ((resetX - (max-min)/2) / (min/10))+"]["+ (resetY / (min/10))+"]="+0);
            }
            Log.e(TAG,"i="+i);
            i++;
            Apply=false;

        }else{
            Toast.makeText(context,"不能在重复的位置下棋",Toast.LENGTH_SHORT).show();
        }
    }

    public void drawRivalChessPiesces(int Color,int X,int Y){
        Log.e(TAG,"drawRivalChessPiesces");
        Log.e(TAG,Color+"/n"+X+"/n"+Y);
        int resetX,resetY;
        resetX=(max-min)/2+X*(min)/10;
        resetY=Y*min/10;
        if(Color==1){
            chessBoardPieces[X][Y] = 1;
            chessPieces.put(i, new PiecesInformarion(1,resetX, resetY));
        }else{
            chessBoardPieces[X][Y] = 0;
            chessPieces.put(i, new PiecesInformarion(0,resetX, resetY));
        }
        Log.e(TAG,"i="+i);
        i++;
        Apply=true;

        Log.e(TAG,"drawRivalChessPiesces Apply="+Apply);
        invalidate();

    }

}
