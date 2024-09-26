package cn.itcast.gobang.Util;

public class PiecesInformarion {
    private float x,y;
    private int color;
    public PiecesInformarion(int color,float x, float y){
        this.color=color;
        this.x=x;
        this.y=y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getColor(){
        return color;
    }
}
