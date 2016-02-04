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
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Pattern;

public class menuedituserActivity extends AppCompatActivity implements View.OnClickListener {

    Button save,cancel;
    TextView user;
    EditText pass,phone,email;
    SharedPreferences secid;
    User test;
    menudb my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuedituser);

        secid= PreferenceManager.getDefaultSharedPreferences(this);
        save= (Button) findViewById(R.id.saveedituserid);
        save.setOnClickListener(this);

//        sec.edit().putBoolean("sec", true).commit();
        // sec.edit().putInt("secid", test.getId()).commit();
        my=new menudb(this);
        user=(TextView) findViewById(R.id.useredituserid);
        pass=(EditText) findViewById(R.id.passedituserid);
        email=(EditText) findViewById(R.id.mailedituserid);
        phone=(EditText) findViewById(R.id.phoneedituserid);
        cancel= (Button) findViewById(R.id.canceledituserid);
        cancel.setOnClickListener(this);
        test=my.getUserByName(secid.getString("secid","a"));
        user.setText("  "+test.getName());
        pass.setText(test.getPassword());
        phone.setText(test.getPhone());
       email.setText(test.getEmail());
    }

    @Override
    public void onClick(View v) {
        boolean arr[]=new boolean[3];
        boolean k=false;
        Arrays.fill(arr, false);
        if(v==save){
            String pas=pass.getText().toString();
            String mail=email.getText().toString();
            String tel=phone.getText().toString();
            my=new menudb(this);
            if(!Pattern.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", pas)){
                arr[0]=true;
                k=true;
            }
            if((!mail.equals(""))&&(!Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", mail))){
                arr[1]=true;
                k=true;
            }
            if((!tel.equals(""))&&(!Pattern.matches("[0]{1}[1]{1}[012]{1}[0-9]{8}", tel))){
                arr[2]=true;
                k=true;
            }
            if(k){
                String s="";
                if(arr[0]) s+="Enter valid password\n";
                if(arr[1]) s+="Enter valid Email\n";
                if(arr[2]) s+="Enter vaild phone\n";
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
                test.setEmail(mail);
                test.setPassword(pas);
                test.setPhone(tel);
                test.setId(test.getId());
                my.updateUserByID(test);
                onBackPressed();
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
