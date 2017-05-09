package com.serverphone.healthyfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class InstantOrderNveg extends AppCompatActivity {

    Button btn7;
    RadioGroup rg;
    String opinion="Rice/Roti, Coconut mix\\n sabji, Bhaji/Salad,\\n Egg Curry (2 pieces)                    ₹60\\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_order_nveg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Instant Order Non Veg");

        rg=(RadioGroup)findViewById(R.id.radioGroup);
        btn7=(Button)findViewById(R.id.button7);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_anim);
        btn7.startAnimation(anim);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InstantOrderNveg.this, Confirm.class);
                i.putExtra("order",opinion);
                startActivity(i);
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                opinion=rb.getText().toString();
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
