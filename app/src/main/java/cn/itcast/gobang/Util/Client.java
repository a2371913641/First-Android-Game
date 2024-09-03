package cn.itcast.gobang.Util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.gobang.R;

public class Client {
    String name,zhanghao,xinbie;
    int image,xinbieImage;
    Boolean onLine;
    List<Client> haoyouList=new ArrayList<>();
    public Client(String name,String zhanghao,String xinbie,int image,Boolean onLine){
        this.name=name;
        this.zhanghao=zhanghao;
        this.xinbie=xinbie;
        this.image=image;
        this.onLine=onLine;
        setXinbieImage();
        Log.e("Client","xinbie="+xinbie);
    }

    public void setXinbieImage(){
        if(xinbie.equals("boy")){
            this.xinbieImage= R.mipmap.nan;
        }else if(xinbie.equals("girl")){
            this.xinbieImage=R.mipmap.nv;
        }
    }

    public int getImage(){
        return image;
    }

    public String getZhanghao(){
        return zhanghao;
    }

    public String getXinbie(){
        return xinbie;
    }

    public String getName(){
        return name;
    }

    public int getXinbieImage(){
        return xinbieImage;
    }

    public Boolean getOnLine(){
        return onLine;
    }
}
