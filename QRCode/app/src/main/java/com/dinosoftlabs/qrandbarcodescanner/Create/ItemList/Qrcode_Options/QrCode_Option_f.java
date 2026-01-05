package com.dinosoftlabs.qrandbarcodescanner.Create.ItemList.Qrcode_Options;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_Email_f;
import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_MeCard_F;
import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_Message_F;
import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_Phone_F;
import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_Text_F;
import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_Url_F;
import com.dinosoftlabs.qrandbarcodescanner.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.dinosoftlabs.qrandbarcodescanner.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QrCode_Option_f extends RootFragment {


    View view;

    Context context;

    RecyclerView creat_item_list;


    ArrayList<QrCode_Option_GetSet> arrayList;
    public QrCode_Option_f() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_qr_option_list_f, container, false);
        context=getContext();

        arrayList= new ArrayList<>();


        creat_item_list=view.findViewById(R.id.create_item_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        creat_item_list.setLayoutManager(layoutManager);
        creat_item_list.setHasFixedSize(false);

        arrayList.add(new QrCode_Option_GetSet(context.getResources()
                .getDrawable(R.drawable.mecard),"MeCard"));

        arrayList.add(new QrCode_Option_GetSet(context.getResources()
                .getDrawable(R.drawable.message),"Message"));


        arrayList.add(new QrCode_Option_GetSet(context.getResources()
                .getDrawable(R.drawable.email),"Email"));


        arrayList.add(new QrCode_Option_GetSet(context.getResources()
                .getDrawable(R.drawable.text),"Text"));

        arrayList.add(new QrCode_Option_GetSet(context.getResources()
                .getDrawable(R.drawable.phone),"Phone"));


        arrayList.add(new QrCode_Option_GetSet(context.getResources()
                .getDrawable(R.drawable.url),"URL"));

        QrCode_Option_adapter adapter=new QrCode_Option_adapter(context, arrayList, new QrCode_Option_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(QrCode_Option_GetSet item) {

                if(item.getName().equals("MeCard"))
                    opencreateMeCardF(item);
                else if(item.getName().equals("Email"))
                opencreateemailF(item);
                else if(item.getName().equals("Message"))
                    opencreateMessageF(item);
                else if(item.getName().equals("Phone"))
                    opencreatePhoneF(item);
                else if(item.getName().equals("URL"))
                    opencreateURLF(item);
                else if(item.getName().equals("Text"))
                    opencreateTextF(item);

            }
        });

        creat_item_list.setAdapter(adapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void opencreateemailF(QrCode_Option_GetSet item){
        Enter_Email_f enterEmailf = new Enter_Email_f();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("type",item.getName());
        enterEmailf.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, enterEmailf).commit();

    }

    public void opencreateMessageF(QrCode_Option_GetSet item){

        Enter_Message_F enter_message_f = new Enter_Message_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("type",item.getName());
        enter_message_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, enter_message_f).commit();

    }

    public void opencreatePhoneF(QrCode_Option_GetSet item){
        Enter_Phone_F phone_f = new Enter_Phone_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("type",item.getName());
        phone_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, phone_f).commit();
    }

    public void opencreateURLF(QrCode_Option_GetSet item){

        Enter_Url_F enter_url_f = new Enter_Url_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("type",item.getName());
        enter_url_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, enter_url_f).commit();

    }
    public void opencreateTextF(QrCode_Option_GetSet item){

        Enter_Text_F enter_text_f = new Enter_Text_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("type",item.getName());
        enter_text_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, enter_text_f).commit();

    }

    public void opencreateMeCardF(QrCode_Option_GetSet item){

        Enter_MeCard_F enter_meCard_f = new Enter_MeCard_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("type",item.getName());
        enter_meCard_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, enter_meCard_f).commit();

    }


}
