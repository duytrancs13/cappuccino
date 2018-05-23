package io.awesome.app.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;
import io.awesome.app.R;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.lstChooseTable;

/**
 * Created by sung on 27/04/2018.
 */

public class CustomChooseTableMoveAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Table> tableList;

    public CustomChooseTableMoveAdapter(Context context, List<Table> tableList) {
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_listview_choosetablemove,null);
            viewHolder = new ViewHolder();
            viewHolder.llItemChooseTableMove = (LinearLayout) convertView.findViewById(R.id.llItemChooseTableMove);
            viewHolder.tvChooseTableMove = (TextView) convertView.findViewById(R.id.tvChooseTableMove);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Table table = this.tableList.get(position);
        if(table.getReceiptId().equals(receiptId)){
            viewHolder.llItemChooseTableMove.setVisibility(View.GONE);
        }else{
            if(lstChooseTable.size() != 0) {
                if (checkListChooseTable(table)) {
                    viewHolder.llItemChooseTableMove.setVisibility(View.GONE);
                }else{
                    viewHolder.llItemChooseTableMove.setVisibility(View.VISIBLE);
                }
            }else{
                viewHolder.llItemChooseTableMove.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.tvChooseTableMove.setText(table.getName());
        return convertView;
    }

    private boolean checkListChooseTable(Table table){
        for (final Map.Entry<String, List<Ordered>> item: lstChooseTable.entrySet()){
            if(item.getKey().equals(table.getId())){
                return true;
            }
        }
        return false;
    }

    static class ViewHolder extends FragmentActivity {
        LinearLayout llItemChooseTableMove;
        TextView tvChooseTableMove;
    }
}
