package com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import it.auron.library.mecard.MeCard;

import static com.dinosoftlabs.qrandbarcodescanner.Scan.Scan_f.getBytes;

/**
 * A simple {@link Fragment} subclass.
 */
public class Enter_MeCard_F extends Fragment {


    View view;
    Context context;
    Databaseclass db;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
    String type;
    EditText firstname,lastname,phone,email,Url,adress,birthday,company,note;
    TextView savebtn;
    ImageButton backbtn;

    public Enter_MeCard_F() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_enter_me_card, container, false);


        context=getContext();
        type = getArguments().getString("type");

        savebtn=view.findViewById(R.id.savebtn);

        firstname=view.findViewById(R.id.firstname_edit);
        lastname=view.findViewById(R.id.lastname_edit);
        phone=view.findViewById(R.id.phone_edit);
        email=view.findViewById(R.id.email_edit);
        Url=view.findViewById(R.id.url_edit);
        adress=view.findViewById(R.id.adress_edit);
        birthday=view.findViewById(R.id.birthday_edit);
        company=view.findViewById(R.id.company_edit);
        note=view.findViewById(R.id.note_edit);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opendata_picker();
            }
        });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstnametext=firstname.getText().toString();
                String lastnametext=lastname.getText().toString();
                String phonetext=phone.getText().toString();
                String emailtext=email.getText().toString();
                String urltext=Url.getText().toString();
                String adresstext=adress.getText().toString();
                String birthdaytext=birthday.getText().toString();
                String companytext=company.getText().toString();
                String notetext=note.getText().toString();

                if(TextUtils.isEmpty(firstnametext))
                    firstname.setError("Enter FirstName...");
                else if(TextUtils.isEmpty(lastnametext))
                    lastname.setError("Enter LastName...");
                else if(TextUtils.isEmpty(phonetext))
                    phone.setError("Enter Phone...");
                else if(TextUtils.isEmpty(emailtext))
                    email.setError("Enter Email...");
                else if(TextUtils.isEmpty(urltext))
                    Url.setError("Enter Url...");
                else if(TextUtils.isEmpty(adresstext))
                    adress.setError("Enter Adress...");
                else if(TextUtils.isEmpty(birthdaytext))
                    birthday.setError("Enter BirthDay...");
                else if(TextUtils.isEmpty(companytext))
                    company.setError("Enter Company...");
                else if(TextUtils.isEmpty(notetext))
                    note.setError("Enter Note...");

                else {
                    MeCard meCard =new MeCard();
                    meCard.setName(firstnametext);
                    meCard.setSurname(lastnametext);
                    meCard.setEmail(emailtext);
                    meCard.setDate(birthdaytext);
                    meCard.setOrg(companytext);
                    meCard.setNote(notetext);
                    meCard.addTelephone(phonetext);
                    meCard.setUrl(urltext);
                    meCard.setAddress(adresstext);
                    String meCardContent=meCard.buildString();
                    SaveData(meCardContent);
                    getActivity().onBackPressed();

                }

            }
        });

        backbtn=view.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Create_F.isdataadd=true;
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



    public void Opendata_picker(){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog mdiDialog =new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                Date chosenDate = cal.getTime();
                DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                String df_medium_uk_str = df_medium_uk.format(chosenDate);
                birthday.setText(df_medium_uk_str);

            }
        }, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        mdiDialog.show();
    }

}
