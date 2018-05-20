package io.awesome.app.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class CustomMoveOrderedAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Ordered> listOrdered;

    public CustomMoveOrderedAdapter(Context context, List<Ordered> listOrdered){
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

            convertView = layoutInflater.inflate(R.layout.layout_listview_moveordered,null);

            viewHolder = new CustomMoveOrderedAdapter.ViewHolder();

            viewHolder.llItemMoveOrdered = (LinearLayout) convertView.findViewById(R.id.llItemMoveOrdered);

            viewHolder.imageMoveOrdered = (ImageView) convertView.findViewById(R.id.imageMoveOrdered);


            viewHolder.tvNameMoveOrdered = (TextView) convertView.findViewById(R.id.tvNameMoveOrdered);

            viewHolder.quatityMoveOrdered = (TextView) convertView.findViewById(R.id.quatityMoveOrdered);

            viewHolder.tvMoneyMoveOrdered = (TextView) convertView.findViewById(R.id.tvMoneyMoveOrdered);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CustomMoveOrderedAdapter.ViewHolder) convertView.getTag();
        }


        Ordered ordered = this.listOrdered.get(position);

        if(ordered.getQuantity() == 0){
            viewHolder.llItemMoveOrdered.setVisibility(View.GONE);
        }else{
            viewHolder.llItemMoveOrdered.setVisibility(View.VISIBLE);
        }

        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");
        Picasso.with(context).load(ordered.getUrlImage()).into(viewHolder.imageMoveOrdered);

        viewHolder.tvNameMoveOrdered.setText(ordered.getName());
        viewHolder.tvNameMoveOrdered.setTypeface(mFont);

        viewHolder.quatityMoveOrdered.setText(""+ordered.getQuantity());

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        viewHolder.tvMoneyMoveOrdered.setText(""+money+" đ");

        return convertView;
    }

    static class ViewHolder extends FragmentActivity {
        LinearLayout llItemMoveOrdered;
        ImageView imageMoveOrdered;
        TextView tvNameMoveOrdered;
        TextView quatityMoveOrdered;
        TextView tvMoneyMoveOrdered;
    }
}
