package com.dinosoftlabs.qrandbarcodescanner.Main_Menu;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinosoftlabs.qrandbarcodescanner.Create.Create_F.Create_F;
import com.dinosoftlabs.qrandbarcodescanner.History.History_f;
import com.dinosoftlabs.qrandbarcodescanner.Main_Menu.RelateToFragment_OnBack.OnBackPressListener;
import com.dinosoftlabs.qrandbarcodescanner.R;
import com.dinosoftlabs.qrandbarcodescanner.Scan.Scan_f;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class MainMenuFragment extends Fragment {

    protected TabLayout tabLayout;

    protected Custom_ViewPager pager;

    private ViewPagerAdapter adapter;
    Context context;


    private InterstitialAd mInterstitialAd;
    boolean is_add_show=false;

    public MainMenuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        context=getContext();
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(4);
        pager.setPagingEnabled(false);
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

        // this is test app id you will get the actual id when you add app in your
        //add mob account
        MobileAds.initialize(context,
                "ca-app-pub-3940256099942544~3347511713");


        //code for intertial add
        mInterstitialAd = new InterstitialAd(context);

        //here we will get the add id keep in mind above id is app id and below Id is add Id
        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.my_Interstitial_Add));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Note that we are passing childFragmentManager, not FragmentManager
        adapter = new ViewPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        setupTabIcons();

           }

    private void setupTabIcons() {
        View view1 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView1= view1.findViewById(R.id.image);
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_scan_orange));
        TextView textView1=view1.findViewById(R.id.text);
        textView1.setText("Scan");
        textView1.setTextColor(getResources().getColor(R.color.orange));
        tabLayout.getTabAt(0).setCustomView(view1);

        View view2 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView2= view2.findViewById(R.id.image);
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_history_gray));
        TextView textView=view2.findViewById(R.id.text);
        textView.setText("History");
        textView.setTextColor(getResources().getColor(R.color.black));
        tabLayout.getTabAt(1).setCustomView(view2);


        View view3 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView3= view3.findViewById(R.id.image);
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_gray));
        TextView textView3=view3.findViewById(R.id.text);
        textView3.setText("Create");
        textView3.setTextColor(getResources().getColor(R.color.black));
        tabLayout.getTabAt(2).setCustomView(view3);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 View v=tab.getCustomView();
                 ImageView image=v.findViewById(R.id.image);
                 switch (tab.getPosition()){
                     case 0:
                         image.setImageDrawable(getResources().getDrawable(R.drawable.ic_scan_orange));
                         break;
                     case 1:
                         image.setImageDrawable(getResources().getDrawable(R.drawable.ic_history_orange));
                         if(!is_add_show){
                             if (mInterstitialAd.isLoaded()) {
                                 is_add_show=true;
                                 mInterstitialAd.show();
                             }
                         }
                         break;
                     case 2:
                         image.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_orange));
                         break;
                 }
                TextView tv =v.findViewById(R.id.text);
                tv.setTextColor(getResources().getColor(R.color.orange));
                tab.setCustomView(v);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            View v=tab.getCustomView();
                ImageView image=v.findViewById(R.id.image);
                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_scan_gray));
                        break;
                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_history_gray));
                        break;
                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_gray));
                        break;
                    case 3:
                }
             TextView tv =v.findViewById(R.id.text);
             tv.setTextColor(getResources().getColor(R.color.black));
             tab.setCustomView(v);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(pager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final Resources resources;

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


        public ViewPagerAdapter(final Resources resources, FragmentManager fm) {
            super(fm);
            this.resources = resources;
        }

        @Override
        public Fragment getItem(int position) {
            final Fragment result;
            switch (position) {
                case 0:
                    result = new Scan_f();
                    break;
                case 1:
                    result = new History_f();
                    break;
                case 2:
                    result = new Create_F();
                    break;
                default:
                    result = null;
                    break;
            }

            return result;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            switch (position) {
                case 0:
                    return "Scan";
                case 1:
                    return "History";
                case 2:
                    return "Create";
                    default:
                    return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }


        /**
         * Get the Fragment by position
         *
         * @param position tab position of the fragment
         * @return
         */
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }


}