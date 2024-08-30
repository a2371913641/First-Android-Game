package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.Client;
import cn.itcast.gobang.Util.GongGongZiYuan;

public class YaoQinAdapter extends ShowClientListAdapter {
    View xuanzeView;
    GongGongZiYuan gongGongZiYuan = new GongGongZiYuan();
    int xuanze_layout=R.layout.layout_yaoqing_xuanze;

    public YaoQinAdapter(Context context, List<Client> clients) {
        super(context, clients);
    }


    @Override
    public View showItem(int position, View converView) {
        if (expandMap.get(position) == null) {
            expandMap.put(position, false);
        }
        xuanzeView = View.inflate(context,xuanze_layout, null);
        if (converView == null) {
            View view = View.inflate(context, R.layout.layout_haoyou_item, null);
            TextView name = (TextView) view.findViewById(R.id.four_haoyou_name);
            name.setText(clientList.get(position).getName());
            LinearLayout layout = view.findViewById(R.id.four_haoyou_xuanze);
            if (isExpand(position)) {
                layout.addView(setXuanze(xuanzeView, position));
            }
            converView = view;
        } else {
            TextView name = (TextView) converView.findViewById(R.id.four_haoyou_name);
            name.setText(clientList.get(position).getName());
            LinearLayout layout = converView.findViewById(R.id.four_haoyou_xuanze);
            if (isExpand(position) && layout.getChildCount() == 0) {
                layout.addView(setXuanze(xuanzeView, position));
            } else if (!isExpand(position) && layout.getChildCount() != 0) {
                layout.removeAllViews();
            }
        }
        return converView;
    }

    @Override
    public View setXuanze(View xuanzeView, int position) {

        Button siLiao = xuanzeView.findViewById(R.id.yaoqing_xuanze_siliao);
        Button siXin = xuanzeView.findViewById(R.id.yaoqing_xuanze_sixin);
        Button yaoQin = xuanzeView.findViewById(R.id.yaoqing_xuanze_yaoqing);

        siLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiLiao:/n" + clientList.get(position).getZhanghao() + "_");
            }
        });

        yaoQin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientYaoQin:/n" + clientList.get(position).getZhanghao() + "_");
            }
        });

        siXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongGongZiYuan.sendMsg("ClientSiXin:/n" + clientList.get(position).getZhanghao() + "_");
            }
        });


        return xuanzeView;
    }

}
