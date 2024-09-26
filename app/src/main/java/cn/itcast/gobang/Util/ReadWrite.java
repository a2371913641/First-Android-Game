package cn.itcast.gobang.Util;

public interface ReadWrite {

    void WriteMiMa(String zhanghao,String mima);
    void WriteZhangHao(String zhanghao);
    String readMiMa(String zhanghao);
    String readAllZhangHao();

}
