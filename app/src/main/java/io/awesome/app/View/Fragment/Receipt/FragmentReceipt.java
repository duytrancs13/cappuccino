package io.awesome.app.View.Fragment.Receipt;

import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Receipt.ReceiptPresenterImpl;
import io.awesome.app.R;
import io.awesome.app.View.Table.TableActivity;

import static android.content.Context.MODE_PRIVATE;
import static io.awesome.app.View.Main.MainActivity.bluetoothSocket;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.listOrdered;

public class FragmentReceipt extends Fragment implements FragmentReceiptView {

    private LinearLayout llReceipt;
    private ReceiptPresenterImpl receiptPresenter;

    public static final String MyPREFERENCES = "capuccino" ;
    private String token;

    private SharedPreferences prefs;

    private Button btnPrintReceipt,btnReceipt;


    private Button quatityMenuReceipt;

    private TextView tvTotalMoney;

    private Intent intent ;

    private int totalMoney=0;

    private int tatalQuatityReceipt=0;

    private boolean checkClickPrintPay = false;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        llReceipt = (LinearLayout) view.findViewById(R.id.llReceipt);

        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = prefs.getString("token", null);

        intent = new Intent(getActivity(), TableActivity.class);


        receiptPresenter = new ReceiptPresenterImpl(this.getContext(), this, token);

        if(listOrdered.size() != 0){
            checkClickPrintPay = true;
            receiptPresenter.getMenuReceipt(token);
        }else{
            return view;
        }

        quatityMenuReceipt = (Button) view.findViewById(R.id.quatityMenuReceipt);
        /*quatityMenuReceipt.setText(listOrdered.size()+"");*/
        quatityMenuReceipt.setText(tatalQuatityReceipt+"");


        tvTotalMoney = (TextView) view.findViewById(R.id.tvTotalMoney);

        tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.GERMAN).format(totalMoney)+" đ");

        totalMoney = 0;
        tatalQuatityReceipt=0;


        if(checkClickPrintPay){
            btnPrintReceipt = (Button) view.findViewById(R.id.btnPrintReceipt);
            btnPrintReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*try {
                        if(bluetoothSocket==null){
                            toast("Vui long ket noi bluetooth");

                        }else{
                            receiptPresenter.printReceipt();
                            receiptPresenter.printData();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/


                }
            });

            btnReceipt = (Button) view.findViewById(R.id.btnReceipt);
            btnReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmUpdateReceipt();

                }
            });
        }


        return view;
    }

    @Override
    public void loadMenuReceipt(List<Ordered> listOrdered) {

        for (Ordered itemOrdered: listOrdered) {
            if(itemOrdered.getQuantity() == 0){
                continue;
            }
            View v = getView(itemOrdered);
            llReceipt.addView(v);
        }
    }



    @Override
    public void gotoTable() {
//        startActivity(intent);
        getActivity().onBackPressed();
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


        view.setTag(viewHolder);

        /*Typeface mFont = Typeface.createFromAsset(this.getContext().getAssets(),"Roboto-Bold.ttf");
*/

        Picasso.with(getContext()).load(ordered.getUrlImage()).into(viewHolder.imageReceipt);


        viewHolder.tvNameReceipt.setText(ordered.getName());
//        viewHolder.tvNameReceipt.setTypeface(mFont);

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(ordered.getPrice());

        viewHolder.tvPriceReceipt.setText(""+price+" đ");

        viewHolder.btnQualityReceipt.setText(""+ordered.getQuantity());

        /*Tinh tong so mon da ordered*/

        tatalQuatityReceipt+=ordered.getQuantity();

        /*Tinh tong tien*/

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        totalMoney+= caculMoney;

        viewHolder.tvMoneyReceipt.setText(""+money+" đ");

        return view;
    }

    private void confirmUpdateReceipt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn thanh toán hóa đơn này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            receiptPresenter.updateReceipt();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}
