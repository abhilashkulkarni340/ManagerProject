package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DashboardStoreActivity extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    EditText dname,dphone,dlocation,dopentime,dclosetime;
    TextView dshopid,drating,ddelivery,dcategory;
    Spinner cdelivery,ccategory;
    ProgressBar progressBarDash;
    String shopid,name,phone,category,location,opentime,closetime,delivery;
    Double rating;
    SharedPreferences mypref;
    String[] delivery_options={"YES","NO"};
    String[] cat={"Food","Groceries","Restaurant","Pharmacy","SuperMarket","Fashion","Electronics"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardstore2);

        mypref=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        shopid=mypref.getString("id","");


        initViews();
        removeFocus();
        checkSession();

        progressBarDash.setVisibility(View.VISIBLE);


        Log.d("shopid",shopid);
        db.collection("storedata").whereEqualTo("id",shopid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot ds:queryDocumentSnapshots){
                            if(ds.exists()) {
                                name = ds.getString("name");
                                //username=ds.getString("username");
                                category = ds.getString("category");
                                location = ds.getString("address");
                                phone = ds.getString("phone");
                                shopid = ds.getString("id");
                                rating = ds.getDouble("rating");
                                opentime = ds.getString("opentime");
                                closetime = ds.getString("closetime");
                                delivery = ds.getString("delivery");
                            }
                        }
                        dname.setText(name);
                        dcategory.setText(category);
                        dlocation.setText(location);
                        dphone.setText(phone);
                        dshopid.setText(shopid);
                        drating.setText(Double.toString(rating));
                        dopentime.setText(opentime);
                        dclosetime.setText(closetime);
                        ddelivery.setText(delivery);
                        progressBarDash.setVisibility(View.INVISIBLE);
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DashboardStoreActivity.this,"Failed to retrieve data!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backpress(View view) {
        Intent i=new Intent(this,DashboardActivity.class);
        startActivity(i);
    }

    public void checkSession(){
        if(mypref.getString("username","").equals("")||mypref.getString("password","").equals("")){
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed(){
        Log.d("LOGIN","back key pressed");
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void initViews(){
        dname=(EditText) findViewById(R.id.display_name);
        dcategory=(TextView)findViewById(R.id.display_category);
        dlocation=(EditText) findViewById(R.id.display_address);
        dphone=(EditText) findViewById(R.id.display_phone);
        dshopid=(TextView)findViewById(R.id.display_shopid);
        drating=(TextView)findViewById(R.id.display_rating);
        dopentime=(EditText)findViewById(R.id.display_opentime);
        dclosetime=(EditText)findViewById(R.id.display_closetime);
        ddelivery=(TextView) findViewById(R.id.display_delivery);
        cdelivery=(Spinner)findViewById(R.id.change_delivery);
        ccategory=(Spinner)findViewById(R.id.change_category);
        progressBarDash=(ProgressBar)findViewById(R.id.progressBarDash);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                delivery_options);
        cdelivery.setAdapter(adapter);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                cat);
        ccategory.setAdapter(adapter);
    }

    public void removeFocus(){
        dname.setFocusable(false);
        dname.setFocusableInTouchMode(false);
        dlocation.setFocusable(false);
        dlocation.setFocusableInTouchMode(false);
        dphone.setFocusable(false);
        dphone.setFocusableInTouchMode(false);
        dopentime.setFocusable(false);
        dopentime.setFocusableInTouchMode(false);
        dclosetime.setFocusable(false);
        dclosetime.setFocusableInTouchMode(false);

        cdelivery.setVisibility(View.INVISIBLE);
        ccategory.setVisibility(View.INVISIBLE);
    }

    public void editInfoFunction(View view) {
        dname.findFocus();
        dname.setFocusable(true);
        dname.setFocusableInTouchMode(true);
        dlocation.setFocusable(true);
        dlocation.setFocusableInTouchMode(true);
        dphone.setFocusable(true);
        dphone.setFocusableInTouchMode(true);
        dopentime.setFocusable(true);
        dopentime.setFocusableInTouchMode(true);
        dclosetime.setFocusable(true);
        dclosetime.setFocusableInTouchMode(true);

        cdelivery.setVisibility(View.VISIBLE);
        ccategory.setVisibility(View.VISIBLE);
    }

    public void saveInfoFunction(View view) {
        progressBarDash.setVisibility(View.VISIBLE);
        DocumentReference storedata=db.collection("storedata").document(shopid);
        dcategory.setText(ccategory.getSelectedItem().toString());
        ddelivery.setText(cdelivery.getSelectedItem().toString());
        storedata.update("name",dname.getText().toString());
        storedata.update("address",dlocation.getText().toString());
        storedata.update("category",ccategory.getSelectedItem().toString());
        storedata.update("phone",dphone.getText().toString());
        storedata.update("opentime",dopentime.getText().toString());
        storedata.update("closetime",dclosetime.getText().toString());
        storedata.update("delivery",cdelivery.getSelectedItem().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DashboardStoreActivity.this,"Update successful",Toast.LENGTH_SHORT).show();
                        removeFocus();
                        progressBarDash.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DashboardStoreActivity.this,"Failed to update data. Try Again!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
