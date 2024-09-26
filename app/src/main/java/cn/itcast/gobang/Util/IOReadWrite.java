package cn.itcast.gobang.Util;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class IOReadWrite implements ReadWrite {

    public static final String ACOUNT_LIST = "账号名单";
    public static final String CHECK_BOX_ZHUANGTAI="勾选框状态";
    String Edit_zhanghaoMima="baocunzhanghao.txt";
    Context context;

    public IOReadWrite(Context context){
        this.context=context;
    }

    IOUtil ioUtil=new IOUtil();

    @Override
    public void WriteMiMa(String zhanghao,String mima) {
        ioUtil.outputFile(new File(context.getFilesDir(), zhanghao+"密码").getAbsolutePath(), mima,false);
        Log.e("IOReadWrite","保存密码成功！"+mima);
        Log.e("IOReadWrite","查看密码="+ioUtil.inputFile(new File(context.getFilesDir(),zhanghao+"密码").getAbsolutePath()));
    }


    @Override
    public void WriteZhangHao(String zhanghao) {
        ioUtil.outputFile(new File(context.getFilesDir(), ACOUNT_LIST).getAbsolutePath(), zhanghao+"/n",true);
    }

    @Override
    public String readMiMa(String zhanghao) {
        return ioUtil.inputFile(new File(context.getFilesDir(),zhanghao+"密码").getAbsolutePath());
    }

    @Override
    public String readAllZhangHao() {
        return ioUtil.inputFile(new File(context.getFilesDir(), ACOUNT_LIST).getAbsolutePath());
    }

    public void writeGouXuanKuangZhuangTai(String s){
        ioUtil.outputFile(new File(context.getFilesDir(),CHECK_BOX_ZHUANGTAI).getAbsolutePath(),s,false);
    }

    public String  readGouXuanKuang(){
        return ioUtil.inputFile(new File(context.getFilesDir(),CHECK_BOX_ZHUANGTAI).getAbsolutePath());
    }

    //重新保存账号
    public void writeResetBaoCunAccount(String zhanghaos){
        ioUtil.outputFile(new File(context.getFilesDir(), ACOUNT_LIST).getAbsolutePath(),zhanghaos,false);
    }

    public void deleteMiMa(String zhanghao){
        ioUtil.deleteFile(new File(context.getFilesDir(),zhanghao+"密码").getAbsolutePath());
    }

}
