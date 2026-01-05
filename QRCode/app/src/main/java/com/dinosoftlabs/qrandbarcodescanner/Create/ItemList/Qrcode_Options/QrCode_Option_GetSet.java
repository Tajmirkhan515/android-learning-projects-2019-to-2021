package com.dinosoftlabs.qrandbarcodescanner.Create.ItemList.Qrcode_Options;

import android.graphics.drawable.Drawable;

/**
 * Created by AQEEL on 8/27/2018.
 */

public class QrCode_Option_GetSet {
    Drawable path;
    String name;

    public QrCode_Option_GetSet(Drawable path, String name) {
        this.path = path;
        this.name = name;
    }

    public Drawable getPath() {
        return path;
    }

    public void setPath(Drawable path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
