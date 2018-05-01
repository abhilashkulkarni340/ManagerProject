package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class OrderActivity extends AppCompatActivity {
    String orderid,storeid,userid,name;
    ArrayList<String> itemname,itemquantity;
    ArrayAdapter<String> adapter1,adapter2;
    SharedPreferences smypref;
    ListView listView1,listView2;
    TextView tvname,tvphone;
    ProgressBar progressBar;
    FirebaseFirestore mDB=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderid=getIntent().getStringExtra("OrderId");
        userid=getIntent().getStringExtra("userId");
        smypref=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        storeid=smypref.getString("id","");
        itemname=new ArrayList<>();
        itemquantity=new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.progressBar11);
        progressBar.setVisibility(View.VISIBLE);
        mDB.collection("storedata").document(storeid).collection("orderdata")
                .document(orderid).collection("orderitems").document("ITEMS")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("Data",""+documentSnapshot.getData());
                Map<String,Object> dict=documentSnapshot.getData();
                Set keys=dict.keySet();
                Collection<Object> value=dict.values();
                Log.d("data2",keys.toString()+" "+value.toString());
                for(Object x :value){
                    itemquantity.add(x.toString());
                    Log.d("val",x.toString());
                }
                for(Object x:keys){
                    itemname.add(x.toString());
                    Log.d("key",x.toString());
                }
                addUserDetails();
            }
        });

    }

    public void addUserDetails(){
        mDB.collection("userdata").document(userid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name=documentSnapshot.getString("name");
                addToList();
            }
        });
    }

    public void addToList(){
        tvname=(TextView)findViewById(R.id.customer_name);
        tvphone=(TextView)findViewById(R.id.customer_phonenumber);
        listView1=(ListView)findViewById(R.id.order_list);
        listView2=(ListView)findViewById(R.id.order_quantity);
        adapter1=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,itemname);
        adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,itemquantity);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        tvname.setText(name);
        tvphone.setText(userid);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void orderCompletedPressed(View view) {
        progressBar.setVisibility(View.VISIBLE);
        mDB.collection("storedata").document(storeid)
                .collection("orderdata").document(orderid)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Delete successful",orderid);
                Intent intent=new Intent(OrderActivity.this,OrderDetailsActivity.class);
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);
            }
        });
    }
}
