package cn.itcast.gobang.FunctionActivityFile;

import android.app.Activity;

import java.util.List;

import cn.itcast.gobang.AdapterUtil.HaoYouAdapter;
import cn.itcast.gobang.Util.Client;

public class HaoYouListUpdate {
    List<Client> haoyouList;
    Activity activity;

    public HaoYouListUpdate(List<Client> haoyouList, Activity activity) {
        this.activity = activity;
        this.haoyouList = haoyouList;
    }

    public void DeleteAll() {
        for (int i = haoyouList.size() - 1; i >= 0; i--) {
            haoyouList.remove(haoyouList.get(i));
        }
    }

    public List<Client> updateHaoYouList(String[] strings) {
        for (int i = 1; i < strings.length; i = i + 5) {
            haoyouList.add(new Client(strings[i], strings[i + 1], strings[i + 2], Integer.parseInt(strings[i + 3]), Boolean.parseBoolean(strings[i + 4])));
        }
        return haoyouList;
    }

    public void updateUIHaoYouList(HaoYouAdapter haoYouAdapter){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run () {
                haoYouAdapter.notifyDataSetChanged();
            }
        });
}
}
