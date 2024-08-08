package cn.itcast.gobang.Util;

public class ClientAccount {
    String zhanghao;
    String mima;
    int cl;//等级


    public ClientAccount(){

    }
    public ClientAccount(String zhanghao,String mima){
        this.zhanghao=zhanghao;
        this.mima=mima;
    }

    public void setZhanghao(String zhanhao){
        this.zhanghao=zhanhao;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public int getCl() {
        return cl;
    }


    public String getZhanghao() {
        return zhanghao;
    }

    public String getMima() {
        return mima;
    }

    public void setCl(int cl) {
        this.cl = cl;
    }
}
