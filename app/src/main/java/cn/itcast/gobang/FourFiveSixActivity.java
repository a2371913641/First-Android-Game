package cn.itcast.gobang;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.itcast.gobang.Util.GongGongZiYuan;

public class FourFiveSixActivity extends AppCompatActivity{
    public GongGongZiYuan gongGongZiYuan=new GongGongZiYuan();

    public void setSiXinDialog(String adverseName){
        View view=View.inflate(this,R.layout.layout_sixin_dialogview,null);
        AlertDialog.Builder sixinDialogBuilder=new AlertDialog.Builder(this);
        sixinDialogBuilder.setView(view);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sixinDialogBuilder.create();
                AlertDialog alertDialog=sixinDialogBuilder.show();
                gongGongZiYuan.setSixinDialogView(FourFiveSixActivity.this,view,alertDialog,adverseName);
            }
        });
    }


}
