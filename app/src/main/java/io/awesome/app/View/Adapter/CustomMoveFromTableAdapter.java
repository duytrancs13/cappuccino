package io.awesome.app.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;

/**
 * Created by sung on 20/03/2018.
 */

public class CustomMoveFromTableAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Ordered> listOrdered;

    public CustomMoveFromTableAdapter(Context context, List<Ordered> listOrdered){
        this.context = context;
        this.listOrdered = listOrdered;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listOrdered.size();
    }

    @Override
    public Object getItem(int position) {
        return listOrdered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.layout_listview_movefromtable,null);

            viewHolder = new CustomMoveFromTableAdapter.ViewHolder();

            viewHolder.imageMoveFromTable = (ImageView) convertView.findViewById(R.id.imageMoveFromTable);

            viewHolder.tvNameMoveFromTable = (TextView) convertView.findViewById(R.id.tvNameMoveFromTable);

            viewHolder.quatityMoveFromTable = (TextView) convertView.findViewById(R.id.quatityMoveFromTable);

            viewHolder.tvMoneyMoveFromTable = (TextView) convertView.findViewById(R.id.tvMoneyMoveFromTable);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CustomMoveFromTableAdapter.ViewHolder) convertView.getTag();
        }


        Ordered ordered = this.listOrdered.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");
        Picasso.with(context).load(ordered.getUrlImage()).into(viewHolder.imageMoveFromTable);

        viewHolder.tvNameMoveFromTable.setText(ordered.getName());
        viewHolder.tvNameMoveFromTable.setTypeface(mFont);

        viewHolder.quatityMoveFromTable.setText(""+ordered.getQuantity());

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        viewHolder.tvMoneyMoveFromTable.setText(""+money+" đ");

        return convertView;
    }

    static class ViewHolder extends FragmentActivity {
        ImageView imageMoveFromTable;
        TextView tvNameMoveFromTable;
        TextView quatityMoveFromTable;
        TextView tvMoneyMoveFromTable;
    }
}
