package com.example.abdullahihsani181654__i180543;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText username;
    EditText password;
    Button login;
    TextView register;
    TextView forgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.loginbutton);
        register=findViewById(R.id.registerbutton);
        forgot=findViewById(R.id.forgotpassword);
        mAuth=FirebaseAuth.getInstance();




    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String get_email=username.getText().toString();
            String get_password=password.getText().toString();
            mAuth.signInWithEmailAndPassword(get_email,get_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(LoginPage.this,"Successfully Logged In",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginPage.this,Chats.class);
                    startActivity(intent);
                }
            });
        }
    });

    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginPage.this,CreateAccount.class);
            startActivity(intent);
        }
    });

    forgot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginPage.this,ForgotPassword.class);
            startActivity(intent);
        }
    });



    }

}