package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String[] cat={"Food","Groceries","Restaurant","Pharmacy","SuperMarket","Fashion","Electronics"};
    TextView name,location,phone,username,password,shopid;
    Spinner spinner;
    ArrayAdapter adapter;
    ProgressBar progressBarRegister;
    Double my_lat,my_lon;
    String opentime="10:00",closetime="21:00",delivery="NO";
    Integer currorderid=0;
    Double rating=3.5;
    FirebaseFirestore mDBRef=FirebaseFirestore.getInstance();
    Validator validator;
    Boolean documentExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getCurrentLocation();

    }

    public void getCurrentLocation(){
        Tracer gps_for_filter = new Tracer(MainActivity.this);
        if (gps_for_filter.getLocation() != null) {
            my_lat = gps_for_filter.getLatitude();
            my_lon = gps_for_filter.getLongitude();
            Toast.makeText(this,"Your location is: "+ my_lat + " " + my_lon,Toast.LENGTH_LONG).show();;
            Log.d("Register onCreate", "Your location is " + my_lat + " " + my_lon);
        } else {
            //gps.showSettingAlert();
            my_lat = 12.942898;
            my_lon = 77.56819659999996;
            Toast.makeText(this,"Your location cannot be found",Toast.LENGTH_LONG).show();;
            Log.d("Register onCreate", "Your location cannot be found");
        }
    }

    public void initViews(){
        adapter=new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                cat);
        spinner=(Spinner)findViewById(R.id.category);
        name=(TextView)findViewById(R.id.name);
        location=(TextView)findViewById(R.id.location);
        username=(TextView)findViewById(R.id.username);
        password=(TextView)findViewById(R.id.password);
        shopid=(TextView)findViewById(R.id.shopid);
        phone=(TextView)findViewById(R.id.phone);
        progressBarRegister=(ProgressBar)findViewById(R.id.progressBarRegister);
        spinner.setAdapter(adapter);
        validator =new Validator();
    }

    public void register(View view) {

        if(name.getText().toString().equals("")||location.getText().toString().equals("")
                ||username.getText().toString().equals("")||password.getText().toString().equals("")
                ||shopid.getText().toString().equals("")||phone.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        Context c=MainActivity.this;
        if(!validator.isValidName(name.getText().toString(),c)||!validator.isValidAddress(location.getText().toString(),c)
                ||!validator.isValidUsername(username.getText().toString(),c)||!validator.isValidPassword(password.getText().toString(),c)
                ||!validator.isValidShopID(shopid.getText().toString(),c) ||spinner.getSelectedItem().toString()==""
                ||!validator.isValidPhoneNumber(phone.getText().toString(),c)){
            return;
        }

        checkExistingData(shopid.getText().toString());


    }

    public void checkExistingData(String shopid){
        progressBarRegister.setVisibility(View.VISIBLE);
        documentExists=false;
        mDBRef.collection("storedata").whereEqualTo("id",shopid)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds:queryDocumentSnapshots){
                    if(ds.exists()) {
                        progressBarRegister.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "The StoreID exists. Choose another!", Toast.LENGTH_SHORT).show();
                        Log.d("Document","does exists");
                        documentExists=true;
                    }
                }
                if(!documentExists){
                    Log.d("Document","does not exist");
                    storeData();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Database Failure",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void storeData(){
        progressBarRegister.setVisibility(View.VISIBLE);
        Map<String,Object> store=new HashMap<>();
        store.put("id",shopid.getText().toString());
        store.put("name",name.getText().toString());
        store.put("username",username.getText().toString());
        store.put("password",password.getText().toString());
        store.put("category",spinner.getSelectedItem().toString());
        store.put("address",location.getText().toString());
        store.put("phone",phone.getText().toString());
        store.put("latitude",my_lat);
        store.put("longitude",my_lon);
        store.put("rating",rating);
        store.put("opentime",opentime);
        store.put("closetime",closetime);
        store.put("delivery",delivery);
        store.put("currorderid",currorderid);

        mDBRef.collection("storedata").document(shopid.getText().toString())
                .set(store).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("X", "DocumentSnapshot added Successfully");
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                progressBarRegister.setVisibility(View.INVISIBLE);
                startActivity(i);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
