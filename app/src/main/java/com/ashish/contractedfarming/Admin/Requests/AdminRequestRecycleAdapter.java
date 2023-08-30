package com.ashish.contractedfarming.Admin.Requests;



import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Farmer.Notification.FarmerNotificationAdapter;
import com.ashish.contractedfarming.Models.RequestModel;
import com.ashish.contractedfarming.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminRequestRecycleAdapter extends RecyclerView.Adapter<AdminRequestRecycleAdapter.viewHolder>{
    ArrayList<RequestModel> list;
    Context context;


    public AdminRequestRecycleAdapter(ArrayList<RequestModel> list, Context context) {
        // super();
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_admin_request_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        RequestModel model = list.get(position);

        String[] req_type=model.getType().toUpperCase().split("_");
        String[] req_user=model.getSender_name().toUpperCase().split("_");
        holder.type.setText("REQUEST TYPE :   "+req_type[0]+" ( "+req_type[2]+" ) ");
        holder.sent_by.setText("SENDER : "+req_user[0]+" ( "+req_user[1]+" )");
        holder.date.setText("DATE :  "+ new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(model.getDate_of_creation()) * 1000L));
        holder.id.setText(model.getId());


        if(model.getChecked().equals("00") || model.getChecked().equals("0") ){
            holder.status.setText("STATUS : Not Checked");
        }
        if(model.getChecked().equals("01")){
            holder.status.setText("STATUS : Manager Checked");
        }
        if(model.getChecked().equals("11")){
            holder.status.setText("STATUS : Manager and Admin Checked");
        }
        if(model.getChecked().equals("10")){
            holder.status.setText("STATUS : Admin Checked");

        }

        if(model.getChecked().equals("10") || model.getChecked().equals("11")){
            holder.backColorLayout.setBackgroundColor(Color.TRANSPARENT);
        }else{
            holder.backColorLayout.setBackgroundResource(R.color.md_theme_dark_onSurfaceVariant);
        }



        if(model.getMessage()!=null) {
            holder.message.setText(model.getMessage());
        }else {
            holder.message.setText("---------------");
        }

        // TODO: 8/26/2023 *** have to set for admin and manager individual started

        if(model.getStared().equals("00")){
            holder.starImg.setImageResource(R.drawable.outline_star_unclickerd_24);
        }else{
            holder.starImg.setImageResource(R.drawable.baseline_star_24);
        }
        holder.starImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    //Log.d("Notification clicked",model.getType());
                    mListener.onItemClicked("stared",model.getId(),model.getSender_id(),req_user[1].toLowerCase(),00,model.getStared().equals("11")); // Pass the ID or relevant data
                }
            }
        });

        holder.backColorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                //Log.d("Notification clicked",model.getType());
                mListener.onItemClicked(model.getType_id(),model.getId(),model.getSender_id(),req_user[1].toLowerCase(),Integer.parseInt(model.getChecked()),false); // Pass the ID or relevant data
            }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView status,sent_by,date,id,type,message;

        ImageView starImg;
        LinearLayout backColorLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            status= itemView.findViewById(R.id.admin_request_view_status);
            sent_by= itemView.findViewById(R.id.admin_request_views_to);
            type= itemView.findViewById(R.id.admin_request_views_type);
            date= itemView.findViewById(R.id.admin_request_views_date);
            id= itemView.findViewById(R.id.admin_request_views_id);
            message=itemView.findViewById(R.id.admin_request_views_message);

            starImg=itemView.findViewById(R.id.admin_request_views_image_stared);
            backColorLayout= itemView.findViewById(R.id.admin_request_views_ll);
        }
    }

    private AdminRequestRecycleAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(AdminRequestRecycleAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(String type_id,String req_id,String sender_id,String sender_type,int checked,Boolean bool);
    }
}