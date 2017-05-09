package com.serverphone.healthyfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlaCarte extends AppCompatActivity {

    ListView lv;
    String arr[]=new String[]{"Paneer Butter Masala","Chicken Butter Masala","Chicken Biriyani"};
    ArrayList al=new ArrayList();
    ArrayList<String> food=new ArrayList<String>();
    Button btn;
    StringBuffer sb=null;
    MyAdapter adapter;
    RecyclerView rv;


    Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ala_carte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alacarte!!");

        //mref=new Firebase("https://healthy-foods-270de.firebaseio.com/Food items");
        //lv=(ListView)findViewById(R.id.listView);
        btn=(Button)findViewById(R.id.button8);
        rv=(RecyclerView)findViewById(R.id.myRecycler);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Food items");


    /*    FirebaseListAdapter<String> firebaseListAdapter =new FirebaseListAdapter<String>(
                AlaCarte.this,String.class,android.R.layout.simple_list_item_multiple_choice,databaseReference) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView)v.findViewById(android.R.id.text1);
                textView.setText(model);

            }
        };


        lv.setAdapter(firebaseListAdapter);*/
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Iterable<com.google.firebase.database.DataSnapshot> iterable = dataSnapshot.getChildren();
                Iterator<com.google.firebase.database.DataSnapshot> it = iterable.iterator();
                while (it.hasNext()) {
                    com.google.firebase.database.DataSnapshot dataSnapshot1 = it.next();
                    String catnane = dataSnapshot1.getKey();
                    Iterable<com.google.firebase.database.DataSnapshot> iterable2 = dataSnapshot1.getChildren();
                    Iterator<com.google.firebase.database.DataSnapshot> it2 = iterable2.iterator();
                    while (it2.hasNext()) {
                        com.google.firebase.database.DataSnapshot dataSnapshot5 = it2.next();
                        Log.d("qwerty", "" + dataSnapshot5.getValue());
                        String item = dataSnapshot5.getKey() + "  " + dataSnapshot5.getValue();
                        food.add(item);
                    }
                }
                //adapter=new MyAdapter(AlaCarte.this,getPlayers());
                adapter=new MyAdapter(AlaCarte.this,food);
                rv.setLayoutManager(new LinearLayoutManager(AlaCarte.this));
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setAdapter(adapter);

                //ArrayAdapter<String> aa = new ArrayAdapter<String>(AlaCarte.this, android.R.layout.simple_list_item_multiple_choice, food);
                //lv.setAdapter(aa);


                //Log.d("qwerty",dataSnapshot.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*final ArrayAdapter<String> aa=new ArrayAdapter<String>(AlaCarte.this,android.R.layout.simple_list_item_multiple_choice,food);

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key=dataSnapshot.getKey();
                String value=dataSnapshot.getValue(String.class);
                String item=key+"-->"+value;
                food.add(item);
                aa.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/




        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = ((CheckedTextView) view);
                //checkedTextView.setChecked(!checkedTextView.isChecked());
                if (checkedTextView.isChecked()) {
                    checkedTextView.setChecked(false);
                    al.remove(adapterView.getItemAtPosition(i));
                } else {
                    checkedTextView.setChecked(true);
                    al.add(adapterView.getItemAtPosition(i));
                }

            }
        });*/



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
