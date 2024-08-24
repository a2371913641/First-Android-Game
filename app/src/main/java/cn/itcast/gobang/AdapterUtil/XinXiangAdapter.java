package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.GongGongZiYuan;
import cn.itcast.gobang.Util.IOUtil;
import cn.itcast.gobang.Util.SiXin;

public class XinXiangAdapter extends BaseAdapter {
    Context context;
    List<SiXin> siXins;
    SetHuiFu setHuiFu;
    SetXinXiangs setXinXiangs;
    ListViewSelection listViewSelection;

    public interface ListViewSelection{
        void setSelection();
    }

    public interface SetHuiFu{
        void setHuiFuButton(int postion);
    }

    public interface SetXinXiangs{
        void setXinXiangFile();
    }


    public XinXiangAdapter(Context context, List<SiXin> siXins){
        this.context=context;
        this.siXins=siXins;
    }

    public void setListViewSelection(ListViewSelection listViewSelection) {
        this.listViewSelection = listViewSelection;
    }

    public void setSetHuiFu(SetHuiFu setHuiFu){
        this.setHuiFu=setHuiFu;
    }

    public void setSetXinxiang(SetXinXiangs setXinXiangs){
        this.setXinXiangs=setXinXiangs;
    }

    @Override
    public int getCount() {
        return siXins.size();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }



    private View setButton(View convertView,int position){
        TextView huifu=(TextView) convertView.findViewById(R.id.four_xinxiang_item_huifu);
        TextView shanchu=(TextView) convertView.findViewById(R.id.four_xinxiang_item_shanchu);
        huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHuiFu.setHuiFuButton(position);
            }
        });

        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siXins.remove(position);
                setSetXinxiang(setXinXiangs);
                XinXiangAdapter.this.notifyDataSetChanged();
                setListViewSelection(listViewSelection);

            }
        });
        return convertView;
    }

    private View setConverView(int position,View convertView){
        if(convertView==null){
            convertView=setButton(View.inflate(context, R.layout.layout_four_xinxiang_item,null),position);
        }
        convertView=setButton(View.inflate(context, R.layout.layout_four_xinxiang_item,null),position);
        TextView ToOrFrom=convertView.findViewById(R.id.four_xinxiang_item_ToOrFrom);
        TextView name=convertView.findViewById(R.id.four_xinxiang_item_name);
        TextView content=convertView.findViewById(R.id.four_xinxiang_item_content);
        TextView time=convertView.findViewById(R.id.four_xinxiang_item_content_time);
        ToOrFrom.setText(siXins.get(position).getFromOrTo()+":");
        name.setText(siXins.get(position).getName());
        content.setText(siXins.get(position).getContent());
        time.setText(siXins.get(position).getTime());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setConverView(position,convertView);
    }


}
