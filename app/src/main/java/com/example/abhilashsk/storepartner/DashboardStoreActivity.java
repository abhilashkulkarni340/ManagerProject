package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DashboardStoreActivity extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    TextView dname,dphone,dusername,dcategory,dlocation;
    String shopid,name,username,phone,category,location;
    SharedPreferences mypref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_store);

        mypref=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        dname=(TextView)findViewById(R.id.display_name);
        dusername=(TextView)findViewById(R.id.display_username);
        dcategory=(TextView)findViewById(R.id.display_category);
        dlocation=(TextView)findViewById(R.id.display_location);
        dphone=(TextView)findViewById(R.id.display_phone);
        shopid=mypref.getString("id","");
        checkSession();
        Log.d("shopid",shopid);
        db.collection("storedata").whereEqualTo("id",shopid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot ds:queryDocumentSnapshots){
                            name=ds.getString("name");
                            username=ds.getString("username");
                            category=ds.getString("category");
                            location=ds.getString("address");
                            phone=ds.getString("phone");
                        }
                        dname.setText(name);
                        dusername.setText(username);
                        dcategory.setText(category);
                        dlocation.setText(location);
                        dphone.setText(phone);
                    }
                });
    }

    public void backpress(View view) {
        SharedPreferences.Editor editor=mypref.edit();
        editor.clear();
        editor.apply();
        Intent i=new Intent(this,LoginActivity.class);
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
}
