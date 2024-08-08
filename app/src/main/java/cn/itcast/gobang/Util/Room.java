package cn.itcast.gobang.Util;

import java.util.ArrayList;
import java.util.List;

public class Room {
    String roomName,roomType,roomAdmin;
    List<Client> roomClientList=new ArrayList<>();
    int roomHaoMa;
    public Room(String name,String type,String admin,int roomHaoMa){
        this.roomAdmin=admin;
        this.roomName=name;
        this.roomType=type;
        this.roomHaoMa=roomHaoMa;
    }

    public String getRoomName(){
        return roomName;
    }
    public String getRoomType(){
        return roomType;
    }
    public int getRoomHaoMa(){
        return roomHaoMa;
    }
    public String getRoomAdmin(){
        return roomAdmin;
    }

}
