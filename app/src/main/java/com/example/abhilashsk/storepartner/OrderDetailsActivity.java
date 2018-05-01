package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    SharedPreferences smypref;
    String shopid;
    FirebaseFirestore mDB=FirebaseFirestore.getInstance();
    ArrayList<String> orderId,orderPhone,orderPrice;
    CustomList customList;
    ProgressBar progressBar;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        smypref=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        shopid=smypref.getString("id","");
        progressBar=(ProgressBar)findViewById(R.id.progressBarOrderDetails);

        orderId=new ArrayList<>();
        orderPhone=new ArrayList<>();
        orderPrice=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        mDB.collection("storedata").document(shopid).collection("orderdata")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot ds:queryDocumentSnapshots){
                    orderId.add(Integer.toString(ds.getDouble("id").intValue()));
                    orderPhone.add(ds.getString("phonenumber"));
                    orderPrice.add(Double.toString(ds.getDouble("payable")));
                }
                for(String x:orderPhone){
                    Log.d("Orderphone",x);
                }

                customList=new CustomList(OrderDetailsActivity.this,orderId,orderPhone,orderPrice);
                list=(ListView)findViewById(R.id.order_details);
                list.setAdapter(customList);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
