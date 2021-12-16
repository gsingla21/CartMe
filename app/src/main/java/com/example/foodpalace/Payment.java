package com.example.foodpalace;

import static java.lang.Float.parseFloat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodpalace.Common.Common;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    Button pay;
    TextView amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Checkout.preload(getApplicationContext());
        pay=findViewById(R.id.razorpay);
        amount=findViewById(R.id.payAmount);
        amount.setText("Amount: Rs. "+Common.getPayamount());

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paynow(Common.getPayamount());
            }
        });


    }

    private void paynow(String amount) {
        final Activity activity=this;

        Checkout checkout=new Checkout();
        checkout.setKeyID("xxxxAPI_KEYxxxx");
        checkout.setImage(R.drawable.ic_baseline_shopping_cart_24);
        double finalamount = parseFloat(amount)*100;


        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", finalamount+""); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");
            checkout.open(activity,orderRequest);


        } catch (JSONException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Payment.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Payment.this, s, Toast.LENGTH_SHORT).show();
    }
@Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
