package com.serverphone.healthyfoods;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Menu1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView iv;
    TextView tv,tv2;
    int i=1;
    int imageArray[]={R.drawable.chicken_curry1,R.drawable.chicken_biriyani,R.drawable.paneer_butter,R.drawable.food,R.drawable.foodg,R.drawable.foodk,R.drawable.kitchen};
    String textArray[]={"Chicken curry","Chicken Biriyani","Paneer Butter Masala","Fresh and Healthy","Creamy and delicious","Pasta with red sauce","Cooked with care and love"};
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Menu");

        firebaseAuth= FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            Intent i=new Intent(Menu1.this,SignIn.class);
            startActivity(i);
        }*/


        //tv=(TextView)findViewById(R.id.info_text);
        iv=(ImageView)findViewById(R.id.image);
        tv2=(TextView)findViewById(R.id.textView2);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true)
                {   runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageResource(imageArray[i]);
                        tv2.setText(textArray[i]);
                    }
                });

                    i++;
                    if(imageArray.length==i)
                        i=0;
                    try {
                        Thread.sleep(4000);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        /*tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv.setImageResource(imageArray[i]);
                tv2.setText(textArray[i]);
                if(i==imageArray.length-1)
                    i=0;
                else
                    i++;
            }
        });*/




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                /*AlertDialog.Builder alert=new AlertDialog.Builder(Menu1.this,R.style.MyDialogTheme);
                alert.setTitle("Sign Out??");
                alert.setMessage("Are you sure?\nYou want to sign out?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        firebaseAuth.signOut();
                        finish();
                        Intent intent=new Intent(Menu1.this,SignIn.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();*/


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if(id == R.id.signout)
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(Menu1.this,R.style.MyDialogTheme);
            alert.setTitle("Sign Out??");
            alert.setMessage("Are you sure?\nYou want to sign out?");
            alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    firebaseAuth.signOut();
                    finish();
                    Intent intent=new Intent(Menu1.this,SignIn.class);
                    startActivity(intent);
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();


        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.iov) {
            // Handle the camera action
            Intent i=new Intent(Menu1.this,InstantOrderVeg.class);
            startActivity(i);
        } else if (id == R.id.ionv) {
            Intent i=new Intent(Menu1.this,InstantOrderNveg.class);
            startActivity(i);

        } else if (id == R.id.alaca) {
            Intent i=new Intent(Menu1.this,AlaCarte.class);
            startActivity(i);

        } else if (id == R.id.cater) {
            Intent i=new Intent(Menu1.this,Catering.class);
            startActivity(i);

        } else if (id == R.id.cont) {
            Intent i=new Intent(Menu1.this,ContactUs.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
