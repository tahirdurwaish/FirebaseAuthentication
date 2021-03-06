package com.mejrabsoft.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword,mPhone;
    Button mButtonRegister;
    TextView mAlreadySigIn;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFullName = findViewById(R.id.edt_full_name);
        mEmail = findViewById(R.id.edt_email);
        mPassword = findViewById(R.id.edt_password);
        mPhone = findViewById(R.id.edt_phone);
        mButtonRegister = findViewById(R.id.btn_register);
        mAlreadySigIn = findViewById(R.id.txt_already_signin);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        mAuth = FirebaseAuth.getInstance();


        mAlreadySigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });

        if(mAuth.getCurrentUser() != null){

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){

                    mEmail.setError("Email is Required.");

                    return;
                }

                if(TextUtils.isEmpty(password)){

                    mPassword.setError("Password is Required.");

                    return;
                }

                if(password.length() < 6){

                    mPassword.setError("Password Must be >= 6 Characters.");

                    return;
                }



                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();


                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {

                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });



            }
        });

    }
}
