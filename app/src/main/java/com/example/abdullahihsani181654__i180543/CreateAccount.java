package com.example.abdullahihsani181654__i180543;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText confirmpassword;
    Button register;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        register=findViewById(R.id.registerbutton);
        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_email=email.getText().toString();
                String get_password=password.getText().toString();
                String get_confirmpassword=confirmpassword.getText().toString();
                if (!get_password.equals(get_confirmpassword)){
                    Toast.makeText(CreateAccount.this,"Passwords Do Not Match",Toast.LENGTH_LONG).show();
                }
                else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String current_user=user.getEmail().toString();
                    if(current_user==get_email){
                        Toast.makeText(CreateAccount.this,"User Already Exists",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent=new Intent(CreateAccount.this,CreateProfile.class);
                        intent.putExtra("Email", get_email);
                        intent.putExtra("Password", get_password);
                        intent.putExtra("ConfirmPassword", get_confirmpassword);
                        startActivity(intent);
                    }
                }

            }
        });







    }
}