package com.example.chatapp;

import android.media.Image;

public class MyContacts {
    private String contactImage;
    private String contactName;
    private Message msg;
    private String type;


    public MyContacts(){};
    public MyContacts(String Name,String Img,String type){
        this.contactName=Name;
        this.contactImage=Img;
        this.type=type;
    }



    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public String getContactImage() {
        return contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public String getType() {
        return type;
    }

    public Message getMsg() {
        return msg;
    }
}
