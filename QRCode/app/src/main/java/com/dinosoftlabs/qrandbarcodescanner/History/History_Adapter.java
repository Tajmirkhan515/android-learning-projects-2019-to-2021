package com.dinosoftlabs.qrandbarcodescanner.History;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinosoftlabs.qrandbarcodescanner.R;

import java.util.ArrayList;

/**
 * Created by AQEEL on 3/20/2018.
 */

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.CustomViewHolder > {
    public Context context;
    ArrayList<History_GetSet> user_dataList = new ArrayList<>();

    private History_Adapter.OnItemClickListener listener;


    // meker the onitemclick listener interface and this interface is impliment in Chatinbox activity
    // for to do action when user click on item
    public interface OnItemClickListener {
        void onItemClick(History_GetSet item);
    }

    public History_Adapter(Context context, ArrayList<History_GetSet> user_dataList, History_Adapter.OnItemClickListener listener) {
        this.context = context;
        this.user_dataList=user_dataList;
        this.listener = listener;
    }

    @Override
    public History_Adapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_adapter,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        History_Adapter.CustomViewHolder viewHolder = new History_Adapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
       return user_dataList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView type,data,date;
        ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
         //   this.type=itemView.findViewById(R.id.type);

            this.data=itemView.findViewById(R.id.data);

            this.date=itemView.findViewById(R.id.datetxt);

            this.imageView=itemView.findViewById(R.id.imageview);

        }

        public void bind(final History_GetSet item, final History_Adapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }


    }

    @Override
    public void onBindViewHolder(final History_Adapter.CustomViewHolder holder, final int i) {
        final History_GetSet item=user_dataList.get(i);
      //  holder.type.setText(item.getType());
        holder.data.setText(item.getData());
        holder.date.setText(item.getDate());
        Linkify.addLinks(holder.data, Linkify.WEB_URLS);
        if(item.getImage()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.imageView.setTag(i);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                OpenfullsizeImage(user_dataList.get(pos));
            }
        });

        holder.bind(item,listener);

   }


    //this method will big the size of image in private chat
    public void OpenfullsizeImage(History_GetSet item){
        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });

        ImageView imageView = new ImageView(context);
        if(item.getImage()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            imageView.setImageBitmap(bitmap);
        }

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        builder.show();
    }


}