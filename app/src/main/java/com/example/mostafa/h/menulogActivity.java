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
import android.widget.Toast;

public class menulogActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user,pass;
    Button log,reg;
    menudb my;
    SharedPreferences sec,secid;
    TextView warn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulog);

        user=(EditText) findViewById(R.id.userid);
        pass=(EditText) findViewById(R.id.passid);

        log=(Button) findViewById(R.id.logid);
        log.setOnClickListener(this);

        reg=(Button) findViewById(R.id.regid);
        reg.setOnClickListener(this);

        warn=(TextView) findViewById(R.id.warnid);

        sec= PreferenceManager.getDefaultSharedPreferences(this);
        secid=PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onClick(View v) {
        my=new menudb(this);
        if(v==log){
            String u=user.getText().toString();
            String p=pass.getText().toString();

            if(u.equals("")&&p.equals("")){
                user.setHint("Enter Valid username");
                pass.setHint("Enter Valid password");
            }
            else if(u.equals("")){
                user.setHint("Enter Valid username");
            }
            else if(p.equals("")){
                pass.setHint("Enter Valid password");
            }
            else {
                User test=my.getUserByName(u);
                if(test.getId()>=0){
                    if(test.getPassword().equals(p)) {
                        sec.edit().putBoolean("sec", true).apply();
                        secid.edit().putString("secid", test.getName()).apply();
                        Intent gotomain = new Intent(this, menumainActivity.class);
                        startActivity(gotomain);
                        finish();
                    }
                    else{
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

                        alertBuilder.setTitle("");
                        alertBuilder.setMessage("Username and/or password are wrong");

                        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        };

                        alertBuilder.setPositiveButton("OK", positiveListener);

                        AlertDialog alertDialog = alertBuilder.create();
                        alertDialog.show();
                    }
                }
                else{
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

                    alertBuilder.setTitle("");
                    alertBuilder.setMessage("Username and/or password are wrong");

                    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    };

                    alertBuilder.setPositiveButton("OK", positiveListener);

                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                }
            }
        }
        else if(v==reg){
            Intent gotoreg=new Intent(this,menuregActivity.class);
            startActivity(gotoreg);
        }
    }
}
