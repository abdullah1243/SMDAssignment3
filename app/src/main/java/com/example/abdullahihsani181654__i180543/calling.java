package com.example.abdullahihsani181654__i180543;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class calling extends AppCompatActivity {
    ImageView image;
    TextView contact_name;
    ImageButton end_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        Intent intent=getIntent();
        image=findViewById(R.id.myimageview);
        contact_name=findViewById(R.id.contact_name);
        end_call=findViewById(R.id.end_call);

        String get_status=intent.getStringExtra("STATUS_S");
        String get_name=intent.getStringExtra("NAME_S");

        contact_name.setText(get_name);

        end_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(calling.this,SpecificChat.class);
                startActivity(intent);
            }
        });



    }
}