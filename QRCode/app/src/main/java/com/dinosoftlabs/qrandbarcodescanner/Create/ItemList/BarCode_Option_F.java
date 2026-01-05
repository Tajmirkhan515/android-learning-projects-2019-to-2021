package com.dinosoftlabs.qrandbarcodescanner.Create.ItemList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dinosoftlabs.qrandbarcodescanner.Create.EnterData_Fragment.Enter_BarCode_F;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.google.zxing.BarcodeFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarCode_Option_F extends Fragment implements View.OnClickListener{

    View view;
    Context context;

    LinearLayout AZTEC,
            CODABAR,
            CODE_39,
            CODE_128,
            DATA_MATRIX,
            EAN_8,
            EAN_13,
            ITF,
            PDF_417,
            UPC_A;
    public BarCode_Option_F() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_bar_code_option, container, false);
        context=getContext();
                AZTEC=view.findViewById(R.id.AZTEC);
        AZTEC.setOnClickListener(this);

                CODABAR=view.findViewById(R.id.CODABAR);
         CODABAR.setOnClickListener(this);

                CODE_39=view.findViewById(R.id.CODE_39);
        CODE_39.setOnClickListener(this);


                CODE_128=view.findViewById(R.id.CODE_128);
        CODE_128.setOnClickListener(this);

                DATA_MATRIX=view.findViewById(R.id.DATA_MATRIX);
        DATA_MATRIX.setOnClickListener(this);

                EAN_8=view.findViewById(R.id.EAN_8);
        EAN_8.setOnClickListener(this);

                EAN_13=view.findViewById(R.id.EAN_13);
        EAN_13.setOnClickListener(this);

                ITF=view.findViewById(R.id.ITF);
        ITF.setOnClickListener(this);


                PDF_417=view.findViewById(R.id.PDF_417);
        PDF_417.setOnClickListener(this);



                UPC_A=view.findViewById(R.id.UPC_A);
        UPC_A.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.AZTEC:
                Enter_BarCode_F.Maxlength=1000;
                Enter_BarCode_F.Minlenght=0;
                Enter_BarCode_F.Inputtype="both";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.AZTEC;
                opencreate_Barcode_F("AZTEC");
                break;
            case R.id.CODABAR:
                Enter_BarCode_F.Maxlength=16;
                Enter_BarCode_F.Minlenght=0;
                Enter_BarCode_F.Inputtype="number";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.CODABAR;
                opencreate_Barcode_F("CODABAR");
                break;
            case R.id.CODE_39:
                Enter_BarCode_F.Maxlength=25;
                Enter_BarCode_F.Minlenght=0;
                Enter_BarCode_F.Inputtype="number";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.CODE_39;
                opencreate_Barcode_F("CODE_39");
                break;

            case R.id.CODE_128:

                Enter_BarCode_F.Maxlength=128;
                Enter_BarCode_F.Minlenght=0;
                Enter_BarCode_F.Inputtype="both";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.CODE_128;
                opencreate_Barcode_F("CODE_128");
                break;
            case R.id.DATA_MATRIX:
                Enter_BarCode_F.Maxlength=1000;
                Enter_BarCode_F.Minlenght=0;
                Enter_BarCode_F.Inputtype="both";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.DATA_MATRIX;
                opencreate_Barcode_F("DATA_MATRIX");
                break;
            case R.id.EAN_8:
                Enter_BarCode_F.Maxlength=8;
                Enter_BarCode_F.Minlenght=8;
                Enter_BarCode_F.Inputtype="number";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.EAN_8;
                opencreate_Barcode_F("EAN_8");
                break;
            case R.id.EAN_13:
                Enter_BarCode_F.Maxlength=13;
                Enter_BarCode_F.Minlenght=13;
                Enter_BarCode_F.Inputtype="number";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.EAN_13;
                opencreate_Barcode_F("EAN_13");
                break;
            case R.id.ITF:
                Enter_BarCode_F.Maxlength=14;
                Enter_BarCode_F.Minlenght=14;
                Enter_BarCode_F.Inputtype="number";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.ITF;
                opencreate_Barcode_F("ITF");
                break;

            case R.id.PDF_417:
                Enter_BarCode_F.Maxlength=1000;
                Enter_BarCode_F.Minlenght=0;
                Enter_BarCode_F.Inputtype="both";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.PDF_417;
                opencreate_Barcode_F("PDF_417");
                break;

            case R.id.UPC_A:
                Enter_BarCode_F.Maxlength=12;
                Enter_BarCode_F.Minlenght=12;
                Enter_BarCode_F.Inputtype="number";
                Enter_BarCode_F.barcodeFormat= BarcodeFormat.UPC_A;
                opencreate_Barcode_F("UPC_A");
                break;

        }
    }

    public void opencreate_Barcode_F(String Format){
        Enter_BarCode_F enter_barCode_f = new Enter_BarCode_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle args = new Bundle();
        args.putString("format",Format);
        enter_barCode_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.mainmenuFragment, enter_barCode_f).commit();

    }
}
