package com.example.mostafa.h;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import android.media.Image;
import java.io.File;
import java.io.IOException;


public class AddResturant extends ActionBarActivity implements View.OnClickListener {

    private int PICK_IMAGE_REQUEST=1;
    EditText name,phone,x,y,address;
    CheckBox like;
    ImageView image;
    Button save;
    int TAKE_PHOTO_CODE = 0;
    int count;
    SharedPreferences myPreferences;
    String dir;
    menudb menu=new menudb(this);
    String path;

    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resturant);

        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newDir = new File(dir);
        newDir.mkdirs();
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        name=(EditText)findViewById(R.id.editText);
        name.setOnClickListener(this);
        x=(EditText)findViewById(R.id.editText2);
        x.setOnClickListener(this);
        y=(EditText)findViewById(R.id.editText3);
        y.setOnClickListener(this);
        address=(EditText)findViewById(R.id.addreditText5);
        address.setOnClickListener(this);
        phone=(EditText)findViewById(R.id.editText4);
        phone.setOnClickListener(this);
        like=(CheckBox)findViewById(R.id.checkBox1);
        like.setOnClickListener(this);
        image=(ImageView)findViewById(R.id.imageView1);
        image.setOnClickListener(this);
        save=(Button)findViewById(R.id.savebutton);
        save.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            myPreferences.edit().putInt("count", count + 1).commit();
            image.setImageResource(0);
            image.setImageURI(Uri.fromFile(new File(path)));
        }
    }


    public  void showData()
    {
        count = myPreferences.getInt("count",0);
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
    public void onClick(View v) {

        if(v==image)
        {
          /*  AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Photo...");
            DialogInterface.OnClickListener gallery=new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    menu.getWritableDatabase();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
            };

            DialogInterface.OnClickListener camera = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showData();
                    if(checkContent(dir)==0)
                    {
                        myPreferences.edit().putInt("count", 0).commit();
                        count = 0;
                    }
                    path = dir + count + ".jpg";
                    File newFile = new File(path);
                    try {
                        newFile.createNewFile();
                    } catch (IOException e) {
                    }

                    Uri outputFileUri = Uri.fromFile(newFile);

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                    startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

                }
            };
            DialogInterface.OnClickListener google=new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.eg/imghp?hl=ar&tab=wi"));
                    startActivity(browserIntent);

                }
            };
            alertDialog.setPositiveButton("Google", google);
            alertDialog.setNegativeButton("Camera", camara);
            alertDialog.setNeutralButton("Gallery",gallery);

            AlertDialog alertDialogB = alertDialog.create();
            alertDialog.show();*/
            showData();
            if(checkContent(dir)==0)
            {
                myPreferences.edit().putInt("count",0).commit();
                count = 0;
            }
            path = dir + count + ".jpg";
            File newFile = new File(path);
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
            menu.getWritableDatabase();
            Rest rest=new Rest(0,name.getText().toString(),phone.getText().toString(),Double.parseDouble(String.valueOf(x.getText())),Double.parseDouble(String.valueOf(y.getText())),address.getText().toString(),(like.isChecked()),path);
            menu.addRest(rest);
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,menumainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,menumainActivity.class));
        finish();
    }
}
