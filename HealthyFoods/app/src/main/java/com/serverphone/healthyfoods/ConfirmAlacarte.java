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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfirmAlacarte extends AppCompatActivity {

    ArrayList<String> arrayList=new ArrayList<String>();
    ListView lv;
    Button btn;
    ImageButton btn4,btn6;
    TextView tv4,tv5,tv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_alacarte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Confirm Order");

        //CustomList adapter=new

        lv=(ListView)findViewById(R.id.listView2);
        btn=(Button)findViewById(R.id.button9);
        arrayList=getIntent().getStringArrayListExtra("mylist");
        //ArrayAdapter<String> aa=new ArrayAdapter<String>(ConfirmAlacarte.this,android.R.layout.simple_list_item_1,arrayList);
        CustomList adapter=new CustomList(ConfirmAlacarte.this,arrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tv4=(TextView)findViewById(R.id.textView4);
                btn4=(ImageButton)findViewById(R.id.button11);
                tv4=(TextView)findViewById(R.id.textView4);
                btn6=(ImageButton)findViewById(R.id.button12);
                //tv6=(TextView)findViewById(R.id.textView6);
                /*tv4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String h=tv5.getText().toString();
                        int num=Integer.parseInt(h);
                        if(num==1);
                        else{
                            num=num-1;
                            String s=Integer.toString(num);
                            tv5.setText(s);
                        }

                    }
                });
                tv6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String h=tv5.getText().toString();
                        int num=Integer.parseInt(h);
                        //if(num==1);
                        num=num+1;
                        String s = Integer.toString(num);
                        tv5.setText(s);


                    }
                });*/
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String h = tv5.getText().toString();
                        int num = Integer.parseInt(h);
                        if (num == 1) ;
                        else {
                            num = num - 1;
                            String s = Integer.toString(num);
                            tv5.setText(s);
                        }
                    }
                });

                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String h=tv5.getText().toString();
                        int num=Integer.parseInt(h);
                        //if(num==1);
                        num=num+1;
                        String s = Integer.toString(num);
                        tv5.setText(s);
                    }
                });


            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(ConfirmAlacarte.this,R.style.MyDialogTheme);
                alert.setTitle("Confirm");
                alert.setMessage("Are you sure?\nYou want to proceed with the order");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // Toast.makeText(ConfirmAlacarte.this, "Your order has been placed,\nShortly you will receive an email informing you about your delivery boy's name and time of delivery", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(ConfirmAlacarte.this,DeliveryAddress.class);
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
