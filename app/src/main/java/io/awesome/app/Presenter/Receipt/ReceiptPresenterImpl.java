package io.awesome.app.Presenter.Receipt;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Receipt;
import io.awesome.app.View.Fragment.Receipt.FragmentReceipt;

import static io.awesome.app.View.Main.MainActivity.bluetoothSocket;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.listOrdered;

/**
 * Created by sung on 26/11/2017.
 */

public class ReceiptPresenterImpl implements ReceiptPresenter {
    private Receipt receipt;
    private Context context;
    private FragmentReceipt fragmentReceipt;

    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean stopWorker;
    private int readBufferPosition;
    private byte[] readBuffer;
    private Thread thread;
    private String BILL = "";

    public ReceiptPresenterImpl(Context context, FragmentReceipt fragmentReceipt) {
        this.context = context;
        this.fragmentReceipt = fragmentReceipt;
    }

    @Override
    public void getMenuReceipt(final String token) {
        fragmentReceipt.loadMenuReceipt(listOrdered);
    }

    @Override
    public void updateReceipt(final String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url="https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId;
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAA", "Update pay receipt success: " +response);
                fragmentReceipt.gotoTable();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("isPay","true");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void addReceipt(String receiptId, String token, final String key, final String value) {
        String url = "https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptId+"?token="+token;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAAA", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("BBB","duy");
                Log.v("AAAError:",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key,value);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void printReceipt() {
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();

            beginListener();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    void beginListener(){
        final Handler handler = new Handler() ;
        final byte delimiter = 10;
        stopWorker = false;
        readBufferPosition=0;
        readBuffer = new byte[1024];

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopWorker){
                    try {
                        int byteAvailable = inputStream.available();
                        if(byteAvailable>0){
                            byte[] packetByte = new byte[byteAvailable];
                            inputStream.read(packetByte);
                            for (int i=0; i<byteAvailable; i++){
                                byte b = packetByte[i];
                                if(b==delimiter){
                                    byte[] encodedByte = new byte[readBufferPosition];
                                    System.arraycopy(
                                            readBuffer,0,
                                            encodedByte,0,
                                            encodedByte.length
                                    );
                                    final String data = new String( encodedByte,"utf-8");
                                    Log.v("XXX",data.toString());
                                    readBufferPosition=0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                        }
                                    });
                                }else{
                                    readBuffer[readBufferPosition++]=b;
                                }
                            }
                        }
                    } catch (Exception e) {
                        stopWorker=true;
                    }
                }
            }
        });
        thread.start();
    }

    public void printData() throws  IOException{
        try{
//            String msg = txtText.getText().toString();
//            msg+="\n";
            outputStream.write(stringBill().getBytes());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String stringBill(){

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

        BILL =   "\n"+"       CAPPUCCINO COFFEE \n";
        BILL = BILL + "Chung cu mieu noi, phuong 2, quan Binh Thanh, TP.HCM \n \n";
        BILL = BILL + "SDT: 0975331152 \n ";
        BILL = BILL + "           Hoa don \n ";
        BILL = BILL + "-------------------------------\n";
        BILL = BILL + "Dung tai ban:             Ban 1\n";
        BILL = BILL + "Thoi gian: "+currentDate+ " "+currentTime+"\n";
        BILL = BILL + "-------------------------------\n";
        BILL = BILL + "Ten mon:            SL  So tien\n";
        int totalMoney = 0;
        for (Ordered ordered : listOrdered){
            totalMoney+=ordered.getPrice()*ordered.getQuantity();
            BILL = BILL + ordered.getName()+"           "+ordered.getQuantity()+"  "+ordered.getPrice()*ordered.getQuantity()+"\n";
        }

        BILL = BILL + "-------------------------------\n";
        BILL = BILL + "Tong:                "+totalMoney+"\n";
        BILL = BILL + "Cam on va chuc ban mot ngay tuyet voi\n";
        return BILL;
    }

    public void toast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }


}
