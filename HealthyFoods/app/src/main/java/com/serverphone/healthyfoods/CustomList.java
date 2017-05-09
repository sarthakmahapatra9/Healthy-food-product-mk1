package com.serverphone.healthyfoods;

/**
 * Created by Sarthak on 19-01-2017.
 */
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    //private final String[] texts;
    ArrayList arrayList=new ArrayList();
    int de[];
 //   TextView tv4,tv5,tv6;
   // ImageButton btn4,btn6;
    //private final Integer[] imageId;

    public CustomList(Activity context, ArrayList web){
                      /*String[] web){, Integer[] imageId) {*/
        super(context, R.layout.singlelist, web);
        this.context = context;
        arrayList=web;
        de=new int[arrayList.size()];
        //this.texts = web;
        //this. = imageId;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singlelist, null, false);
        //TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        //tv4=(TextView)rowView.findViewById(R.id.textView4);
        final TextView txtTitle = (TextView) rowView.findViewById(R.id.info_text);
        ImageButton btn4=(ImageButton)rowView.findViewById(R.id.button11);
        final TextView tv4=(TextView)rowView.findViewById(R.id.textView4);
        final ImageButton btn6=(ImageButton)rowView.findViewById(R.id.button12);
        //tv6=(TextView)rowView.findViewById(R.id.textView6);
        tv4.setText(""+de[position]);
        txtTitle.setText((CharSequence) arrayList.get(position));

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String h = tv4.getText().toString();
                int num = Integer.parseInt(h);
                if (num == 1) ;
                else {
                    num = num - 1;
                    de[position]=num;
                    String s = Integer.toString(num);
                    tv4.setText(s);
                }

                Log.d("log","this1 "+ num);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h=tv4.getText().toString();
                int num=Integer.parseInt(h);
                //if(num==1);
                num=num+1;
                String s = Integer.toString(num);
                de[position]=num;
                tv4.setText(s);
                Log.d("log","this2 " + num);
            }
        });

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

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        //txtTitle.setText(texts[position]);


        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}