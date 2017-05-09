package com.serverphone.healthyfoods;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    boolean netConnection=false;
    private EditText signinEmail, signinPassword;
    private TextInputLayout inputLayoutSigninEmail, inputLayoutSigninPassword;
    private Button btnSignIn;
    TextView tv6;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In");

        netConnection=haveNetworkConnection();
        if(netConnection==false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this, R.style.MyDialogTheme);
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
                                    SignIn.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }

        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null)
                {
                    finish();
                    Intent i=new Intent(SignIn.this,Menu1.class);
                    startActivity(i);
                }

            }
        };


        inputLayoutSigninEmail = (TextInputLayout) findViewById(R.id.input_layout_signinEmail);
        inputLayoutSigninPassword = (TextInputLayout) findViewById(R.id.input_layout_signinPassword);
        signinEmail = (EditText) findViewById(R.id.signinEmail);
        signinPassword = (EditText) findViewById(R.id.signinPassword);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        tv6=(TextView)findViewById(R.id.textView6);

        progressDialog=new ProgressDialog(SignIn.this);

        signinEmail.addTextChangedListener(new MyTextWatcher(signinEmail));
        signinPassword.addTextChangedListener(new MyTextWatcher(signinPassword));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(submitForm()==false){

                    Toast.makeText(getApplicationContext(), "Please Check the mandatory fields and try again", Toast.LENGTH_SHORT).show();

                }
                else{
                    String email=signinEmail.getText().toString().trim();
                    String password=signinPassword.getText().toString().trim();

                    progressDialog.setMessage("Signing in.. Please wait...");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                finish();
                                Intent i=new Intent(SignIn.this,Menu1.class);
                                startActivity(i);
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(SignIn.this,"Please check your email id and password",Toast.LENGTH_LONG).show();
                            }

                        }
                    });



                }


            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(SignIn.this,SignUp.class);
                startActivity(i);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
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

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        return true;


    }

    private boolean validateEmail() {
        String email = signinEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutSigninEmail.setError("Not a valid email id");
            requestFocus(signinEmail);
            return false;
        } else {
            inputLayoutSigninEmail.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (signinPassword.getText().toString().trim().isEmpty() || signinPassword.getText().toString().trim().length() < 6) {
            inputLayoutSigninEmail.setError("Atleast 6 characters required");
            requestFocus(signinPassword);
            return false;
        } else {
            inputLayoutSigninPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

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
                case R.id.signinEmail:
                    validateEmail();
                    break;
                case R.id.signinPassword:
                    validatePassword();
                    break;
                /*case R.id.no_people:
                    validatePeople();
                    break;*/

            }
        }
    }


}
