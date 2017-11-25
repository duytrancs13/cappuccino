package info.devexchanges.navvp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.devexchanges.navvp.Model.Ordered;
import info.devexchanges.navvp.R;

public class FragmentReceipt extends Fragment {
    private List<Ordered> listOrdered;

    private LinearLayout llReceipt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

        listOrdered = new ArrayList<Ordered>();
        listOrdered.add(new Ordered("1","Ca phe sua",29000,"https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s",1));
        listOrdered.add(new Ordered("2","Ca phe sua",29000,"https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s",2));

        llReceipt = (LinearLayout) view.findViewById(R.id.llReceipt);
        for (Ordered itemOrdered: listOrdered) {
            View v = getView(itemOrdered);
            llReceipt.addView(v);
        }



        return view;
    }

    private class ViewHolder{
        ImageView imageReceipt;
        TextView tvNameReceipt;
        TextView tvPriceReceipt;
        Button btnQualityReceipt;
        TextView tvMoneyReceipt;
        Button btnMinusReceipt;
        Button btnPlusReceipt;
    }

    public View getView(final Ordered ordered) {

        final ViewHolder viewHolder;

        View view = null ;



        viewHolder = new ViewHolder();



        view = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_receipt, null);

        viewHolder.imageReceipt = (ImageView) view.findViewById(R.id.imageReceipt);

        viewHolder.tvNameReceipt = (TextView) view.findViewById(R.id.tvNameReceipt);

        viewHolder.tvPriceReceipt = (TextView) view.findViewById(R.id.tvPriceReceipt);

        viewHolder.btnQualityReceipt = (Button) view.findViewById(R.id.btnQualityReceipt);

        viewHolder.tvMoneyReceipt = (TextView) view.findViewById(R.id.tvMoneyReceipt);

        viewHolder.btnMinusReceipt = (Button) view.findViewById(R.id.btnMinusReceipt);

        viewHolder.btnPlusReceipt = (Button) view.findViewById(R.id.btnPlusReceipt);


        view.setTag(viewHolder);

        /*Typeface mFont = Typeface.createFromAsset(this.getContext().getAssets(),"Roboto-Bold.ttf");
*/

        Picasso.with(getContext()).load(ordered.getSrcImage()).into(viewHolder.imageReceipt);


        viewHolder.tvNameReceipt.setText(ordered.getName());
//        viewHolder.tvNameReceipt.setTypeface(mFont);

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(ordered.getPrice());

        viewHolder.tvPriceReceipt.setText(""+price+" đ");

        viewHolder.btnQualityReceipt.setText(""+ordered.getQuantity());

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        viewHolder.tvMoneyReceipt.setText(""+money+" đ");






        return view;
    }
    public void toast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
