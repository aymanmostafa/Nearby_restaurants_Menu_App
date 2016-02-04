package com.example.mostafa.h;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class menuaboutActivity extends AppCompatActivity {

    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuabout);
        t=(TextView) findViewById(R.id.aboutidid);
        t.setText("Developed by\nH++ team\nAhmed Mohamed\nAyman Mostafa\nEslam Kamal\nMostafa Shafy");
    }

}
