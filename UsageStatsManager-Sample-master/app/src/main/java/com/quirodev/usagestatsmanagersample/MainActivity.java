package com.quirodev.usagestatsmanagersample;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements UsageContract.View {

    private ProgressBar progressBar;
    private TextView permissionMessage;

    private UsageContract.Presenter presenter;
    private UsageStatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean granted = false;

        if(!checkUstageAllowedOrNot()){
            Intent usagstatIntent=new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            usagstatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(usagstatIntent);
        }

        AppOpsManager appOps = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            appOps = (AppOpsManager) this
                    .getSystemService(Context.APP_OPS_SERVICE);
        }
        int mode = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), this.getPackageName());
        }

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (this.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        permissionMessage = (TextView) findViewById(R.id.grant_permission_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsageStatAdapter();
        recyclerView.setAdapter(adapter);

        permissionMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.openSettings();
            }
        });

        presenter = new UsagePresenter(MainActivity.this, this);
    }

    private void openSettings() {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        presenter.retrieveUsageStats();
    }

    @Override
    public void onUsageStatsRetrieved(List<UsageStatsWrapper> list) {
        showProgressBar(false);
        permissionMessage.setVisibility(GONE);
        adapter.setList(list);
    }

    @Override
    public void onUserHasNoPermission() {
        showProgressBar(false);
        permissionMessage.setVisibility(VISIBLE);
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(GONE);
        }
    }

    private boolean checkUstageAllowedOrNot() {
        int mode=0;
        try {
            PackageManager pkManager=getPackageManager();
            ApplicationInfo applicationInfo=pkManager.getApplicationInfo(getPackageName(),0);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                AppOpsManager appOpsManager=(AppOpsManager)this.getSystemService(APP_OPS_SERVICE);
                mode=appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,applicationInfo.uid,applicationInfo.packageName);
                return (mode==AppOpsManager.MODE_ALLOWED);
            }
        }catch (Exception e){
            Toast.makeText(this, "can not found any usage state", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}


