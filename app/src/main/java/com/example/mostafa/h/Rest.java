package com.example.mostafa.h;

/**
 * Created by Ayman on 08-Sep-15.
 */
public class Rest {
    private int id;
    private double x, y;
    private String name,phone,image,add;
    private Boolean like;

    public Rest(int id,String name,String phone, double x,double y,String add ,Boolean like,String image)
    {
        this.id=id;
        this.x=x;
        this.y=y;
        this.name=name;
        this.phone=phone;
        this.image=image;
        this.like=like;
        this.add=add;
    }
    public Rest()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return add;
    }

    public void setAddress(String add) {
        this.add = add;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public String toString (){
        return this.name;
    }
}
