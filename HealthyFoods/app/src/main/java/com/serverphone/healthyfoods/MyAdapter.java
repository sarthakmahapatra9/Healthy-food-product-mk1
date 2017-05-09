package com.serverphone.healthyfoods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Sarthak on 02-03-2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Activity context;
    ArrayList<String> players;
    boolean sd[];
    Button bt;

    MyAdapter(Activity c,ArrayList<String> al)
    {
        context=c;
        this.players=al;
        sd=new boolean[al.size()];
        bt=(Button)c.findViewById(R.id.button8);
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_model,null);
        MyHolder holder=new MyHolder(v);
        return holder;
    }
    ArrayList<String> function()
    {   ArrayList<String> strings=new ArrayList<>();
        for(int i=0;i<sd.length;i++)
        {
            if(sd[i])
            {
                strings.add(players.get(i));
            }
        }
        return  strings;
    }
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.txt.setText(players.get(position));
        if(sd[position])
        {
            holder.chx.setChecked(true);
        }

        else
        holder.chx.setChecked(false);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> strings=new ArrayList<String>();
                strings=function();
                if(strings.isEmpty()){
                    Snackbar.make(view, "Tap on the checkboxes to select items", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    Intent intent=new Intent(context,ConfirmAlacarte.class);
                    intent.putStringArrayListExtra("mylist",function());
                    context.startActivity(intent);
                }


            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chx=(CheckBox)v;
                if(chx.isChecked())
                {
                 sd[position]=true;
                    chx.setChecked(true);

                }
                else
                {
                    sd[position]=false;
                    chx.setChecked(false);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
