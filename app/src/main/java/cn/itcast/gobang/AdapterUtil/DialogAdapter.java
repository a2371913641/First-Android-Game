package cn.itcast.gobang.AdapterUtil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.List;

import cn.itcast.gobang.R;
import cn.itcast.gobang.Util.ClientAccount;
import cn.itcast.gobang.Util.IOUtil;

public class DialogAdapter extends BaseAdapter {

    List<ClientAccount> accounts;

    Context context;

    int resource;

    EditText zhanghao,mima;

    public LayoutInflater inflater;


    public DialogAdapter(@NonNull Context context, int resource, @NonNull List objects, EditText zhanghao,
                         EditText mima) {
        this.accounts=objects;
        this.context=context;
        this.resource=resource;
        this.inflater=LayoutInflater.from(context);
        this.zhanghao=zhanghao;
        this.mima=mima;
    }


    @Override
    public int getCount() {
        return accounts.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DialogAdapter.this.notifyDataSetChanged();
        View view=inflater.inflate(resource,null);
        TextView name=(TextView) view.findViewById(R.id.item_account);
        Button delete=(Button)view.findViewById(R.id.button_dialog_delete_account);
        name.setText(accounts.get(position).getZhanghao());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhanghao.setText(accounts.get(position).getZhanghao());
                mima.setText(accounts.get(position).getMima());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Adapter","MainActivity.accountList="+accounts.toString());
                IOUtil ioUtil=new IOUtil();
                ioUtil.deleteFile(new File(context.getFilesDir(),zhanghao.getText()+"密码").getAbsolutePath());
                accounts.remove(position);
                DialogAdapter.this.notifyDataSetChanged();
            }
        });
        return view;
    }
}
