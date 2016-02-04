package com.example.mostafa.h;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.regex.Pattern;

public class menuregActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,pass,email,phone;
    Button save,cancel;
    menudb my;
    SharedPreferences sec,secid;
    boolean k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menureg);

        name=(EditText) findViewById(R.id.userregid);
        pass=(EditText) findViewById(R.id.passregid);
        email=(EditText) findViewById(R.id.mailregid);
        phone=(EditText) findViewById(R.id.phoneregid);

        save=(Button) findViewById(R.id.saveregid);
        save.setOnClickListener(this);

        cancel=(Button) findViewById(R.id.cancelregid);
        cancel.setOnClickListener(this);

        sec= PreferenceManager.getDefaultSharedPreferences(this);
        secid=PreferenceManager.getDefaultSharedPreferences(this);
    }


    @Override
    public void onClick(View v) {
        boolean arr[]=new boolean[5];
        boolean k=false;
        Arrays.fill(arr, false);
        if(v==save){
            String user=name.getText().toString();
            String pas=pass.getText().toString();
            String mail=email.getText().toString();
            String tel=phone.getText().toString();
            my=new menudb(this);
            if(!Pattern.matches("^[a-z0-9_-]{3,15}$",user)){
                arr[0]=true;
                k=true;
            }
            else if(my.getUserByName(user).getId() >= 0){
                arr[1]=true;
                k=true;
            }
            if(!Pattern.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", pas)){
                arr[2]=true;
                k=true;
            }
            if((!mail.equals(""))&&(!Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", mail))){
                arr[3]=true;
                k=true;
            }
            if((!tel.equals(""))&&(!Pattern.matches("[0]{1}[1]{1}[012]{1}[0-9]{8}", tel))){
                arr[4]=true;
                k=true;
            }
            if(k){
                String s="";
                if(arr[0]) s+="Enter valid username\n";
                if(arr[1]) s+="Username exists before\n";
                if(arr[2]) s+="Enter valid password\n";
                if(arr[3]) s+="Enter valid Email\n";
                if(arr[4]) s+="Enter vaild phone\n";
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("");
                alertBuilder.setMessage(s);
                DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
                alertBuilder.setPositiveButton("OK", positiveListener);
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
            }
            else{
                User t = new User(0, user, tel, pas, mail);
                my.addUser(t);
                sec.edit().putBoolean("sec", true).apply();
                secid.edit().putString("secid",t.getName()).apply();
                Intent gotoback=new Intent(this,MainActivity.class);
                gotoback.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotoback);
                finish();


            }
        }
        else if(v==cancel){
            onBackPressed();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
