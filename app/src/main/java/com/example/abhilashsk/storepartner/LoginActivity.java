package com.example.abhilashsk.storepartner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore mDBRef=FirebaseFirestore.getInstance();
    EditText username,password;
    String user,pass,pass2,shopid;
    SharedPreferences mypref;
    ProgressBar progressBarLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mypref=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        checkLogin();
    }

    public void loginFunction(View view) {
        initViews();
        user=username.getText().toString();
        pass=password.getText().toString();
        if(user.equals("")||pass.equals("")){
            return;
        }

        mDBRef.collection("storedata").whereEqualTo("username",user)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot ds:queryDocumentSnapshots){
                    pass2=ds.getString("password");
                    shopid=ds.getString("id");
                }
                Log.d("passwords",pass+"  "+pass2);
                if(pass.equals(pass2)){
                    SharedPreferences.Editor editor=mypref.edit();
                    editor.putString("username",user);
                    editor.putString("password",pass);
                    editor.putString("id",shopid);
                    editor.apply();
                    Intent i=new Intent(LoginActivity.this,DashboardStoreActivity.class);
                    i.putExtra("shopid",shopid);
                    startActivity(i);
                    progressBarLogin.setVisibility(View.INVISIBLE);
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Failed to fetch data from the database!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initViews(){
        username=(EditText)findViewById(R.id.username_login);
        password=(EditText)findViewById(R.id.password_login);
        progressBarLogin=(ProgressBar)findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    public void registerFunction(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void checkLogin(){
        if(mypref.getString("username","").equals("")&&mypref.getString("password","").equals("")){
            Toast.makeText(this,"Login First!",Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent i=new Intent(LoginActivity.this,DashboardStoreActivity.class);
            i.putExtra("shopid",shopid);
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
