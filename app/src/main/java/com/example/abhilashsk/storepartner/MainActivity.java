package com.example.abhilashsk.storepartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String[] cat={"Food","Groceries","Restaurant","Pharmacy","SuperMarket","Fashion","Electronics"};
    TextView name,location,phone,username,password,shopid;
    Spinner spinner;
    ArrayAdapter adapter;
    Double my_lat,my_lon;
    FirebaseFirestore mDBRef=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        spinner.setAdapter(adapter);

        Tracer gps_for_filter = new Tracer(MainActivity.this);
        if (gps_for_filter.getLocation() != null) {
            my_lat = gps_for_filter.getLatitude();
            my_lon = gps_for_filter.getLongitude();
            Toast.makeText(this,"Your location is: "+ my_lat + " " + my_lon,Toast.LENGTH_LONG).show();;
            Log.d("onCreate MarkersMap", "Your location is " + my_lat + " " + my_lon);
        } else {
            //gps.showSettingAlert();
            my_lat = 12.942898;
            my_lon = 77.56819659999996;
            Toast.makeText(this,"Your location cannot be found",Toast.LENGTH_LONG).show();;
            Log.d("onCreate MarkersMap", "Your location cannot be found");
        }
    }

    public void register(View view) {
        if(name.getText()==""||location.getText()==""||username.getText()==""||password.getText()==""||shopid.getText()==""||spinner.getSelectedItem().toString()==""||phone.getText().toString()==""){
            return;
        }
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

        mDBRef.collection("storedata").document(shopid.getText().toString())
        .set(store).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("X", "DocumentSnapshot added Successfully");
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
