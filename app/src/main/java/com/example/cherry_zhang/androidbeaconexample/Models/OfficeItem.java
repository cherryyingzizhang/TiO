package com.example.cherry_zhang.androidbeaconexample.Models;

import android.graphics.Bitmap;

/**
 * Created by Cherry_Zhang on 2014-09-20.
 */
public class OfficeItem
{
    private Bitmap officeImage;
    private String title;
    private String address;
    private String company;
    private String price;

    public OfficeItem(Bitmap officeImage, String title, String address, String company, String price)
    {
        this.officeImage = officeImage;
        this.title = title;
        this.address = address;
        this.company = company;
        this.price = price;
    }

    public void setOfficeImage(Bitmap officeImage)
    {
        this.officeImage = officeImage;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAddress()
    {
        this.address = address;
    }

    public void setCompany()
    {
        this.company = company;
    }

    public void setPrice()
    {
        this.price = price;
    }


    public Bitmap getOfficeImage() {
        return officeImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
