package com.example.mostafa.h;

/**
 * Created by Ayman on 08-Sep-15.
 */
public class User {

    private int id;
    private String name,phone,email,password;

    public User(int id,String name,String phone,String password,String email)
    {
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.phone=phone;
    }
    public User()
    {

    }
    public User(int id)
    {
        this.id=id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
