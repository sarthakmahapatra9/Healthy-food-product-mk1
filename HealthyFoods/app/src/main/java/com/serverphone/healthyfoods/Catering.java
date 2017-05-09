package com.serverphone.healthyfoods;

/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catering");*/

import android.os.Bundle;
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
import android.widget.Toast;

public class Catering extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputName, inputPhone, inputDate,np;
    private TextInputLayout inputLayoutName, inputLayoutPhone, inputLayoutEventDate,inputLayoutNP;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catering");

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutEventDate = (TextInputLayout) findViewById(R.id.input_layout_event_date);
        inputLayoutNP =(TextInputLayout)findViewById(R.id.input_layout_np);
        inputName = (EditText) findViewById(R.id.full_name);
        inputPhone = (EditText) findViewById(R.id.ph_no);
        inputDate = (EditText) findViewById(R.id.event_date);
        np=(EditText)findViewById(R.id.no_people);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputDate.addTextChangedListener(new MyTextWatcher(inputDate));
        np.addTextChangedListener(new MyTextWatcher(np));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    // Email= Phone, Password=Date,
    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Please check the full name field");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
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

    private boolean validatePassword() {
        if (inputDate.getText().toString().trim().isEmpty()) {
            inputLayoutEventDate.setError("Check Date");
            requestFocus(inputDate);
            return false;
        } else {
            inputLayoutEventDate.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePeople() {
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
    }

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
                case R.id.full_name:
                    validateName();
                    break;
                case R.id.ph_no:
                    validateEmail();
                    break;
                case R.id.event_date:
                    validatePassword();
                    break;
                case R.id.no_people:
                    validatePeople();
                    break;

            }
        }
    }
}
