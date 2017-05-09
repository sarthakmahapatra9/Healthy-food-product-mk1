package com.serverphone.healthyfoods;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Confirm extends AppCompatActivity {

    TextView tv,tv5;
    Button btn,btn13,btn14;
    ArrayList arrayList=new ArrayList();
    String noOfPlates=new String();
    String order=new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Confirm");

        tv=(TextView)findViewById(R.id.textView3);
        btn13=(Button)findViewById(R.id.button13);
        tv5=(TextView)findViewById(R.id.textView5);
        btn14=(Button)findViewById(R.id.button14);
        btn=(Button)findViewById(R.id.button10);
        Bundle ob=getIntent().getExtras();
        order=ob.getString("order");
        tv.setText(order);
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h = tv5.getText().toString();
                int num = Integer.parseInt(h);
                if (num == 1) ;
                else {
                    num = num - 1;
                    String s = Integer.toString(num);
                    noOfPlates=Integer.toString(num);
                    tv5.setText(s);
                }
            }
        });
        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h=tv5.getText().toString();
                int num=Integer.parseInt(h);
                //if(num==1);
                num=num+1;
                String s = Integer.toString(num);
                noOfPlates=Integer.toString(num);
                tv5.setText(s);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(Confirm.this,R.style.MyDialogTheme);
                alert.setTitle("Confirm Order");
                alert.setMessage("Are you sure?\nYou want to fix the order");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(Confirm.this, "Your order has been placed,\nShortly you will receive an email informing you about your delivery boy's name and time of delivery", Toast.LENGTH_LONG).show();
                        String deliveryOrder=order+"\t"+noOfPlates;
                        arrayList.add(deliveryOrder);
                        Intent intent=new Intent(Confirm.this,DeliveryAddress.class);
                        intent.putExtra("mylist",arrayList);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
