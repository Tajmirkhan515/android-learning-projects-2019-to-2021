package com.ripplesofter.mainPagaework;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.ripplesofter.DetailActivity.Details;
import com.ripplesofter.corona.R;
import com.ripplesofter.questionair.QuestionarActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<Model> personNames;
    Context context;

    public CustomAdapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        //vh.imagev.setImageIcon();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(personNames.get(position).getName());
        holder.imagev.setImageResource(personNames.get(position).getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Log.e("ddd", "onClick: "+personNames.get(position).getName() );
                if(personNames.get(position).getName().equalsIgnoreCase("Corona Test")||personNames.get(position).getName().equalsIgnoreCase("کورونا ٹیسٹ")||personNames.get(position).getName().equalsIgnoreCase("电晕测试") ){
                    Intent intent=new Intent(context, QuestionarActivity.class);
                    intent.putExtra("value",personNames.get(position).getName());
                    context.startActivity(intent);
                }else if(personNames.get(position).getName().equalsIgnoreCase("About Us") || personNames.get(position).getName().equalsIgnoreCase("ہمارے بارے میں") || personNames.get(position).getName().equalsIgnoreCase("关于我们")){
                    aboutUs();
                }
                else if(personNames.get(position).getName().equalsIgnoreCase("Share") || personNames.get(position).getName().equalsIgnoreCase("بانٹیں") || personNames.get(position).getName().equalsIgnoreCase("分享")){
                    share();
                }
                 else if(personNames.get(position).getName().equalsIgnoreCase("Rate Us") || personNames.get(position).getName().equalsIgnoreCase("ہمیں درجہ دیں") || personNames.get(position).getName().equalsIgnoreCase("评价我们")){
                    rateUs();
                }
                else if(personNames.get(position).getName().equalsIgnoreCase("More App") || personNames.get(position).getName().equalsIgnoreCase("اور ایپ") || personNames.get(position).getName().equalsIgnoreCase("更多应用")){
                    moreApp();
                }
                else if(personNames.get(position).getName().equalsIgnoreCase("Contact") ||personNames.get(position).getName().equalsIgnoreCase("ربط")||personNames.get(position).getName().equalsIgnoreCase("联系")){
                    contact();
                }
                 else if(personNames.get(position).getName().equalsIgnoreCase("Privacy Policy") || personNames.get(position).getName().equalsIgnoreCase("رازداری کی پالیسی") || personNames.get(position).getName().equalsIgnoreCase("隐私政策")){
                    privacyPolicy();
                }
                else {
                    Intent intent = new Intent(context, Details.class);
                    intent.putExtra("value", personNames.get(position).getName());
                    context.startActivity(intent);
                    Toast.makeText(context, "Click" + personNames.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        }); }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;// init the item view's
        TextView textView;
        ImageView imagev;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            cardView =  itemView.findViewById(R.id.cardview_id);
            textView=(TextView)itemView.findViewById(R.id.textview_id);
            imagev=(ImageView)itemView.findViewById(R.id.imag_id);
        }
    }

    public void moreApp() {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Ripple+Softer&hl=en")));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void share(){
        try {
            //name = "share";
            // mFirebaseAnalytics.logEvent(name, params);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out this Corona app at: https://play.google.com/store/apps/details?id" + context.getApplicationContext().getPackageName());
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {

        }
    }

    public void rateUs(){
        Uri uri = Uri.parse("market://details?id=" + context.getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName())));
        }
    }

    public void contact(){
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", context.getString(R.string.email), null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Favourite News");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            context.startActivity(Intent.createChooser(intent, "Choose an Email client :"));

        } catch (Exception e) {

        }
    }

    public void privacyPolicy(){
        try {
            Uri uri = Uri.parse("https://sites.google.com/view/agecalculatorwithcategorise"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public void aboutUs(){
        try {
           // Intent i = new Intent(context, Aboutus.class);
            final AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("RippleSofter");
            builder.setMessage(context.getResources().getString(R.string.company));

            AlertDialog dialog=builder.create();
            dialog.show();
            //context.startActivity(i);
        } catch (Exception e) {

        }
    }

}
