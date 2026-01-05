package com.ripplesofter.corona;

import android.app.Application;

import java.util.Locale;
import android.content.res.Configuration;

import com.ripplesofter.containers.Session;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Create a new Locale object

        Locale locale = new Locale(new Session(MyApplication.this).getLanguage());
        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }

}