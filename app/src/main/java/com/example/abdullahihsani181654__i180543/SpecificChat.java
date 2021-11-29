package com.example.abdullahihsani181654__i180543;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.screenshotdetection.ScreenshotDetectionDelegate;
import com.github.piasy.rxscreenshotdetector.RxScreenshotDetector;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpecificChat extends AppCompatActivity {
    protected final String TAG=getClass().getSimpleName();

    ArrayList<SpecificChatClass> ls;
    private static final int pic_id=123;
    TextView name;
    ImageView status;
    ImageButton camera;
    EditText message_text;
    ImageButton send;
    ImageButton call;
    ImageButton backbutton;
    RecyclerView rv;
    ImageView click_image_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);




        name = findViewById(R.id.textnameview);
        status = findViewById(R.id.textstatusview);
        camera=(ImageButton) findViewById(R.id.camera);
        message_text=(EditText) findViewById(R.id.message_text);
        send=findViewById(R.id.Send);
        call=findViewById(R.id.call);
        backbutton=findViewById(R.id.backbutton);
        rv=findViewById(R.id.rv);

        String get_name=getIntent().getStringExtra("NAME");
        String get_status=getIntent().getStringExtra("STATUS");
        name.setText(get_name);
        status.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("IMAGE")));




        ls = new ArrayList<>();

        camera.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });




        send.setOnClickListener(v -> {

            String get_text_message=message_text.getText().toString();
            name.setText(getIntent().getStringExtra("NAME"));
            status.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("STATUS")));
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("Name", get_name);
            user.put("Status", get_status);
            user.put("Message",get_text_message);

// Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(SpecificChat.this,"Message Stored Into Database",Toast.LENGTH_LONG).show();
                            message_text.setText("");
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SpecificChat.this, 1);
                            rv.setLayoutManager(mLayoutManager);

                            ls.add(new SpecificChatClass(get_text_message));
                            RecyclerViewAdapterSpecificChat adapter;
                            adapter = new RecyclerViewAdapterSpecificChat(ls,SpecificChat.this);
                            adapter.notifyDataSetChanged();
                            rv.setAdapter(adapter);









                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            Toast.makeText(SpecificChat.this,"Error In Storing",Toast.LENGTH_LONG).show();

                        }
                    });



        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SpecificChat.this, calling.class);
                intent.putExtra("NAME_S",get_name);
                intent.putExtra("STATUS_S",get_status);
                startActivity(intent);
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SpecificChat.this,Chats.class);
                startActivity(intent);
            }
        });

        

    }


    protected void onActivityResult(int requestCode,int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        click_image_id=findViewById(R.id.click_image_view);
        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            click_image_id.setImageBitmap(photo);
        }
    }



}