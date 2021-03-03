package com.example.quiz_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class sighnup extends AppCompatActivity {
    private EditText etpassword,etconfirmpassword,etemail;
    private Button btnsighnup;
    private TextView sighnintv;
    private FirebaseAuth firebaseAuth;
    private  ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighnup);
        firebaseAuth=FirebaseAuth.getInstance();
        etemail=findViewById(R.id.etEmailAddress);
        etpassword=findViewById(R.id.etPassword);
        etconfirmpassword=findViewById(R.id.etconfirmPassword);
        btnsighnup=findViewById(R.id.btnsignn);
        sighnintv=findViewById(R.id.sighnin);
         progressDialog=new ProgressDialog(this);
        btnsighnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
        sighnintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sighnup.this,loginactivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
    private void Register(){
        String email=etemail.getText().toString();
        String password=etpassword.getText().toString();
        String confirmpassword=etconfirmpassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            etemail.setError("Enter Email Address");
            return;
        } else if(TextUtils.isEmpty(password)){
            etpassword.setError("Enter Password Please");
            return;
        }
        else if(TextUtils.isEmpty(confirmpassword)){
            etconfirmpassword.setError("Confirm your password Please");
            return;
        }else if(!password.equals(confirmpassword)){
            etconfirmpassword.setError("Password Didn't Match");
            return;
        }else if(password.length()<5){
            etpassword.setError("Password must be more than 5 characters");
            return;
        }
        else if(!isValid(email)){
            etemail.setError("Invalid Email");
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(sighnup.this, "Sighn Up Successfuly", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(sighnup.this,dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(sighnup.this, "Sighn Up Failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });


    }
    private boolean isValid(CharSequence target){
        return (!TextUtils.isEmpty(target)&& Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}