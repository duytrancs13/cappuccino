package io.awesome.app.View.Fragment.Receipt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.util.List;
import java.util.Locale;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Receipt.ReceiptPresenterImpl;
import io.awesome.app.R;
import io.awesome.app.View.Table.TableActivity;

import static android.content.Context.MODE_PRIVATE;
import static io.awesome.app.View.MenuTabs.MenuTabsActivity.listOrdered;
import static io.awesome.app.View.Main.MainActivity.receiptId;

public class FragmentReceipt extends Fragment implements FragmentReceiptView {

    private LinearLayout llReceipt;
    private ReceiptPresenterImpl receiptPresenter;

    public static final String MyPREFERENCES = "capuccino" ;
    private String token;

    private SharedPreferences prefs;

    private Button btnReceipt;

    private TextView tvTotalMoney;

    private Intent intent ;

    private int totalMoney=0;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        llReceipt = (LinearLayout) view.findViewById(R.id.llReceipt);

        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = prefs.getString("token", null);

        intent = new Intent(getActivity(), TableActivity.class);


        receiptPresenter = new ReceiptPresenterImpl(this.getContext(), this);

        if(!listOrdered.equals(null)){
            receiptPresenter.getMenuReceipt(token);
        }else{
            return view;
        }


        tvTotalMoney = (TextView) view.findViewById(R.id.tvTotalMoney);

        tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.GERMAN).format(totalMoney)+" đ");

        totalMoney = 0;

        btnReceipt = (Button) view.findViewById(R.id.btnReceipt);
        btnReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            receiptPresenter.updateReceipt(token);
            }
        });

        return view;
    }

    @Override
    public void loadMenuReceipt(List<Ordered> listOrdered) {

        for (Ordered itemOrdered: listOrdered) {
            View v = getView(itemOrdered);
            llReceipt.addView(v);
        }
    }

    @Override
    public void gotoTable() {
        startActivity(intent);
    }

    public void toast(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
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

        Picasso.with(getContext()).load(ordered.getUrlImage()).into(viewHolder.imageReceipt);


        viewHolder.tvNameReceipt.setText(ordered.getName());
//        viewHolder.tvNameReceipt.setTypeface(mFont);

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(ordered.getPrice());

        viewHolder.tvPriceReceipt.setText(""+price+" đ");

        viewHolder.btnQualityReceipt.setText(""+ordered.getQuantity());

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        totalMoney+= caculMoney;

        viewHolder.tvMoneyReceipt.setText(""+money+" đ");

        viewHolder.btnPlusReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qualityCurrent = Integer.parseInt(viewHolder.btnQualityReceipt.getText().toString());
                receiptPresenter.addReceipt(receiptId, token, "menuItemId", ordered.getItemId());
                viewHolder.btnQualityReceipt.setText((qualityCurrent + 1) + "");
            }
        });
        return view;
    }

}
