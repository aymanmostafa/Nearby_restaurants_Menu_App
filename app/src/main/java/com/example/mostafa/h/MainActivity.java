package com.example.mostafa.h;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sec,secc;
    menudb my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sec=PreferenceManager.getDefaultSharedPreferences(this);
        secc=PreferenceManager.getDefaultSharedPreferences(this);
        my=new menudb(this);
        if(secc.getBoolean("hamoo",true)){
            my.addRest(new Rest(1,"Macdonalds","01000435774",29.9542417,31.2608065,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Spectra","01000435774",29.9601561,31.2569137,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Roastery","01000435774",29.9563851,31.2703984,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Asian Corner","01000435774",29.9582298,31.2690908,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Steak Out","01000435774",29.9542417,31.2608065,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Bistro","01000435774",29.9582298,31.3357061,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Pizza Hut","01000435774",30.094973,31.3357061,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Shawerma El Reem","01000435774",30.1064342,31.3438858,"Madi",false,"android.mipmap.ic_launcher"));
            my.addRest(new Rest(1,"Burger King","01000435774",30.1064342,31.3438858,"Madi",false,"android.mipmap.ic_launcher"));
            secc.edit().putBoolean("hamoo", false).apply();
        }
        if(sec.getBoolean("sec",false))

        {
            Intent gotomain = new Intent(this, menumainActivity.class);
            startActivity(gotomain);
            finish();
        }

        else

        {
            Intent gotolog = new Intent(this, menulogActivity.class);
            startActivity(gotolog);
            finish();
        }
    }



}
