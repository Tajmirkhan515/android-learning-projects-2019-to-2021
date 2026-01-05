package com.dinosoftlabs.qrandbarcodescanner.History;

/**
 * Created by AQEEL on 8/17/2018.
 */

public class History_GetSet {

    String type,data,date;
    byte[] image;
    public History_GetSet() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
