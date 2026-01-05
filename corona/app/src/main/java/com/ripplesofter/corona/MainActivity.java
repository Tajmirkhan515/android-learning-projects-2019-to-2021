package com.ripplesofter.corona;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ripplesofter.containers.Session;

import android.os.Handler;

import com.ripplesofter.mainPagaework.CustomAdapter;
import com.ripplesofter.mainPagaework.Model;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager_slide;
    private MainActivity.MyViewPagerAdapter myViewPagerAdapter_slide;
    private LinearLayout dotsLayout_slide;
    private TextView[] dots_slide;
    private int[] layouts_slide;
    private String colorNightOrDay_slide;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 800;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 6000; // time in milliseconds between successive task executions.


    int[] arry=new int[]{R.drawable.test,R.drawable.ic_caution,R.drawable.ic_rout,R.drawable.ic_predictions,R.drawable.ic_vaccine,
            R.drawable.ic_history,R.drawable.ic_aboutus,R.drawable.ic_share_brn,R.drawable.ic_rateus,R.drawable.ic_moreapp,
            R.drawable.ic_contact,R.drawable.ic_privacy_policy
    };
    String[] names;

    ImageButton btn_setting;
    Session session;
    Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        session=new Session(MainActivity.this);
        setLocal(session.getLanguage(),"first");
        super.onCreate(savedInstanceState);

        // Resources resources=  setLocal(session.getLanguage());
          names=getResources().getStringArray(R.array.names_array);
        Log.e("lang", "onCreate: "+MainActivity.this.getString(R.string.next) );
        if(session.cheFirstTime()){
            startActivity(new Intent(MainActivity.this,AppIntroduction.class));
            //MainActivity.this.finish();
        }
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_setting=findViewById(R.id.btn_setting_id);
        // set a LinearLayoutManager with default vertical orientation
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //  call the constructor of DetailAdapter to send the reference and data to Adapter
        ArrayList<Model> personNames=new ArrayList();
        Model model;
        for(int i=0;i<arry.length;i++){
            model=new Model(names[i],arry[i]);
            personNames.add(model);
        }

        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personNames);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr={"english","اردو","Chines"};
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Language");

                builder.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            setLocal("en","");
                            session.setLanguage("en");
                            //LanguageManager.setNewLocale(MainActivity.this, LanguageManager.LANGUAGE_KEY_ENGLISH);

                        }else if(i==1){
                            setLocal("ur","");
                            session.setLanguage("ur");
                            //LanguageManager.setNewLocale(MainActivity.this, LanguageManager.LANGUAGE_KEY_URDU);
                            Toast.makeText(MainActivity.this, "ur", Toast.LENGTH_SHORT).show();
                        }else if(i==2){
                            setLocal("zh","");
                            session.setLanguage("zh");
                        }
                      dialogInterface.dismiss();
                    }

                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        viewPager_slide = (ViewPager) findViewById(R.id.view_pager_slide);
        dotsLayout_slide = (LinearLayout) findViewById(R.id.layoutDots_slide);



        layouts_slide = new int[]{
                R.layout.home_slide1,
                R.layout.home_slide2,
                R.layout.home_slide3,
                R.layout.home_slide4
        };

        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter_slide = new MyViewPagerAdapter();
        viewPager_slide.setAdapter(myViewPagerAdapter_slide);
        viewPager_slide.addOnPageChangeListener(viewPagerPageChangeListener);


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int current = getItem(+1);
                if (current < layouts_slide.length) {
                    // move to next screen
                    viewPager_slide.setCurrentItem(current);
                } else {
                    //launchHomeScreen();
                    viewPager_slide.setCurrentItem(0);
                }
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void setLocal(String lang,String first) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if(!first.equals("first")) {
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            finish();
        }
    }

    private void addBottomDots(int currentPage) {
        dots_slide = new TextView[layouts_slide.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout_slide.removeAllViews();
        for (int i = 0; i < dots_slide.length; i++) {
            try {
                dots_slide[i] = new TextView(this);
                dots_slide[i].setText(Html.fromHtml("&#8226;"));
                dots_slide[i].setTextSize(35);
                dots_slide[i].setTextColor(colorsInactive[currentPage]);
                dotsLayout_slide.addView(dots_slide[i]);
            } catch (Exception e) {

            }
        }
        try {
            if (dots_slide.length > 0)
                dots_slide[currentPage].setTextColor(colorsActive[currentPage]);
        } catch (Exception e) {
        }
    }
    private int getItem(int i) {
        return viewPager_slide.getCurrentItem() + i;
    }
    private void launchHomeScreen() {
        //startActivity(new Intent(AppIntroduction.this, AplashScreen.class));
        Toast.makeText(this, "disply", Toast.LENGTH_SHORT).show();
        new Session(MainActivity.this).setFirstTime(false);
        this.finish();
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts_slide.length - 1) {
                // last page. make button text to GOT IT
//                btnNext_slide.setText(getString(R.string.start));
//                btnSkip_slide.setVisibility(View.GONE);
            } else {
                // still pages are left
                //btnNext_slide.setText(getString(R.string.next));
                //btnSkip_slide.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }




    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts_slide[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts_slide.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


}
