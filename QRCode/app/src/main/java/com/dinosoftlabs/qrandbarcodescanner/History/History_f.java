package com.dinosoftlabs.qrandbarcodescanner.History;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dinosoftlabs.qrandbarcodescanner.Database.Databaseclass;
import com.dinosoftlabs.qrandbarcodescanner.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class History_f extends RootFragment {

    public static boolean isscan=true;

    View view;
    ArrayList<History_GetSet> history_list;
    Context context;
    RecyclerView history_recycler;

    Databaseclass db;

    LinearLayout nohistory_layout;

    ImageButton refreshbtn;
    AdView adView;

    public History_f() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_history, container, false);
        context=getContext();

        nohistory_layout=view.findViewById(R.id.no_history_layout);
        refreshbtn=view.findViewById(R.id.refreshbtn);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nohistory_layout.setVisibility(View.GONE);
                get_history_data();
            }
        });

        db=new Databaseclass(context);
        history_recycler = (RecyclerView) view.findViewById(R.id.history_recycler);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        history_recycler.setLayoutManager(layout);
        history_recycler.setHasFixedSize(false);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isscan){
            get_history_data();
            isscan=false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // on start the fraagment open the banner add will be load
        adView = view.findViewById(R.id.adView_history);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    // Get the all data that is saved as the scan history from the database and parse it
    public void get_history_data(){
        Cursor cr=db.get_all_data();
        cr.moveToLast();
        if(cr.getCount()>0){
            nohistory_layout.setVisibility(View.GONE);
            history_recycler.setVisibility(View.VISIBLE);
            history_list=new ArrayList<>();

            for (int i=1;i<=cr.getCount();i++){
                History_GetSet history_getSet=new History_GetSet();
                history_getSet.setType(cr.getString(1));
                history_getSet.setData(cr.getString(2));
                history_getSet.setImage(cr.getBlob(3));
                history_getSet.setDate(cr.getString(4));
                history_list.add(history_getSet);
                cr.moveToPrevious();
            }

            History_Adapter adapter=new History_Adapter(context, history_list, new History_Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(History_GetSet item) {
                    CallAction(item);
                }
            });

            history_recycler.setAdapter(adapter);
        }else {
            nohistory_layout.setVisibility(View.VISIBLE);
            history_recycler.setVisibility(View.GONE);
        }
    }


    // check the result if it start with tel: ,Mailto  Uri than we will start the activity according to that
    public void CallAction(History_GetSet item){
        if(item.getData().startsWith("tel:")){
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall(item.getData());
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                requestPermissions( PERMISSIONS_STORAGE, 123);
            }
        }else if(item.getData().startsWith("mailto:")){
            OpenmailIntent(item.getData());
        }
        else if(item.getData().startsWith("sms:")){
            OpenSmsIntent(item.getData());
        }
        else if(item.getData().startsWith("http://") || item.getType().startsWith("https://")){
            OpenLink(item.getData());
        }

    }



    private void phoneCall(String contactnumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(contactnumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void OpenmailIntent(String email){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(email));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }

    public void OpenSmsIntent(String message){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse(message));
        startActivity(smsIntent);
    }

    public void OpenLink(String url){
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode){
            case 123:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)

                    break;
        }
    }
}
