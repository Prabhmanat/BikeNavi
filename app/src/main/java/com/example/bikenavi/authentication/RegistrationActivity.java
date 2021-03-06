package com.example.bikenavi.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.bikenavi.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    // constants
    private static final String TAG = "RegistrationActivity";

    // widgets
    private EditText rFirstName, rLastName,
            rEmail, rPhone, rPassword;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findAllViews();
    }

    private void findAllViews() {
        rFirstName = findViewById(R.id.etFirstName);
        rLastName = findViewById(R.id.etLastName);
        rEmail = findViewById(R.id.etEmail);
        rPassword = findViewById(R.id.etPassword);
        rPhone = findViewById(R.id.etPhoneNumber);

        fAuth = FirebaseAuth.getInstance();
    }

    public void userValidator(String firstName, String lastName,
                                String email, String password,
                                String phoneNumber){

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(phoneNumber)){
            rFirstName.setError(getResources().getString(R.string.err_firstName));
            rLastName.setError(getResources().getString(R.string.err_lastName));
            rEmail.setError(getResources().getString(R.string.err_email));
            rPassword.setError(getResources().getString(R.string.err_password));
            rPhone.setError(getResources().getString(R.string.err_phone));
        }

        if (password.length() < 6){
            rPassword.setError(getResources().getString(R.string.valid_password));
        }
    }

    public void onSignUpClick(View view){
        String firstName = rFirstName.getText().toString().trim();
        String lastName = rLastName.getText().toString().trim();
        String email = rEmail.getText().toString().trim();
        String password = rPassword.getText().toString().trim();
        String phoneNumber = rPhone.getText().toString().trim();

        userValidator(firstName, lastName, email, password, phoneNumber);
    }
}