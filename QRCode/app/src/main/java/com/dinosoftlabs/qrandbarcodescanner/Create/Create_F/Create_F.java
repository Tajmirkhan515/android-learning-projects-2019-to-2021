package com.dinosoftlabs.qrandbarcodescanner.Create.Create_F;


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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dinosoftlabs.qrandbarcodescanner.Create.ItemList.Create_Main_Fragment;
import com.dinosoftlabs.qrandbarcodescanner.Database.Databaseclass;
import com.dinosoftlabs.qrandbarcodescanner.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */


/*
this is the main fragment which is show in thired tab
*/

public class Create_F extends RootFragment {

    public static boolean isdataadd=false;

    View view;

    ImageButton plusbtn;

    RecyclerView create_recyler;


    Context context;


    Databaseclass db;

    LinearLayout empty_list_layout;
    ArrayList<Create_GetSet> Createlist;

    Button create_qr_btn;
    AdView adView;

    public Create_F() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_create, container, false);
        context=getContext();
        db=new Databaseclass(context);

        empty_list_layout=view.findViewById(R.id.empty_list_layout);
        plusbtn=view.findViewById(R.id.plusbtn);
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               opencreatelistF();
            }
        });

        create_recyler=view.findViewById(R.id.create_recyler);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        create_recyler.setLayoutManager(layout);
        create_recyler.setHasFixedSize(false);

        create_qr_btn=view.findViewById(R.id.create_qr_btn);
        create_qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencreatelistF();
            }
        });


        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (isdataadd) {
                    get_Create_data();
                    isdataadd = false;
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adView = view.findViewById(R.id.adView_create);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        get_Create_data();
    }

    public void get_Create_data(){
            Cursor cr=db.get_all_create_data();
            cr.moveToLast();
            if(cr.getCount()>0){
                create_recyler.setVisibility(View.VISIBLE);
                empty_list_layout.setVisibility(View.GONE);

                Createlist=new ArrayList<>();
                for (int i=1;i<=cr.getCount();i++){
                    Create_GetSet create_getSet=new Create_GetSet();
                    create_getSet.setTitle(cr.getString(1));
                    create_getSet.setType(cr.getString(2));
                    create_getSet.setData(cr.getString(3));
                    create_getSet.setImage(cr.getBlob(4));
                    create_getSet.setDate(cr.getString(5));
                    Createlist.add(create_getSet);
                    cr.moveToPrevious();
                }

                Create_Adapter adapter=new Create_Adapter(context, Createlist, new Create_Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Create_GetSet item) {

                        CallAction(item);
                    }
                });

                create_recyler.setAdapter(adapter);
        }
        else {
                create_recyler.setVisibility(View.GONE);
                empty_list_layout.setVisibility(View.VISIBLE);
            }
        }

    public void opencreatelistF(){
            Create_Main_Fragment create_main_fragment = new Create_Main_Fragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
            transaction.addToBackStack(null);
            transaction.replace(R.id.mainmenuFragment, create_main_fragment).commit();
        }

     public void CallAction(Create_GetSet item){
        if(item.getType().equals("Phone")){
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall(item.getData());
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                requestPermissions( PERMISSIONS_STORAGE, 123);
            }
        }else if(item.getType().equals("Email")){
            OpenmailIntent(item.getData());
        }
        else if(item.getType().equals("Message")){
            OpenSmsIntent(item.getData());
        }
        else if(item.getType().equals("URL")){
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
