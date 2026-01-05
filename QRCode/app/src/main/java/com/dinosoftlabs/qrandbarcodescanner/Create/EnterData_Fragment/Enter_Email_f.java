package com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
public class Enter_Email_f extends RootFragment {

    View view;
    Context context;
    Databaseclass db;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
    String type;
    EditText email,cc,subject,body;
    TextView savebtn;
    ImageButton backbtn;

    public Enter_Email_f() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_enter_email, container, false);

        context=getContext();
        type = getArguments().getString("type");

        savebtn=view.findViewById(R.id.savebtn);

        email=view.findViewById(R.id.email_edit);
        cc=view.findViewById(R.id.cc_edit);
        subject=view.findViewById(R.id.subject_edit);
        body=view.findViewById(R.id.body_edit);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailtext=email.getText().toString();
                String cctext=cc.getText().toString();
                String subjecttext=subject.getText().toString();
                String bodytext=body.getText().toString();
                if(TextUtils.isEmpty(mailtext))
                   email.setError("Enter Email...");
                 else if(TextUtils.isEmpty(cctext))
                    cc.setError("Enter CC...");
                else if(TextUtils.isEmpty(subjecttext))
                    subject.setError("Enter subject...");
                else if(TextUtils.isEmpty(bodytext))
                    body.setError("Enter Body...");
                else {
                String uriText =
                        "mailto:"+mailtext+
                                "?cc="+Uri.encode(cctext)+
                                "&subject=" + Uri.encode(subjecttext) +
                                "&body=" + Uri.encode(bodytext);

                SaveData(uriText);

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
        db.adddata_increate(email.getText().toString(),type,data,imageaarray,currentdate);

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
