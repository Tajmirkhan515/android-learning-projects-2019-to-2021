package com.dinosoftlabs.qrandbarcodescanner.Main_Menu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dinosoftlabs.qrandbarcodescanner.R;
import com.dinosoftlabs.qrandbarcodescanner.Scan.Scan_f;


public class MainMenuActivity extends AppCompatActivity {
    private MainMenuFragment mainMenuFragment;
    long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

         if (savedInstanceState == null) {

            initScreen();

        } else {
            mainMenuFragment = (MainMenuFragment) getSupportFragmentManager().getFragments().get(0);
        }


        getcamrapermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  laststatus();
    }


    private void initScreen() {
        mainMenuFragment = new MainMenuFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mainMenuFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!mainMenuFragment.onBackPressed()) {
            int count = this.getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                if (mBackPressed + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    Toast.makeText(getBaseContext(), "Tap Again To Exit", Toast.LENGTH_SHORT).show();

                    mBackPressed = System.currentTimeMillis();

                }
            } else {
                super.onBackPressed();
            }
        }

    }




    private void getcamrapermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            getStoragepermission();

        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    786);
        }
    }


    private void getStoragepermission(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){

        }
        else {
            try {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        787 );
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 786) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Scan_f.zXingScannerView.startCamera();
                getStoragepermission();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }
        else if (requestCode == 787) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }



}
