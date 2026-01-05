package com.ripplesofter.containers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;


    private static final int PRIVATE_MODE = 0;
    private static final String PREFS_NAME = "TABS";

    private static final String IS_USER_LOGED_ID = "isUserLoggedIn";


    public Session(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public void setFirstTime(Boolean check) {
        editor.putBoolean(Utility.CHECKFIRSTTIME, check);
        editor.commit();
    }

    public boolean cheFirstTime() {
        return prefs.getBoolean(Utility.CHECKFIRSTTIME, true);
    }

    public void setLanguage(String lang){
        editor.putString("lang",lang);
        editor.commit();
    }

    public String getLanguage(){
        return prefs.getString("lang","");
    }
}
