package com.ripplesofter.questionair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.ripplesofter.containers.Session;
import com.ripplesofter.corona.R;

import java.util.HashMap;
import java.util.Locale;


public class QuestionarActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private Session session;
    HashMap<String, Boolean> Entery=new HashMap<>();
    String[] array;
    // enum Possible {Fever,Flu,Throat,Cough,Vomit,Pandemic,Breathing,Tiredness,Disease,Age};
     int numberOfQuestion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionar);


        session = new Session(this);
      ////  ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
       // Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);

        viewPager = (ViewPager) findViewById(R.id.view_pager_qus);
        btnSkip = (Button) findViewById(R.id.btn_skip_qus);
        btnNext = (Button) findViewById(R.id.btn_next_qus);

array=new String[]{getResources().getString(R.string.fever),getResources().getString(R.string.flu),
        getResources().getString(R.string.throut),getResources().getString(R.string.cough),
        getResources().getString(R.string.vomit),getResources().getString(R.string.pandemic),
        getResources().getString(R.string.breathing),getResources().getString(R.string.tiredness),
        getResources().getString(R.string.disease),getResources().getString(R.string.age),
        getResources().getString(R.string.day)};

        final CheckBox check_yes=findViewById(R.id.check_yes_id);
        final CheckBox check_no=findViewById(R.id.check_no_id);

        check_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

              if(check_no.isChecked()){
                  check_no.setChecked(false);
                  //check_yes.setChecked(true);
              }

            }
        });
        check_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(check_yes.isChecked()){
                    check_yes.setChecked(false);
                    //check_no.setChecked(true);
                }
            }
        });

        layouts = new int[]{
                R.layout.question1,
                R.layout.question2,
                R.layout.question3,
                R.layout.question4,
                R.layout.question5,
                R.layout.question6,
                R.layout.question7,
                R.layout.question8,
                R.layout.question9,
                R.layout.question10,
                R.layout.question11,
        };

        addBottomDots(0);
        // making notification bar transparent
        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launchHomeScreen();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                if(check_yes.isChecked() || check_no.isChecked()){
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);

                    if(check_no.isChecked()){
                        Entery.put(array[numberOfQuestion],false);
                    }else if(check_yes.isChecked()){
                        Entery.put(array[numberOfQuestion],true);
                    }
                    numberOfQuestion++;
                    check_no.setChecked(false);
                    check_yes.setChecked(false);

                }else if(current==layouts.length) {

                    if(check_no.isChecked()){
                        Entery.put(array[numberOfQuestion],false);
                    }else if(check_yes.isChecked()){
                        Entery.put(array[numberOfQuestion],true);
                    }

                    for(String str :Entery.keySet()){
                        Log.e("ddd", "onClick: "+str+"    "+Entery.get(str) );
                    }//numberOfQuestion++;
                    check_no.setChecked(false);
                    check_yes.setChecked(false);

//"Fever","Flu","Throat","Cough","Vomit","Pandemic","Breathing","Tiredness","Disease","Age","Total Days"
//

                     String title = "title";
                     String msg="message";


                    if( (Entery.get(getResources().getString(R.string.flu))==true &&
                            Entery.get(getResources().getString(R.string.fever))==true &&
                            Entery.get(getResources().getString(R.string.throut))==true  &&
                            Entery.get(getResources().getString(R.string.cough))==true  &&
                            Entery.get(getResources().getString(R.string.vomit))==true  &&
                            Entery.get(getResources().getString(R.string.pandemic))==true  &&
                            Entery.get(getResources().getString(R.string.breathing))==true  &&
                            Entery.get(getResources().getString(R.string.tiredness))==true  &&
                            Entery.get(getResources().getString(R.string.disease))==true   &&
                            Entery.get(getResources().getString(R.string.day))==true &&
                            Entery.get(getResources().getString(R.string.age))==true
                    ) ||
                            (Entery.get(getResources().getString(R.string.flu))==true &&
                            Entery.get(getResources().getString(R.string.fever))==true &&
                            Entery.get(getResources().getString(R.string.throut))==true  &&
                            Entery.get(getResources().getString(R.string.cough))==true  &&
                            Entery.get(getResources().getString(R.string.vomit))==true  &&
                            Entery.get(getResources().getString(R.string.pandemic))==true &&
                            Entery.get(getResources().getString(R.string.tiredness))==true &&
                            Entery.get(getResources().getString(R.string.disease))==false &&
                            Entery.get(getResources().getString(R.string.day))==false &&
                            Entery.get(getResources().getString(R.string.age))==false &&
                            Entery.get(getResources().getString(R.string.breathing))==false
                            )
                       ||
                            (Entery.get(getResources().getString(R.string.flu))==true &&
                                    Entery.get(getResources().getString(R.string.fever))==true &&
                                    Entery.get(getResources().getString(R.string.throut))==true  &&
                                    Entery.get(getResources().getString(R.string.cough))==true  &&
                                    Entery.get(getResources().getString(R.string.pandemic))==true &&
                                    Entery.get(getResources().getString(R.string.age))==true &&
                                    Entery.get(getResources().getString(R.string.tiredness))==true &&
                                    Entery.get(getResources().getString(R.string.vomit))==false  &&
                                    Entery.get(getResources().getString(R.string.disease))==false &&
                                    Entery.get(getResources().getString(R.string.day))==false  &&
                                    Entery.get(getResources().getString(R.string.breathing))==false
                            )
                            ||
                            (       Entery.get(getResources().getString(R.string.flu))==true &&
                                    Entery.get(getResources().getString(R.string.fever))==true &&
                                    Entery.get(getResources().getString(R.string.throut))==true  &&
                                    Entery.get(getResources().getString(R.string.cough))==true  &&
                                    Entery.get(getResources().getString(R.string.vomit))==true  &&
                                    Entery.get(getResources().getString(R.string.breathing))==true &&
                                    Entery.get(getResources().getString(R.string.age))==true &&
                                    Entery.get(getResources().getString(R.string.day))==true &&
                                    Entery.get(getResources().getString(R.string.pandemic))==false &&
                                    Entery.get(getResources().getString(R.string.tiredness))==false &&
                                    Entery.get(getResources().getString(R.string.disease))==false
                            )
                            ||
                            (
                                    Entery.get(getResources().getString(R.string.flu))==true &&
                                    Entery.get(getResources().getString(R.string.fever))==true &&
                                    Entery.get(getResources().getString(R.string.breathing))==true  &&
                                    Entery.get(getResources().getString(R.string.cough))==true  &&
                                    Entery.get(getResources().getString(R.string.pandemic))==true &&
                                    Entery.get(getResources().getString(R.string.age))==true &&
                                    Entery.get(getResources().getString(R.string.day))==true  &&
                                    Entery.get(getResources().getString(R.string.vomit))==false  &&
                                    Entery.get(getResources().getString(R.string.tiredness))==false &&
                                    Entery.get(getResources().getString(R.string.throut))==false  &&
                                    Entery.get(getResources().getString(R.string.disease))==false

                            )
                            ||
                            (       Entery.get(getResources().getString(R.string.flu))==true &&
                                    Entery.get(getResources().getString(R.string.fever))==true &&
                                    Entery.get(getResources().getString(R.string.breathing))==true  &&
                                    Entery.get(getResources().getString(R.string.disease))==true  &&
                                    Entery.get(getResources().getString(R.string.cough))==true &&
                                    Entery.get(getResources().getString(R.string.age))==true &&
                                    Entery.get(getResources().getString(R.string.day))==true &&
                                    Entery.get(getResources().getString(R.string.pandemic))==false &&
                                    Entery.get(getResources().getString(R.string.vomit))==false &&
                                    Entery.get(getResources().getString(R.string.tiredness))==false &&
                                    Entery.get(getResources().getString(R.string.throut))==false
                            )

                    ){
                         title = getResources().getString(R.string.serious);//"Serious/Critical";
                         msg=getResources().getString(R.string.serious_desc);

                    }else if(
                            (Entery.get(getResources().getString(R.string.flu))==true &&
                            Entery.get(getResources().getString(R.string.fever))==true &&
                            Entery.get(getResources().getString(R.string.throut))==true  &&
                            Entery.get(getResources().getString(R.string.vomit))==true  &&
                            Entery.get(getResources().getString(R.string.pandemic))==true &&
                            Entery.get(getResources().getString(R.string.breathing))==true &&

                                    Entery.get(getResources().getString(R.string.disease))==false  &&
                                    Entery.get(getResources().getString(R.string.cough))==false &&
                                    Entery.get(getResources().getString(R.string.age))==false &&
                                    Entery.get(getResources().getString(R.string.day))==false &&
                                    Entery.get(getResources().getString(R.string.tiredness))==false
                           )
                                    ||
                            (
                                    Entery.get(getResources().getString(R.string.flu))==true &&
                                    Entery.get(getResources().getString(R.string.fever))==true &&
                                    Entery.get(getResources().getString(R.string.pandemic))==true  &&
                                    Entery.get(getResources().getString(R.string.breathing))==true  &&
                                    Entery.get(getResources().getString(R.string.day))==true &&
                                    Entery.get(getResources().getString(R.string.throut))==false  &&
                                    Entery.get(getResources().getString(R.string.vomit))==false &&
                                    Entery.get(getResources().getString(R.string.disease))==false  &&
                                    Entery.get(getResources().getString(R.string.cough))==false &&
                                    Entery.get(getResources().getString(R.string.age))==false &&
                                    Entery.get(getResources().getString(R.string.tiredness))==false
                            )
                                    ||
                                    (       Entery.get(getResources().getString(R.string.flu))==true &&
                                            Entery.get(getResources().getString(R.string.fever))==true &&
                                            Entery.get(getResources().getString(R.string.tiredness))==true  &&
                                            Entery.get(getResources().getString(R.string.breathing))==true  &&
                                            Entery.get(getResources().getString(R.string.cough))==true &&

                                            Entery.get(getResources().getString(R.string.pandemic))==false &&
                                            Entery.get(getResources().getString(R.string.day))==false &&
                                            Entery.get(getResources().getString(R.string.throut))==false  &&
                                            Entery.get(getResources().getString(R.string.vomit))==false &&
                                            Entery.get(getResources().getString(R.string.disease))==false &&
                                            Entery.get(getResources().getString(R.string.age))==false
                                    )
                    ){
                        title = getResources().getString(R.string.mild);
                        msg=getResources().getString(R.string.mild_des);
                    } else if(
                            (Entery.get(getResources().getString(R.string.flu))==false &&
                            Entery.get(getResources().getString(R.string.fever))==false &&
                            Entery.get(getResources().getString(R.string.throut))==false  &&
                            Entery.get(getResources().getString(R.string.cough))==false &&
                            Entery.get(getResources().getString(R.string.vomit))==false  &&
                            Entery.get(getResources().getString(R.string.pandemic))==false  &&
                            Entery.get(getResources().getString(R.string.breathing))==false  &&
                            Entery.get(getResources().getString(R.string.tiredness))==false  &&
                            Entery.get(getResources().getString(R.string.disease))==false  &&
                            Entery.get(getResources().getString(R.string.age))==false  &&
                            Entery.get(getResources().getString(R.string.day))==false
                    ) &&
                                    (Entery.get(getResources().getString(R.string.flu))==false &&
                                            Entery.get(getResources().getString(R.string.fever))==false &&
                                            Entery.get(getResources().getString(R.string.throut))==false  &&
                                            Entery.get(getResources().getString(R.string.cough))==false &&
                                            Entery.get(getResources().getString(R.string.breathing))==false  &&
                                            Entery.get(getResources().getString(R.string.disease))==false  &&
                                            Entery.get(getResources().getString(R.string.age))==false

                    ) ){
                        title=getResources().getString(R.string.healthy);
                        msg=getResources().getString(R.string.health_des);
                    }else if (Entery.get(getResources().getString(R.string.flu))==false &&
                            Entery.get(getResources().getString(R.string.fever))==false &&
                            Entery.get(getResources().getString(R.string.throut))==false  &&
                            Entery.get(getResources().getString(R.string.cough))==false &&
                            Entery.get(getResources().getString(R.string.vomit))==false  &&
                            Entery.get(getResources().getString(R.string.pandemic))==true  &&
                            Entery.get(getResources().getString(R.string.breathing))==false  &&
                            Entery.get(getResources().getString(R.string.tiredness))==false  &&
                            Entery.get(getResources().getString(R.string.disease))==false  &&
                            Entery.get(getResources().getString(R.string.age))==false  &&
                            Entery.get(getResources().getString(R.string.day))==false)
                    {
                        title=getResources().getString(R.string.waiting);
                        msg=getResources().getString(R.string.waiting_des);
                    }
                    else{
                        title=getResources().getString(R.string.problem);
                        msg=getResources().getString(R.string.problem_des);
                    }

                    final String finalMsg = msg;
                    final String finalTitle = title;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            openDialog(finalTitle, finalMsg);
                        }
                    });
                }else {
                //    launchHomeScreen();
                }
             }else{
                    Toast.makeText(QuestionarActivity.this, "Please Select One", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openDialog(String finalTitle, String finalMsg) {
        final Dialog dialog = new Dialog(QuestionarActivity.this); // Context, this, etc.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_show);
        TextView title_txv=dialog.findViewById(R.id.title_id);
        TextView msg_txv=dialog.findViewById(R.id.dialog_info);
        Button btn_ok=dialog.findViewById(R.id.dialog_ok);

        title_txv.setText(finalTitle);
        msg_txv.setText(finalMsg);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void addBottomDots(int currentPage) {
//        dots = new TextView[layouts.length];
//
//        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
//        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
//
//        dotsLayout.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            try {
//                dots[i] = new TextView(this);
//                dots[i].setText(Html.fromHtml("&#8226;"));
//                dots[i].setTextSize(35);
//                dots[i].setTextColor(colorsInactive[currentPage]);
//                dotsLayout.addView(dots[i]);
//            } catch (Exception e) {
//
//            }
//        }
//        try {
//            if (dots.length > 0)
//                dots[currentPage].setTextColor(colorsActive[currentPage]);
//        } catch (Exception e) {
//        }

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        //startActivity(new Intent(AppIntroduction.this, DynamicTopics.class));
        //new Session(AppIntroduction.this).setFirstTime(false);
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
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

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
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