package com.example.clientpro;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.IRemoteAIdle;
import com.example.myapplication.Product;

public class MainActivity extends AppCompatActivity {
    IRemoteAIdle addService;
    Button Add;
    Button Get;
    TextView text;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Connect", "Service Connected");
            addService = IRemoteAIdle.Stub.asInterface(iBinder);
            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        addService.addProduct("Kosta", 4, 40);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

            Get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Product kosta = addService.getProduct("Kosta");
                        text.setText(kosta.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Disconnect", "Service Disconnected");
            addService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConnection();
        Add = findViewById(R.id.add);
        Get = findViewById(R.id.get);
        text = findViewById(R.id.text);

    }

    private void initConnection() {
        if (addService == null) {
            Intent i = new Intent("com.example.myapplication.ProductService");

            /*From 5.0 annonymous intent calls are suspended so replacing with server app's package name*/
            i.setPackage("com.example.myapplication");

            // binding to remote service
            bindService(i, serviceConnection, Service.BIND_AUTO_CREATE);
        }
    }
}
