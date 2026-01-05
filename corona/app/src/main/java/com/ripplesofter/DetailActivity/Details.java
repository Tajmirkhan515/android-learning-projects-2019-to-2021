package com.ripplesofter.DetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.ripplesofter.containers.Session;
import com.ripplesofter.corona.MainActivity;
import com.ripplesofter.corona.R;
import com.ripplesofter.mainPagaework.CustomAdapter;
import com.ripplesofter.mainPagaework.Model;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Details extends AppCompatActivity {

    private Session session;
    String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session=new Session(Details.this);
        setLocal(session.getLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        String str = intent.getStringExtra("value");
        if(str.equalsIgnoreCase("caution")||str.equalsIgnoreCase("警告")||str.equalsIgnoreCase("احتیاط")){
            names=getResources().getStringArray(R.array.caution_array);
        }else if(str.equalsIgnoreCase("Routes")||str.equalsIgnoreCase("路线")||str.equalsIgnoreCase("راستہ")){
            names=getResources().getStringArray(R.array.rout_array);
        }else if(str.equalsIgnoreCase("vaccine")||str.equalsIgnoreCase("疫苗")||str.equalsIgnoreCase("ویکسین")){
            names=getResources().getStringArray(R.array.vaccine_array);
        }else if(str.equalsIgnoreCase("prediction")||str.equalsIgnoreCase("预测")||str.equalsIgnoreCase("پیش گوئی")) {
            names=getResources().getStringArray(R.array.prediction_array);
        }else if(str.equalsIgnoreCase("history")||str.equalsIgnoreCase("历史")||str.equalsIgnoreCase("تاریخ")){
            names=getResources().getStringArray(R.array.history_array);
        }



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detail_recycle_id);
        TextView title_txv=findViewById(R.id.title_id);
        TextView des_txv=findViewById(R.id.description_id);
        title_txv.setText(names[0]);
        des_txv.setText(names[1]);

        String[] yourArray=new String[names.length-2];
        int c=0;
        for(int i=2;i<names.length;i++){
            yourArray[c]=names[i];
            c++;
        }
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of DetailAdapter to send the reference and data to Adapter

        DetailAdapter customAdapter = new DetailAdapter(Details.this, yourArray);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

    }

    private void setLocal(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
