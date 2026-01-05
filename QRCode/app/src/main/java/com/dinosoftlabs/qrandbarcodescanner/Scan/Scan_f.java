package com.dinosoftlabs.qrandbarcodescanner.Scan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dinosoftlabs.qrandbarcodescanner.Database.Databaseclass;
import com.dinosoftlabs.qrandbarcodescanner.History.History_f;
import com.dinosoftlabs.qrandbarcodescanner.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Scan_f extends RootFragment implements ZXingScannerView.ResultHandler {

    public static ZXingScannerView zXingScannerView;

    LinearLayout scan_layout;

    ImageView camerabtn,select_image,flash_light_btn;
    TextView flashtext,cameratxt;

    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

    public Scan_f() {
        // Required empty public constructor
    }

    Context context;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_scan, container, false);
        context=getContext();

        scan_layout=view.findViewById(R.id.scan_layout);


        camerabtn=view.findViewById(R.id.camerabtn);
        flash_light_btn=view.findViewById(R.id.flash_light_btn);
        select_image=view.findViewById(R.id.select_image);


        flashtext=view.findViewById(R.id.flaash_txt);

        // ZXingScannerview that is the main Border view in Scan that will scan the code
        zXingScannerView =new ZXingScannerView(context);
        scan_layout.addView(zXingScannerView);
        zXingScannerView.setResultHandler(Scan_f.this);
        zXingScannerView.setAutoFocus(true);


        // get the flash light status
        if(zXingScannerView.getFlash()){
         flashtext.setTextColor(context.getResources().getColor(R.color.black));
        }


        //button to on off flash light
        flash_light_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zXingScannerView.getFlash()){
                zXingScannerView.setFlash(true);
                flash_light_btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_flash_off));
                    flashtext.setTextColor(context.getResources().getColor(R.color.black));
                }
                else{
                    zXingScannerView.setFlash(false);
                    flashtext.setTextColor(context.getResources().getColor(R.color.darkgray));
                    flash_light_btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_flash_on));
            }
            }
        });


        // select image btn to select image to image to scan the code from image
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);

            }
        });


        // camera btn to start the camera if camera is stop or pause
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zXingScannerView!=null)
                    zXingScannerView.startCamera();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(zXingScannerView!=null)
        zXingScannerView.startCamera();
    }

      @Override
    public void onPause() {
        super.onPause();
        if(zXingScannerView!=null)
            zXingScannerView.stopCamera();
    }



    // this function will save the scaned data into local data base
    public void SaveData(BarcodeFormat tpye,String data){
            Databaseclass db=new Databaseclass(context);
          // byte [] imageaarray = convertYuvByteArrayToBitmap(image, zXingScannerView.getCamera());
            byte [] imageaarray = GenerateQRimage(data,tpye);
            Calendar cal = Calendar.getInstance();
            Date c =cal.getTime();
            String currentdate=sdfDate.format(c);
            db.adddata(tpye.toString(),data,imageaarray,currentdate);

    }


    // if app sane some Qr or bar  code  this method will call which will return the result
    @Override
    public void handleResult(Result result) {
        History_f.isscan=true;
        ShowAlert_Result(result.getText());
        SaveData(result.getBarcodeFormat(),result.getText());
    }



    // we create the Qr code of scan result and save it in local sqlite database
    public byte [] GenerateQRimage(String result, BarcodeFormat format) {
        Bitmap bitmap;

        MultiFormatWriter fm=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = fm.encode(result, format, 400,400);
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


    // change the bitmap to bytes
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    // show the scaned result in alert dialog
    public void ShowAlert_Result(String result){
        AlertDialog.Builder alert=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        alert.setTitle("Scan cam")
                .setMessage(result)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        zXingScannerView.resumeCameraPreview(Scan_f.this);
                        dialog.cancel();

                    }
                });
        alert.setCancelable(false);
        alert.show();
    }

    //on image select this method will give the bitmap of selected image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

             if (requestCode == 2) {

                Uri selectedImage = data.getData();

                InputStream imageStream = null;
                try {
                    imageStream =context.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imagebitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                Result result=scanQRImage(imagebitmap);
                 handleResult(result);
            }
        }
    }


    // this function will scan the Qr and bar code from the image bitmap
    public  Result scanQRImage(Bitmap bMap) {
        String contents = null;

        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            return result;
        }
        catch (Exception e) {
            Log.e("QrTest", "Error decoding barcode", e);
        }

        return null;
    }


}
