package com.example.mostafa.h;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Collections;

public class menumainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView list;
    ArrayAdapter ada;
    ArrayList<Rest> arr;
    menudb my;
    TabHost tabs;
    Button add, map, search, lang, edit, log, about;
    SharedPreferences sec;
    EditText se;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menumain);

        tabs = (TabHost) findViewById(R.id.tabHost2);
        tabs.setup();

        TabHost.TabSpec spec1 = tabs.newTabSpec("Tab1");
        spec1.setContent(R.id.tab11);
        spec1.setIndicator("Restaurants");
        tabs.addTab(spec1);

        TabHost.TabSpec spec2 = tabs.newTabSpec("Tab2");
        spec2.setContent(R.id.tab12);
        spec2.setIndicator("Options");
        tabs.addTab(spec2);


        list = (ListView) findViewById(R.id.listViewidid);
        my = new menudb(this);
        arr = my.getAllRests();
        se = (EditText) findViewById(R.id.searchidid);
        list.setOnItemClickListener(this);


        for (int i = 0; i < arr.size(); i++) {
            for (int k = 0; k < arr.size(); k++) {
                if (arr.get(i).getName().toLowerCase().compareTo(arr.get(k).getName().toLowerCase()) < 0)
                    Collections.swap(arr, i, k);
            }
        }


        ada = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
        list.setAdapter(ada);
        se.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                menumainActivity.this.ada.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add = (Button) findViewById(R.id.addresmenutid);
        add.setOnClickListener(this);

        map = (Button) findViewById(R.id.mapmenuid);
        map.setOnClickListener(this);

        search = (Button) findViewById(R.id.searchmenuid);
        search.setOnClickListener(this);


        edit = (Button) findViewById(R.id.editmenuid);
        edit.setOnClickListener(this);

        log = (Button) findViewById(R.id.logmenuid);
        log.setOnClickListener(this);

        about = (Button) findViewById(R.id.aboutmenuid);
        about.setOnClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Rest t = (Rest) parent.getAdapter().getItem(position);
        Intent gotorest=new Intent(this,ResDetailsAtivity.class);
        Integer x=t.getId();
        gotorest.putExtra("resID",x.toString()); //sending (ID) of resturant to next activity with key "res"
        startActivity(gotorest);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v == add) {
            startActivity(new Intent(this, AddResturant.class));
            finish();

        } else if (v == map) {
            startActivity(new Intent(this, MapsActivity.class));
        } else if (v == search) {
            startActivity(new Intent(this, NearbyRestaurant.class));

        }  else if (v == edit) {
            startActivity(new Intent(this, menuedituserActivity.class));

        } else if (v == log) {
            sec = PreferenceManager.getDefaultSharedPreferences(this);
            sec.edit().putBoolean("sec", false).commit();
            Intent gotoreg = new Intent(this, MainActivity.class);
            gotoreg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(gotoreg);
            finish();

        } else if (v == about) {
            startActivity(new Intent(this, menuaboutActivity.class));
        }
    }
}
