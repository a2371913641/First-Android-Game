package cn.itcast.gobang.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BlackChessPiecesView extends View {
    String GAT="BlackChessPiecesView";
    private Paint paint;
    private float lastX,lastY;

    public BlackChessPiecesView(Context context) {
        super(context);
        Log.e(GAT,"BlackChessPiecesView(Context context)");
        init();
    }

    public BlackChessPiecesView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public BlackChessPiecesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    }

    public BlackChessPiecesView(Context context, AttributeSet attrs,int defStyleAttr, int defStyleRes) {
        super(context,attrs,defStyleAttr,defStyleRes);
        init();
    }


    //初始化画笔
    private void init(){
        paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getX()/2,getY()/2,100,paint);
    }

    /**
     * Call this view's OnClickListener, if it is defined.  Performs all normal
     * actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     *
     * @return True there was an assigned OnClickListener that was called, false
     * otherwise is returned.
     */
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                float dx=event.getX()-lastX;
                float dy=event.getY()-lastY;
                setX(getX()+dx);
                setY(getY()+dy);
                lastX=event.getX();
                lastY=event.getY();
                break;

        }
        return super.onTouchEvent(event);

    }
}
