package io.awesome.app.Presenter.Receipt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Receipt;
import io.awesome.app.View.Fragment.Receipt.FragmentReceipt;

import static io.awesome.app.View.Main.MainActivity.account;
import static io.awesome.app.View.Main.MainActivity.bluetoothSocket;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
import static io.awesome.app.View.Table.TableActivity.nameTableMoveOrdered;

/**
 * Created by sung on 26/11/2017.
 */

public class ReceiptPresenterImpl implements ReceiptPresenter {
    private Receipt receipt;
    private Context context;
    private FragmentReceipt fragmentReceipt;
    private String token;

    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean stopWorker;
    private int readBufferPosition;
    private byte[] readBuffer;
    private Thread thread;
    private String BILL = "";

    public ReceiptPresenterImpl(Context context, FragmentReceipt fragmentReceipt, String token) {
        this.context = context;
        this.fragmentReceipt = fragmentReceipt;
        this.token = token;
    }

    @Override
    public void getMenuReceipt(final String token) {
        fragmentReceipt.loadMenuReceipt(listOrdered);
    }

    @Override
    public void updateReceipt() {
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

            writeWithFormat("CAPPUCCINO COFFEE\n".getBytes(), new Formatter().bold().get(), Formatter.centerAlign());
            writeWithFormat("Dai hoc bach khoa TP.HCM \n".getBytes(), new Formatter().get(), Formatter.centerAlign());
            writeWithFormat("SDT: 0975331152\n".getBytes(), new Formatter().get(), Formatter.centerAlign());


            writeWithFormat("\nBIEN LAI\n".getBytes(), new Formatter().bold().get(), Formatter.centerAlign());
            writeWithFormat("--------------------------------\n".getBytes(), new Formatter().bold().get(), Formatter.centerAlign());


            writeWithFormat(String.valueOf("Dung tai:"+countSpace(32,"Dung tai:", nameTableMoveOrdered)+nameTableMoveOrdered+"\n").getBytes(), new Formatter().get(), Formatter.rightAlign());
            writeWithFormat(String.valueOf("Phuc vu boi:"+countSpace(32,"Phuc vu boi:", account.getDisplayName())+account.getDisplayName()+"\n").getBytes(), new Formatter().get(), Formatter.rightAlign());

            String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
            writeWithFormat(String.valueOf("Vao ngay:"+countSpace(32,"Vao ngay:", currentDate)+currentDate+"\n").getBytes(), new Formatter().get(), Formatter.rightAlign());
            writeWithFormat(String.valueOf("Thoi gian:"+countSpace(32,"Thoi gian:", currentTime)+currentTime+"\n").getBytes(), new Formatter().get(), Formatter.rightAlign());

            writeWithFormat("--------------------------------\n".getBytes(), new Formatter().bold().get(), Formatter.centerAlign());

            writeWithFormat("Mat hang            SL   So tien\n".getBytes(), new Formatter().bold().get(), Formatter.rightAlign());

            int totalMoney = 0;
            for (Ordered ordered : listOrdered){
                String space1 = countSpace(21,ordered.getName(), String.valueOf(ordered.getQuantity()));
                String space2 = countSpace(12,String.valueOf(ordered.getQuantity()), String.valueOf(ordered.getPrice()*ordered.getQuantity()+" "));

                ;
                String row = ordered.getName()+space1+ordered.getQuantity()+space2+NumberFormat.getNumberInstance(Locale.GERMAN).format(ordered.getPrice()*ordered.getQuantity());

                writeWithFormat(String.valueOf(row+"\n").getBytes(), new Formatter().get(), Formatter.rightAlign());
                totalMoney+=ordered.getPrice()*ordered.getQuantity();
            }

            writeWithFormat("--------------------------------\n".getBytes(), new Formatter().bold().get(), Formatter.centerAlign());

            writeWithFormat(String.valueOf("Tong:"+countSpace(32,"Tong:", String.valueOf(totalMoney)+"  d")+NumberFormat.getNumberInstance(Locale.GERMAN).format(totalMoney)+" d\n").getBytes(), new Formatter().get(), Formatter.rightAlign());

            writeWithFormat("Chuc ban mot ngay tuyet voi\n".getBytes(), new Formatter().bold().get(), Formatter.centerAlign());

    }
    private String countSpace(int size ,String left, String right){
        String space="";
        for(int i =0; i< size-left.length()-right.length();i++){
            space+= " ";
        }
        return space;
    }



    public boolean writeWithFormat(byte[] buffer, final byte[] pFormat, final byte[] pAlignment) {
        try {
            // Notify printer it should be printed with given alignment:
            outputStream.write(pAlignment);
            // Notify printer it should be printed in the given format:
            outputStream.write(pFormat);
            // Write the actual data:
            outputStream.write(buffer, 0, buffer.length);

            // Share the sent message back to the UI Activity
            /*.getHandler().obtainMessage(MESSAGE_WRITE, buffer.length, -1, buffer).sendToTarget();*/
            return true;
        } catch (IOException e) {
            Log.v("AAA", "Exception during write", e);
            return false;
        }
    }

    public void toast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }


}
