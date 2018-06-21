package io.awesome.app.View.Fragment.Receipt;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
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
import android.os.AsyncTask;
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
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Receipt.ReceiptPresenterImpl;
import io.awesome.app.R;
import io.awesome.app.View.Table.TableActivity;

import static android.content.Context.MODE_PRIVATE;
import static io.awesome.app.View.Main.MainActivity.bluetoothSocket;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.checkConfirmChangedOrdered;
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

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;

    private ProgressDialog dialog ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        llReceipt = (LinearLayout) view.findViewById(R.id.llReceipt);

        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = prefs.getString("token", null);

        intent = new Intent(getActivity(), TableActivity.class);


        receiptPresenter = new ReceiptPresenterImpl(this.getContext(), this, token);

        dialog = new ProgressDialog(view.getContext(), R.style.AppTheme_Dark_Dialog);

        if(listOrdered.size() != 0){
            receiptPresenter.getMenuReceipt(token);
        }

        quatityMenuReceipt = (Button) view.findViewById(R.id.quatityMenuReceipt);
        quatityMenuReceipt.setText(tatalQuatityReceipt+"");


        tvTotalMoney = (TextView) view.findViewById(R.id.tvTotalMoney);

        tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.GERMAN).format(totalMoney)+" đ");

        totalMoney = 0;
        tatalQuatityReceipt=0;


        btnPrintReceipt = (Button) view.findViewById(R.id.btnPrintReceipt);
        btnPrintReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listOrdered.size()!=0 && checkConfirmChangedOrdered){
                    if(!receiptPresenter.isBluetoothEnabled()){
                        toast("Vui lòng mở bluetooth để kết nối!!!");
                        bluetoothSocket = null;
                    }else{
                        if(bluetoothSocket==null){
                            receiptPresenter.findBluetoothDevice();
                            receiptPresenter.openBluetoothPrinter();
                        }
                        confirmConnectBluetooth();
                    }
                }
                else if(!checkConfirmChangedOrdered){
                    toast("Vui lòng xác nhận món");
                }else{
                    toast("Vui lòng đặt món");
                }
            }
        });

        btnReceipt = (Button) view.findViewById(R.id.btnReceipt);
        btnReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listOrdered.size()!=0 && checkConfirmChangedOrdered){
                    confirmUpdateReceipt();
                }
                else if(!checkConfirmChangedOrdered){
                    toast("Vui lòng xác nhận món");
                }else{
                    toast("Vui lòng đặt món");
                }
            }
        });


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
    public void showProgress() {
        new Progress().execute();
    }

    @Override
    public void alertMessage(String titleError, String textError, int responseCode) {
        if(responseCode == 500) {
            Alerter.create(getActivity())
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
            dialog.dismiss();
        }else{
            Alerter.create(getActivity())
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show();
            dialog.dismiss();
            checkConfirmChangedOrdered = true;
            getActivity().onBackPressed();
        }
    }

    private class Progress extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang xử lý...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
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

    private void confirmConnectBluetooth(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn kết nối bluetooth không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    showProgress();
                    receiptPresenter.printReceipt();
                    receiptPresenter.printData();
                    dialog.dismiss();
                    Alerter.create(getActivity())
                            .setTitle("Thành công")
                            .setText("Bạn đã in hóa đơn thành công")
                            .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }


    private void confirmUpdateReceipt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn thanh toán hóa đơn này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            showProgress();
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
