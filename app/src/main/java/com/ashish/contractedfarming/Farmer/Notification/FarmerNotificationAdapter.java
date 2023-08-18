package com.ashish.contractedfarming.Farmer.Notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerNotificationAdapter extends RecyclerView.Adapter<FarmerNotificationAdapter.viewHolder> {
        Context context;
        ArrayList<FarmerNotificationModel> list;

public FarmerNotificationAdapter(Context context, ArrayList<FarmerNotificationModel> list) {
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
        FarmerNotificationModel model = list.get(position);
        //holder.newspic.setImageResource(R.drawable.news);

        holder.creator.setText(model.getCreator());
        holder.message.setText(model.getMessage());

        holder.date.setText(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getDate_created()) * 1000L));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getType()){
                    case "farm":
                        //context.startActivity(new Intent(context,));
                        break;
                    case "farmer_plant":
                        break;
                    case "plant":
                        break;
                    case "posts":
                        break;
                    case "conference":
                        break;
                    case "news":
                        break;
                }
            }
        });


        }

@Override
public int getItemCount() {
        return list.size();
        }

public class viewHolder extends RecyclerView.ViewHolder {
    TextView creator,message,date;
    CardView cardView;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        cardView= itemView.findViewById(R.id.view_farmer_notification_cardview);
        creator= itemView.findViewById(R.id.view_farmer_notification_from);
        message = itemView.findViewById(R.id.view_farmer_notification_txt);
        date = itemView.findViewById(R.id.view_farmer_notification_date);
    }
}
}
