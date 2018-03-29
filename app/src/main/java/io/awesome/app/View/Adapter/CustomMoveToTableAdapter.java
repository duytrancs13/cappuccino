package io.awesome.app.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class CustomMoveToTableAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Ordered> listOrdered;

    public CustomMoveToTableAdapter(Context context, List<Ordered> listOrdered){
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

            convertView = layoutInflater.inflate(R.layout.layout_listview_movetotable,null);

            viewHolder = new CustomMoveToTableAdapter.ViewHolder();

            viewHolder.imageMoveToTable = (ImageView) convertView.findViewById(R.id.imageMoveToTable);

            viewHolder.tvNameMoveToTable = (TextView) convertView.findViewById(R.id.tvNameMoveToTable);

            /*viewHolder.btnQualityMoveToTable = (Button) convertView.findViewById(R.id.btnQualityMoveToTable);*/

            viewHolder.tvMoneyMoveToTable = (TextView) convertView.findViewById(R.id.tvMoneyMoveToTable);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CustomMoveToTableAdapter.ViewHolder) convertView.getTag();
        }


        Ordered ordered = this.listOrdered.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");
        Picasso.with(context).load(ordered.getUrlImage()).into(viewHolder.imageMoveToTable);

        viewHolder.tvNameMoveToTable.setText(ordered.getName());
        viewHolder.tvNameMoveToTable.setTypeface(mFont);

        /*viewHolder.btnQualityMoveToTable.setText(""+ordered.getQuantity());*/

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        viewHolder.tvMoneyMoveToTable.setText(""+money+" đ");

        return convertView;
    }

    static class ViewHolder extends FragmentActivity {
        ImageView imageMoveToTable;
        TextView tvNameMoveToTable;
        /*Button btnQualityMoveToTable;*/
        TextView tvMoneyMoveToTable;
    }
}
