package cn.itcast.gobang.Util;

import java.util.ArrayList;
import java.util.List;

public class Client {
    String name,zhanghao,xinbie;
    int image;
    Boolean onLine;
    List<Client> haoyouList=new ArrayList<>();
    public Client(String name,String zhanghao,String xinbie,int image){
        this.name=name;
        this.zhanghao=zhanghao;
        this.xinbie=xinbie;
        this.image=image;
//        this.onLine=onLine;
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

    public int getImage(){
        return image;
    }

    public Boolean getOnLine(){
        return onLine;
    }
}
