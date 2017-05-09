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

public class SignUp extends AppCompatActivity {

    boolean netConnection=false;
    private EditText signupEmail, signupPassword;
    private TextInputLayout inputLayoutSignupEmail, inputLayoutSignupPassword;
    private Button btnSignUp;
    TextView tv8;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        netConnection=haveNetworkConnection();
        if(netConnection==false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this, R.style.MyDialogTheme);
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
                                    SignUp.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {

            finish();
            Intent i=new Intent(SignUp.this,Menu1.class);
            startActivity(i);
        }

        inputLayoutSignupEmail = (TextInputLayout) findViewById(R.id.input_layout_signupEmail);
        inputLayoutSignupPassword = (TextInputLayout) findViewById(R.id.input_layout_signupPassword);
        signupEmail = (EditText) findViewById(R.id.signupEmail);
        signupPassword = (EditText) findViewById(R.id.signupPassword);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        tv8=(TextView)findViewById(R.id.textView8);

        progressDialog=new ProgressDialog(SignUp.this);

        signupEmail.addTextChangedListener(new MyTextWatcher(signupEmail));
        signupPassword.addTextChangedListener(new MyTextWatcher(signupPassword));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (submitForm() == false) {

                    Toast.makeText(getApplicationContext(), "Please Check the mandatory fields and try again", Toast.LENGTH_SHORT).show();

                } else {

                    String email=signupEmail.getText().toString().trim();
                    String password=signupPassword.getText().toString().trim();

                    progressDialog.setMessage("Registering user.. Please wait...");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {

                            progressDialog.dismiss();

                            if(!task.isSuccessful())
                            {

                                Toast.makeText(SignUp.this,"Registration error "+task.getException(),Toast.LENGTH_LONG).show();
                            }
                            else{
                                finish();
                                Intent intent=new Intent(SignUp.this,Menu1.class);
                                startActivity(intent);
                            }


                        }
                    });

                }

            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUp.this,SignIn.class);
                startActivity(i);
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

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        return true;


    }

    private boolean validateEmail() {
        String email = signupEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutSignupEmail.setError("Not a valid email id");
            requestFocus(signupEmail);
            return false;
        } else {
            inputLayoutSignupEmail.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (signupPassword.getText().toString().trim().isEmpty() || signupPassword.getText().toString().trim().length() < 6) {
            inputLayoutSignupEmail.setError("Atleast 6 characters required");
            requestFocus(signupPassword);
            return false;
        } else {
            inputLayoutSignupPassword.setErrorEnabled(false);
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
                case R.id.signupEmail:
                    validateEmail();
                    break;
                case R.id.signupPassword:
                    validatePassword();
                    break;
                /*case R.id.no_people:
                    validatePeople();
                    break;*/

            }
        }
    }


}
