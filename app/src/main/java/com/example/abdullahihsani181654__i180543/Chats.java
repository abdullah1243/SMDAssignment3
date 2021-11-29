package com.example.abdullahihsani181654__i180543;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Chats extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<ImageClass> ls;
    ImageButton camera;
    ImageButton recv_contacts;
    RecyclerViewAdapter adapter;
    EditText sh;
    TextView tx;
    FirebaseFirestore fb=FirebaseFirestore.getInstance();
    String TAG="123";



    public void updateUserStatus(String state,int userid) {


        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calForTime.getTime());


        Map currentStateMap = new HashMap();
        currentStateMap.put("userid", userid);
        currentStateMap.put("time", saveCurrentTime);
        currentStateMap.put("date", saveCurrentDate);
        currentStateMap.put("Status", state);


        fb.collection("messages")
                .add(currentStateMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(Chats.this, "Status Saved", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chats.this, "Status Failure", Toast.LENGTH_LONG).show();

            }
        });


        }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Intent Get_Contact_Details=getIntent();
        rv = findViewById(R.id.rv);
        camera=findViewById(R.id.camera);
        recv_contacts=findViewById(R.id.contacts);
        sh = (EditText) findViewById(R.id.sh);

        sh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        recv_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Chats.this,AddNewContact.class);
                startActivity(intent);
            }
        });

        ls = new ArrayList<>();
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null),"Abdullah","Hello How are You?",ResourcesCompat.getDrawable(getResources(),R.drawable.online,null),null));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_women, null),"Zaid","XD",ResourcesCompat.getDrawable(getResources(),R.drawable.offline,null),null));
        ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.smiley, null),"Ali",":|",ResourcesCompat.getDrawable(getResources(),R.drawable.online,null),null));

            for(int i=0;i<3;i++) {
                if (ls.get(i).getState()=="online"){
                    updateUserStatus("online",i);
                }
                else{
                    updateUserStatus("offline",i);
                }

            }



            Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(Manifest.permission.READ_CONTACTS)) {
                            getContacts();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(Chats.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        Bitmap bitmap = (Bitmap) Get_Contact_Details.getParcelableExtra("BitmapImage");
        String Get_First_Name=Get_Contact_Details.getStringExtra("FirstName");
        String Get_Last_Name=Get_Contact_Details.getStringExtra("LastName");
        String Get_Phone_Number=Get_Contact_Details.getStringExtra("PhoneNumber");



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewAdapter(ls,this);
        rv.setAdapter(adapter);

    }

    private void getContacts() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        while (phones.moveToNext()) {
            @SuppressLint("Range") String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            @SuppressLint("Range") String phoneURI = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            // int id = getResources().getIdentifier(phoneURI, "drawable", getPackageName());
            // Drawable pic = getResources().getDrawable(id);

            ls.add(new ImageClass(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_women, null), name, phoneNumber, ResourcesCompat.getDrawable(getResources(),R.drawable.online,null),null));
        }

        phones.close();
    }

    private void filter(String text) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();

        for (ImageClass item : ls) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}
