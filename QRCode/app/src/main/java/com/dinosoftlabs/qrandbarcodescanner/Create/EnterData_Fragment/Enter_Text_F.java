package com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dinosoftlabs.qrandbarcodescanner.Create.Create_F.Create_F;
import com.dinosoftlabs.qrandbarcodescanner.Database.Databaseclass;
import com.dinosoftlabs.qrandbarcodescanner.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.dinosoftlabs.qrandbarcodescanner.Scan.Scan_f.getBytes;

/**
 * A simple {@link Fragment} subclass.
 */
public class Enter_Text_F extends RootFragment {


    View view;
    Context context;
    Databaseclass db;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
    String type;
    EditText textedit;
    TextView savebtn;
    ImageButton backbtn;


    public Enter_Text_F() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_enter_text, container, false);
        context=getContext();
        type = getArguments().getString("type");

        savebtn=view.findViewById(R.id.savebtn);

        textedit=view.findViewById(R.id.text_edit);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=textedit.getText().toString();
                if(TextUtils.isEmpty(text))
                    textedit.setError("Enter Email...");
                else {
                    SaveData(text);
                    Create_F.isdataadd=true;
                    getActivity().onBackPressed();

                }

            }
        });

        backbtn=view.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        db= new Databaseclass(context);
        return view;
    }



    public void SaveData(String data){
        byte [] imageaarray = GenerateQRimage(data);
        Calendar cal = Calendar.getInstance();
        Date c =cal.getTime();
        String currentdate=sdfDate.format(c);
        db.adddata_increate(textedit.getText().toString(),type,data,imageaarray,currentdate);

    }

    public byte [] GenerateQRimage(String result) {
        Bitmap bitmap;

        MultiFormatWriter fm=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = fm.encode(result, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            bitmap=barcodeEncoder.createBitmap(bitMatrix);
            byte[] a= getBytes(bitmap);
            return a;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

