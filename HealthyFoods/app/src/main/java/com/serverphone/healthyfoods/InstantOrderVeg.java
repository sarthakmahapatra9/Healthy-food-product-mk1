package com.serverphone.healthyfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

public class InstantOrderVeg extends AppCompatActivity {

    RadioButton rb1,rb2;
    RadioGroup rg;
    String opinion;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_order_veg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Instant Order Veg");

        rg=(RadioGroup)findViewById(R.id.radioGroup1);
        rb1=(RadioButton)findViewById(R.id.radioButton5);
        rb2=(RadioButton)findViewById(R.id.radioButton6);
        btn=(Button)findViewById(R.id.button15);
        Calendar calendar=Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);


        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                //Choiceable Order
                //rb1.setText()
                finish();
                Intent i=new Intent(InstantOrderVeg.this,AlaCarte.class);
                startActivity(i);
                break;

            case Calendar.MONDAY:
                // Current day is Monday
                rb1.setText("Normal: Rice/Roti, Dal,\nDhokla, Bhaji, Salad                            50/-\n");
                rb2.setText("Special: Paneer/Mushroom,\nSweet, Papad with Normal\nmeal                                                      90/-");
                break;

            case Calendar.TUESDAY:
                rb1.setText("Normal: Rice/Roti, Green\nMotor Aloo Sabji, Dahi \nKadi, Bhaji, Aloo Choka                      50/-\n");
                rb2.setText("Special: Paneer/Mushroom,\n" +
                        "Sweet, Papad with Normal\n" +
                        "meal                                                      90/-");
                break;

            case Calendar.WEDNESDAY:
                rb1.setText("Normal: Rice/Roti, Coconut\nmix sabji, Bhaji, Pickle,\nSamber, Papad                                    50/-\n");
                rb2.setText("Special: Paneer/Mushroom,\n" +
                        "Sweet, Papad with Normal\n" +
                        "meal                                                      90/-");
                break;

            case Calendar.THURSDAY:
                rb1.setText("Normal: Rice/Roti, Soyabean\nSabji, Bioled Dal,\nBhaji, Aloo Choka                                50/-\n");
                rb2.setText("Special: Paneer/Mushroom,\n" +
                        "Sweet, Papad with Normal\n" +
                        "meal                                                      90/-");
                break;

            case Calendar.FRIDAY:
                rb1.setText("Normal: Rice/Roti, Dal,\nDhokla, Bhaji, Salad                            50/-\n");
                rb2.setText("Special: Paneer/Mushroom,\n" +
                        "Sweet, Papad with Normal\n" +
                        "meal                                                      90/-");
                break;

            case Calendar.SATURDAY:
                rb1.setText("Normal: Rice(Tomato/\nLemon/Jeera), Veg sabji/Kofta\n sabji, Raita, Papad                             50/-\n");
                rb2.setText("Special: Paneer/Mushroom,\n" +
                        "Sweet, Papad with Normal\n" +
                        "meal                                                      90/-");
                break;
                // etc.
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                opinion=rb.getText().toString();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InstantOrderVeg.this, Confirm.class);
                i.putExtra("order",opinion);
                startActivity(i);
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
