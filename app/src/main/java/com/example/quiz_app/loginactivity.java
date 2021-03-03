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

public class loginactivity extends AppCompatActivity {
    private EditText etpassword1,etconfirmpassword1,etemail1;
    private Button loginbtn;
    private TextView sighnuplog;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        firebaseAuth=FirebaseAuth.getInstance();
        etemail1=findViewById(R.id.loginemail);
        etpassword1=findViewById(R.id.loginpassword);
        etconfirmpassword1=findViewById(R.id.etconfirmPassword);
        loginbtn=findViewById(R.id.btnlogin);
        sighnuplog=findViewById(R.id.signuplog);
        progressDialog=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
        sighnuplog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginactivity.this,sighnup.class);
                startActivity(intent);
                finish();

            }
        });
    }
    private void login(){
        String email=etemail1.getText().toString();
        String password=etpassword1.getText().toString();
//        String confirmpassword=etconfirmpassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            etemail1.setError("Enter Email Address");
            return;
        } else if(TextUtils.isEmpty(password)){
            etpassword1.setError("Enter Password Please");
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(loginactivity.this, "Log IN Successfuly", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(loginactivity.this,dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(loginactivity.this, "Sighn Up Failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });


    }

}