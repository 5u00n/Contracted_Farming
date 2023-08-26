package com.ashish.contractedfarming.Farmer.Notification;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ashish.contractedfarming.Models.NotificationModel;
import com.ashish.contractedfarming.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerNotificationAdapter extends RecyclerView.Adapter<FarmerNotificationAdapter.viewHolder> {
    Context context;
    ArrayList<NotificationModel> list;

    private FarmerNotificationAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(FarmerNotificationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public FarmerNotificationAdapter(Context context, ArrayList<NotificationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FarmerNotificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_notification, parent, false);

        return new FarmerNotificationAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerNotificationAdapter.viewHolder holder, int position) {
        NotificationModel model = list.get(position);
        //holder.newspic.setImageResource(R.drawable.news);

        holder.creator.setText("From : "+model.getCreator_type().toUpperCase());
        holder.message.setText(model.getCreator()+" "+model.getMessage());

        holder.date.setText(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getDate_created()) * 1000L));

        if(model.seen.equals("false")){
            holder.ll.setBackgroundResource(R.color.md_theme_dark_onSurfaceVariant);
        }else{
            holder.ll.setBackgroundColor(Color.TRANSPARENT);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mListener != null) {
                    //Log.d("Notification clicked",model.getType());
                    mListener.onItemClicked(model.getNot_id(),model.getType(),model.getCreator()); // Pass the ID or relevant data
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView creator, message, date;
        CardView cardView;

        LinearLayout ll;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.view_farmer_notification_cardview);
            creator = itemView.findViewById(R.id.view_farmer_notification_from);
            message = itemView.findViewById(R.id.view_farmer_notification_txt);
            date = itemView.findViewById(R.id.view_farmer_notification_date);

            ll= itemView.findViewById(R.id.view_farmer_notification_ll);
        }
    }
    public interface OnItemClickListener {
        void onItemClicked(String not_id,String notification_type,String creator);
    }
}
