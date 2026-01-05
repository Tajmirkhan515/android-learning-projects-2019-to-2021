package io.github.simplemultilanguage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            //Click English menu option
            case R.id.action_english:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_ENGLISH);

                reLaunchApp();
                return true;

            //Click French menu option
            case R.id.action_french:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_FRENCH);
                reLaunchApp();
                return true;

            //Click Russian menu option
            case R.id.action_russian:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_RUSSIA);
                reLaunchApp();
                return true;

            //Click Spanish menu option
            case R.id.action_spanish:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_SPANISH);

                reLaunchApp();
                return true;

            //Click Arabic menu option
            case R.id.action_arabic:
                LanguageManager.setNewLocale(this, LanguageManager.LANGUAGE_KEY_ARABIC);

                reLaunchApp();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void reLaunchApp(){

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
