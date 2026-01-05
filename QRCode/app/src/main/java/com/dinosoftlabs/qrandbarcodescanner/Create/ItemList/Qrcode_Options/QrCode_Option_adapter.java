package com.dinosoftlabs.qrandbarcodescanner.Create.ItemList.Qrcode_Options;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinosoftlabs.qrandbarcodescanner.R;

import java.util.ArrayList;

/**
 * Created by AQEEL on 8/27/2018.
 */

public class QrCode_Option_adapter extends RecyclerView.Adapter<QrCode_Option_adapter.CustomViewHolder > {
    public Context context;
    ArrayList<QrCode_Option_GetSet> user_dataList = new ArrayList<>();
    private QrCode_Option_adapter.OnItemClickListener listener;


    // meker the onitemclick listener interface and this interface is impliment in Chatinbox activity
    // for to do action when user click on item
    public interface OnItemClickListener {
        void onItemClick(QrCode_Option_GetSet item);
    }

    public QrCode_Option_adapter(Context context, ArrayList<QrCode_Option_GetSet> user_dataList, QrCode_Option_adapter.OnItemClickListener listener) {
        this.context = context;
        this.user_dataList=user_dataList;
        this.listener = listener;
    }

    @Override
    public QrCode_Option_adapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_qr_option,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        QrCode_Option_adapter.CustomViewHolder viewHolder = new QrCode_Option_adapter.CustomViewHolder(view);
        return viewHolder;
    }



    @Override
    public int getItemCount() {
        return user_dataList.size();
    }



    class CustomViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView name;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView=itemView.findViewById(R.id.imageview);
            this.name=itemView.findViewById(R.id.name);

        }

        public void bind(final QrCode_Option_GetSet item, final QrCode_Option_adapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }


    }


    @Override
    public void onBindViewHolder(final QrCode_Option_adapter.CustomViewHolder holder, final int i) {
        final QrCode_Option_GetSet item=user_dataList.get(i);
        holder.bind(item,listener);
        holder.imageView.setImageDrawable(item.getPath());
        holder.name.setText(item.getName());
    }


}
