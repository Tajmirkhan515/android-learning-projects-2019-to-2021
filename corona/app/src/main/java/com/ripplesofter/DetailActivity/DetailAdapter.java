package com.ripplesofter.DetailActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ripplesofter.corona.R;
import com.ripplesofter.mainPagaework.Model;

import java.util.ArrayList;

import android.content.Context;

        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import com.ripplesofter.DetailActivity.Details;
        import com.ripplesofter.corona.R;

        import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    String[] array;
    Context context;
    public DetailAdapter(Context context, String[] array) {
        this.context = context;
        this.array = array;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showmessage, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        //vh.imagev.setImageIcon();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

          holder.textView.setText(array[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
//                Intent intent=new Intent(context, Details.class);
//                intent.putExtra("value",personNames.get(position).getName());
//                context.startActivity(intent);
                Toast.makeText(context, "Click"+array[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's

            textView=(TextView)itemView.findViewById(R.id.message_id);

        }
    }
}
