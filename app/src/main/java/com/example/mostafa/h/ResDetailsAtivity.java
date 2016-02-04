package com.example.mostafa.h;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class ResDetailsAtivity extends ActionBarActivity {
    TextView nameTV,phoneTV,addTV,isLikedTV;
    ImageView image;
    String s;
    menudb myDB;
    Rest res;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_details_ativity);

        myDB = new menudb(this);
        nameTV = (TextView)findViewById(R.id.nameStr_textView);
        phoneTV = (TextView)findViewById(R.id.phoneStr_textView);
        addTV = (TextView)findViewById(R.id.addStr_textView);
        isLikedTV = (TextView)findViewById(R.id.isLikedStr_textView);
        image = (ImageView)findViewById(R.id.imageView);
        Intent came = getIntent();
        s = came.getStringExtra("resID");
        res = myDB.getRestByID(Integer.parseInt(s));
        nameTV.setText(" "+res.getName());
        phoneTV.setText(" "+res.getPhone());
        addTV.setText("   "+res.getAddress());
        if (res.getLike())
        {
            isLikedTV.setText("Liked");
        }
        else
        {
            isLikedTV.setText("Disliked");
        }
        int x = getImageOrientation();
       image.setImageURI(Uri.fromFile(new File(res.getImage())));
        Toast.makeText(this,String.valueOf(x),Toast.LENGTH_LONG).show();
    }

    private int getImageOrientation(){
        final String[] imageColumns = { MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION };
        final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageColumns, null, null, imageOrderBy);

        if(cursor.moveToFirst()){
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            cursor.close();
            return orientation;
        } else {
            return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_me)
        {
            Intent goToUpdate = new Intent(this,UpdateActivity.class);
            goToUpdate.putExtra("resID", s);
            startActivity(goToUpdate);
            finish();
        }
        else if(item.getItemId()==R.id.action_settings)
        {
            Toast.makeText(getBaseContext(),"Settings",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,menumainActivity.class));
        finish();
    }

}
