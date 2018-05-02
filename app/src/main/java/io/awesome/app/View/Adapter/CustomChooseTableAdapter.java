package io.awesome.app.View.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.awesome.app.Model.Table;
import io.awesome.app.R;

import static io.awesome.app.View.Main.MainActivity.receiptId;

/**
 * Created by sung on 27/04/2018.
 */

public class CustomChooseTableAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Table> tableList;

    public CustomChooseTableAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.tableList = tableList;
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int i) {
        return tableList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_listview_choosetable,null);
            viewHolder = new ViewHolder();
            viewHolder.tvChooseTable = (TextView) convertView.findViewById(R.id.tvChooseTable);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Table table = tableList.get(position);
        if(table.getReceiptId() != receiptId){
            viewHolder.tvChooseTable.setText(table.getName());
        }

        return convertView;
    }

    static class ViewHolder extends FragmentActivity {

        TextView tvChooseTable;
    }
}
