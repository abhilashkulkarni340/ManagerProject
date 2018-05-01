package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {
    SharedPreferences mypref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mypref=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
    }

    public void logoutPressed(View view){
        SharedPreferences.Editor editor=mypref.edit();
        editor.clear();
        editor.apply();
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    public void detailsPressed(View view) {
        Intent i=new Intent(this,DashboardStoreActivity.class);
        startActivity(i);
    }

    public void ordersPressed(View view) {
        Intent i=new Intent(this,OrderDetailsActivity.class);
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
        if(mypref.getString("username","").equals("")||mypref.getString("password","").equals("")){
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
        }else{
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}
