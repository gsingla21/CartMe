package com.example.foodpalace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BluetoothActivity extends AppCompatActivity {

    Button bluetoothbtn,windowshopping;
//    BluetoothAdapter bluetoothAdapter;
//    public static final int BLUETOOTH_REQ_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        getSupportActionBar().setTitle("Connect My Cart!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        bluetoothbtn=findViewById(R.id.searchcart);
//        windowshopping=findViewById(R.id.windowshopping);
//
//        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        if(bluetoothAdapter==null)
//        {
//            Toast.makeText(BluetoothActivity.this, "This Device doesn't Support Bluetooth", Toast.LENGTH_SHORT).show();
//        }
//        if(!bluetoothAdapter.isEnabled())
//        {
//            bluetoothbtn.setText("Turn Bluetooth On");
//        }else
//        {
//            bluetoothbtn.setText("Turn Bluetooth Off");
//        }
//
//        bluetoothbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!bluetoothAdapter.isEnabled())
//                {
//                    Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(intent,BLUETOOTH_REQ_CODE);
//                }
//                else
//                {
//                    bluetoothAdapter.disable();
//                    bluetoothbtn.setText("Turn Bluetooth OFF");
//                }
//            }
//        });
        windowshopping=findViewById(R.id.windowshopping);
        windowshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BluetoothActivity.this,StoreMe.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==RESULT_OK){
//            Toast.makeText(BluetoothActivity.this, "Bluetooth is ON!", Toast.LENGTH_SHORT).show();
//            bluetoothbtn.setText("Turn Bluetooth OFF");
//        }
//        else if(resultCode==RESULT_CANCELED)
//        {
//            Toast.makeText(BluetoothActivity.this, "Bluetooth Operation is Cancelled!", Toast.LENGTH_SHORT).show();
//        }
//    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}