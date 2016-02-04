package com.example.mostafa.h;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class UpdateActivity extends ActionBarActivity implements View.OnClickListener{
    private int PICK_IMAGE_REQUEST=1;
    EditText name,phone,x,y,address;
    CheckBox like;
    ImageView image;
    Button save, clear;
    String picturePath;
    int TAKE_PHOTO_CODE = 0;
    SharedPreferences myPreferences;
    int count;
    Rest rest;
    menudb menu=new menudb(this);
    String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newDir = new File(dir);
        newDir.mkdirs();
        name=(EditText)findViewById(R.id.editText6);
        name.setOnClickListener(this);
        x=(EditText)findViewById(R.id.xidid);
        x.setOnClickListener(this);
        y=(EditText)findViewById(R.id.yidid);
        y.setOnClickListener(this);
        phone=(EditText)findViewById(R.id.pidid);
        phone.setOnClickListener(this);
        like=(CheckBox)findViewById(R.id.checkBox);
        like.setOnClickListener(this);
        image=(ImageView)findViewById(R.id.imageView);
        image.setOnClickListener(this);
        save=(Button)findViewById(R.id.savbutton);
        save.setOnClickListener(this);
        clear=(Button)findViewById(R.id.clearbutton);
        clear.setOnClickListener(this);
        address=(EditText)findViewById(R.id.editText5);
        address.setOnClickListener(this);
        Intent came = getIntent();
        rest = menu.getRestByID(Integer.parseInt(came.getStringExtra("resID")));
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(rest.getName());
        x.setText(String.valueOf(rest.getX()));
        y.setText(String.valueOf(rest.getY()));
        phone.setText(rest.getPhone());
        like.setChecked(false);

        address.setText(rest.getAddress());
        image.setImageURI(Uri.fromFile(new File(rest.getImage())));

        picturePath="";


    }

    @Override
    public void onClick(View v) {

         if(v==image)
        {
           /* Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);*/
            showData();
            picturePath = dir + count + ".jpg";
            File newFile = new File(picturePath);
            try {
                newFile.createNewFile();
            } catch (IOException e) {
            }

            Uri outputFileUri = Uri.fromFile(newFile);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);


        }
        else if(v==save)
         {
             rest.setName(name.getText().toString());
             rest.setX(Double.parseDouble(x.getText().toString()));
             rest.setY(Double.parseDouble(y.getText().toString()));
             rest.setAddress(address.getText().toString());
             rest.setPhone(phone.getText().toString());
             rest.setLike(like.isChecked());
             if(!picturePath.equals("")) rest.setImage(picturePath);
             else rest.setImage(rest.getImage());
             menu.updateRestByID(rest);
             Intent gotorest=new Intent(this,ResDetailsAtivity.class);
             gotorest.putExtra("resID", String.valueOf(rest.getId()));
             startActivity(gotorest);
             finish();
         }
        else if(v==clear)
         {

             menu.deleteRest(rest.getId());
             startActivity(new Intent(this,menumainActivity.class));
             finish();
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      /*  super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            picturePath = getRealPathFromURI(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
             Log.d("CameraDemo", "Pic saved");
       //     myPreferences.edit().putInt("count", count + 1).commit();
            image.setImageResource(0);
            image.setImageURI(Uri.fromFile(new File(picturePath)));
            Toast.makeText(this,String.valueOf(getImageOrientation()),Toast.LENGTH_LONG).show();

        }
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
    public  void showData()
    {
        count = myPreferences.getInt("count",0);
    }
    public String getRealPathFromURI(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public int checkContent(String dir)
    {
        File file=new File(dir);
        File[] list = file.listFiles();
        int c = 0;
        for (File f: list) {
            String name = f.getName();
            if (name.endsWith(".jpg"))
                c++;
        }
        return c;
    }
    @Override
    public void onBackPressed() {

        Intent gotorest=new Intent(this,ResDetailsAtivity.class);
        gotorest.putExtra("resID", String.valueOf(rest.getId()));
        startActivity(gotorest);
        finish();
    }

}



