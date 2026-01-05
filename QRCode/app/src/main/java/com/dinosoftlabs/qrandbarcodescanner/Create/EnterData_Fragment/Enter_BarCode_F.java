package com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dinosoftlabs.qrandbarcodescanner.Create.Create_F.Create_F;
import com.dinosoftlabs.qrandbarcodescanner.Database.Databaseclass;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class Enter_BarCode_F extends Fragment {

    // these four variable are changed according to the Barcode format selected
    // form privious fragment means barcode option which you select in privious fragment
    public static BarcodeFormat barcodeFormat;
    public static int Maxlength=0;
    public static int Minlenght=0;
    public static String Inputtype="number";


    View view;
    Context context;
    Databaseclass db;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
    String format;
    EditText idedit;
    TextView savebtn;
    ImageButton backbtn;



    public Enter_BarCode_F() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_enter_bar_code, container, false);
        context=getContext();
        format = getArguments().getString("format");

        savebtn=view.findViewById(R.id.savebtn);

        idedit=view.findViewById(R.id.id_edit);

        if(Inputtype.equals("number")){
            idedit.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if(Inputtype.equals("both")) {
            idedit.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if(Minlenght==Maxlength){
            idedit.setHint("ID length must be "+Maxlength+" long");
        }else {
            idedit.setHint("ID length must be between "+Minlenght +" to "+Maxlength+" long");
        }

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=idedit.getText().toString();
                if(TextUtils.isEmpty(id))
                    idedit.setError("Enter ID");
                else if(id.length()<Minlenght || id.length()>Maxlength)
                    idedit.setError("Please Enter the Correct Format");
                else {
                    SaveData(id);
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
        db.adddata_increate(idedit.getText().toString(),format,data,imageaarray,currentdate);

    }

    public byte [] GenerateQRimage(String result) {
        Bitmap bitmap;

        MultiFormatWriter fm=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = fm.encode(result, barcodeFormat, 400,400);
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
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}

