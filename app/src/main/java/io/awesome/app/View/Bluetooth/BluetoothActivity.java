package io.awesome.app.View.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import io.awesome.app.R;


import java.util.UUID;
import java.util.Set;
import java.util.logging.LogRecord;

import static io.awesome.app.View.Main.MainActivity.bluetoothSocket;

public class BluetoothActivity extends AppCompatActivity {
    private TextView lbl, lblPrinterName;
    private EditText txtText;
    private Button btnConnect, btnDisconnect, btnPrint ;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        lbl = (TextView) findViewById(R.id.lbl);
        lblPrinterName = (TextView) findViewById(R.id.lblPrinterName);

        txtText = (EditText) findViewById(R.id.txtText);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            lblPrinterName.setText("No bluetooth adapter found !!!");
        }
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            findBluetoothDevice();
            openBluetoothPrinter();
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectBT();
            }
        });

    }

    void findBluetoothDevice(){

        if(!bluetoothAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, 0);
        }
        Set<BluetoothDevice> pairDevice = bluetoothAdapter.getBondedDevices();
        if(pairDevice.size()>0){
            for (BluetoothDevice pairedDevice : pairDevice){
                // bluetooth printer name is BTP_F09F1A
                if(pairedDevice.getName().equals("BlueTooth Printer")){
                    bluetoothDevice = pairedDevice;
                    lblPrinterName.setText("Bluetooth printer attached: "+ pairedDevice.getName());
                    break;
                }
            }
        }
//        lblPrinterName.setText("Bluetooth printer attached");
    }

    public void openBluetoothPrinter(){
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void disconnectBT(){
        try {
//            stopWorker=true;
//            outputStream.close();
//            inputStream.close();
            bluetoothSocket.close();
            lblPrinterName.setText("Printer disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }
}
