package com.example.mostafa.h;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ayman on 08-Sep-15.
 */
public class menudb extends SQLiteOpenHelper {

    public menudb(Context context) {
        super(context, "menu", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE Users ( ID INTEGER PRIMARY KEY autoincrement,Name TEXT not null,Phone TEXT,Password TEXT not null,Email Text)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_RESTS_TABLE = "CREATE TABLE Rests ( ID INTEGER PRIMARY KEY autoincrement,Name TEXT not null,Phone TEXT not null,X TEXT not null,Y TEXT not null,Address TEXT not null,Like BOOLEAN not null,Image TEXT not null)";
        db.execSQL(CREATE_RESTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Rests");
        // Create tables again
        onCreate(db);
    }
    public void addUser(User us)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",us.getName());
        values.put("Phone",us.getPhone());
        values.put("Password",us.getPassword());
        values.put("Email", us.getEmail());
        db.insert("Users", null, values);
        db.close();
    }
    public void addRest(Rest rs)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",rs.getName());
        values.put("Phone",rs.getPhone());
        values.put("X",rs.getX());
        values.put("Y",rs.getY());
        values.put("Address",rs.getAddress());
        values.put("Like",rs.getLike());
        values.put("Image",rs.getImage());
        db.insert("Rests", null, values);
        db.close();
    }

    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Users";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            //ID INTEGER PRIMARY KEY autoincrement,Name TEXT not null,Phone TEXT,Password TEXT not null,Email Text)";
            do
            {
                int id = cursor.getInt(0);
                String oneName = cursor.getString(1);
                String phone = cursor.getString(2);
                String password = cursor.getString(3);
                String email = cursor.getString(4);

                User oneUser = new User(id,oneName,phone,password,email);

                userList.add(oneUser);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return userList;
    }

    public ArrayList<Rest> getAllRests()
    {
        ArrayList<Rest> restList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Rests";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            //ID INTEGER PRIMARY KEY autoincrement,Name TEXT not null,Phone TEXT not null,X TEXT not null,Y TEXT not null,Like BOOLEAN not null,Image TEXT not null
            do
            {
                int id = cursor.getInt(0);
                String oneName = cursor.getString(1);
                String phone = cursor.getString(2);
                String x = cursor.getString(3);
                String y = cursor.getString(4);
                String add=cursor.getString(5);
                Boolean like = cursor.getInt(6)>0;
                String img = cursor.getString(7);

                Rest oneRest = new Rest(id,oneName,phone,Double.valueOf(x),Double.valueOf(y),add,like,img);

                restList.add(oneRest);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return restList;
    }

    public User getUserByName(String userName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Users", new String[]{"ID", "Phone","Password", "Email"},
                "Name=?", new String[]{String.valueOf(userName)}
                , null, null, null, null);

        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();

            int retID = cursor.getInt(0);
            String retPhone = cursor.getString(1);
            String retPassword = cursor.getString(2);
            String retEmail = cursor.getString(3);
            //  public User(int id,String name,String phone,String password,String email)
            User myUser = new User(retID, userName, retPhone, retPassword, retEmail);
            return myUser;
        }
        return new User(-1);
    }

    public User getUserByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Users", new String[]{"Name", "Phone","Password", "Email"},
                "ID=?", new String[]{String.valueOf(id)}
                , null, null, null, null);

        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();

            String userName = cursor.getString(0);
            String retPhone = cursor.getString(1);
            String retPassword = cursor.getString(2);
            String retEmail = cursor.getString(3);
            //  public User(int id,String name,String phone,String password,String email)
            User myUser = new User(id, userName, retPhone, retPassword, retEmail);
            return myUser;
        }
        return new User(-1);
    }


    public Rest getRestByID(int restID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Rests", new String[]{"Name", "Phone","X","Y","Address","Like", "Image"},
                "ID=?", new String[]{String.valueOf(restID)}
                , null, null, null, null);

        //public Rest(int id,String name,String phone, double x,double y ,Boolean like,String image)
        // "CREATE TABLE Rests ( ID INTEGER PRIMARY KEY autoincrement,Name TEXT not null,Phone TEXT not null,X TEXT not null,Y TEXT not null,Like BOOLEAN not null,Image TEXT not null)
        if (cursor != null)
            cursor.moveToFirst();

        String retName = cursor.getString(0);
        String retPhone = cursor.getString(1);
        String retX = cursor.getString(2);
        String retY = cursor.getString(3);
        String retAdd=cursor.getString(4);
        Boolean retLike = cursor.getInt(5)>0;
        String retImg = cursor.getString(6);

        Rest myRest=new Rest(restID,retName,retPhone,Double.valueOf(retX),Double.valueOf(retY),retAdd,retLike,retImg);
        Rest oneCont =new Rest();
        return myRest;
    }


    public int updateRestByID(Rest oneRest)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", oneRest.getName());
        values.put("Phone",oneRest.getPhone());
        values.put("X",oneRest.getX());
        values.put("Y",oneRest.getY());
        values.put("Address",oneRest.getAddress());
        values.put("Like",oneRest.getLike());
        values.put("Image",oneRest.getImage());


        int updateItems = db.update("Rests", values,"ID = ?", new String[] {String.valueOf(oneRest.getId())} );
        db.close();
        return updateItems;

    }
    public int updateUserByID(User oneUser)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name",oneUser.getName());
        values.put("Phone",oneUser.getPhone());
        values.put("Password",oneUser.getPassword());
        values.put("Email", oneUser.getEmail());

        int updateItems = db.update("Users", values,"ID = ?", new String[] {String.valueOf(oneUser.getId())} );
        db.close();
        return updateItems;

    }

    public void deleteUser(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("Users", "ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteRest(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("Rests","ID = ?",new String[]{String.valueOf(id)});
        db.close();
    }



}
