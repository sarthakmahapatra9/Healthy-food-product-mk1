package com.serverphone.healthyfoods;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Sarthak on 02-03-2017.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView txt;
    CheckBox chx;
    ItemClickListener itemClickListener;
    public MyHolder(View itemView) {
        super(itemView);

        txt=(TextView)itemView.findViewById(R.id.alacarteFoodText);
        chx=(CheckBox)itemView.findViewById(R.id.checkBox);

        chx.setOnClickListener(this);

    }

    void setItemClickListener(ItemClickListener ic)
    {
        itemClickListener=ic;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());
    }
}
