package cn.itcast.gobang.Util;

public class SiXin {
    private String FromOrTo;
    private String name;
    private String content;
    private String item;

    public SiXin(String FromOrTo,String name,String content,String item){
        this.FromOrTo=FromOrTo;
        this.name=name;
        this.content=content;
        this.item=item;
    }

    public String getFromOrTo(){
        return FromOrTo;
    }

    public String getName(){
        return name;
    }

    public String getContent(){
        return content;
    }

    public String getTime(){
        return item;
    }
}
