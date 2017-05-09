package com.serverphone.healthyfoods;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class DeliveryAddress extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputName, inputPhone, inputAddress,np;
    private TextInputLayout inputLayoutName, inputLayoutPhone, inputLayoutDeliveryAddress;
    private Button btnSignUp;
    Firebase mrootref;
    ArrayList arrayList=new ArrayList();
    boolean netConnection=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delivery Details");

        mrootref=new Firebase("https://healthy-foods-270de.firebaseio.com/");

        netConnection=haveNetworkConnection();
        if(netConnection==false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryAddress.this, R.style.MyDialogTheme);
            builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.\n Else cancel and try again.")
                    .setTitle("Unable to connect")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                    startActivity(i);
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeliveryAddress.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }



        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_deliveryname);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_deliveryphone);
        inputLayoutDeliveryAddress = (TextInputLayout) findViewById(R.id.input_layout_delivery_address);
        //inputLayoutNP =(TextInputLayout)findViewById(R.id.input_layout_np);
        inputName = (EditText) findViewById(R.id.deliveryfull_name);
        inputPhone = (EditText) findViewById(R.id.deliveryph_no);
        inputAddress = (EditText) findViewById(R.id.delivery_address);
        //np=(EditText)findViewById(R.id.no_people);
        btnSignUp = (Button) findViewById(R.id.deliverybtn_signup);

        Bundle ob=getIntent().getExtras();
        arrayList=(ArrayList)getIntent().getSerializableExtra("mylist");


        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputAddress.addTextChangedListener(new MyTextWatcher(inputAddress));
        //np.addTextChangedListener(new MyTextWatcher(np));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(submitForm()==false){

                    Toast.makeText(getApplicationContext(), "Please Check the mandatory fields and try again", Toast.LENGTH_SHORT).show();

                }
                else{
                    AlertDialog.Builder alert=new AlertDialog.Builder(DeliveryAddress.this,R.style.MyDialogTheme);
                    alert.setTitle("Confirm Address");
                    alert.setMessage("Are you sure?\nOn tapping yes your order will be placed");
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            mrootref = new Firebase("https://healthy-foods-270de.firebaseio.com/" + inputName.getText().toString());
                            Firebase mphone = mrootref.child("Phone");
                            mphone.setValue(inputPhone.getText().toString());
                            Firebase maddress = mrootref.child("Address");
                            maddress.setValue(inputAddress.getText().toString());
                            Firebase order = mrootref.child("Order");
                            order.setValue(arrayList);
                            Toast.makeText(DeliveryAddress.this, "Your order has been placed,\nShortly you will receive an email informing you about your delivery boy's name and time of delivery", Toast.LENGTH_LONG).show();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();



                }
            }
        });
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        /*for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }*/
        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        }else{

            return false;
        }
        //return haveConnectedWifi || haveConnectedMobile;
    }

    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        return true;


    }

    private boolean validateName() { //Check fullname
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Please check the full name field");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() { //check phone number
        String email = inputPhone.getText().toString().trim();

        if (email.isEmpty() /*|| !isValidEmail(email)*/ || !email.matches("[0-9]+") || email.length()<10) {
            inputLayoutPhone.setError("Please recheck the phone number");
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() { //check address
        if (inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutDeliveryAddress.setError("Check Address");
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutDeliveryAddress.setErrorEnabled(false);
        }

        return true;
    }

    /*private boolean validatePeople() {
        String nh=np.getText().toString();
        int numPeople=Integer.parseInt(nh);
        if (np.getText().toString().trim().isEmpty() || !np.getText().toString().matches("[0-9]+") || numPeople<15) {
            inputLayoutNP.setError("Check no. of people");
            requestFocus(np);
            return false;
        } else {
            inputLayoutNP.setErrorEnabled(false);
        }

        return true;
    }*/

    /*private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.deliveryfull_name:
                    validateName();
                    break;
                case R.id.deliveryph_no:
                    validateEmail();
                    break;
                case R.id.delivery_address:
                    validatePassword();
                    break;
                /*case R.id.no_people:
                    validatePeople();
                    break;*/

            }
        }
    }


}



