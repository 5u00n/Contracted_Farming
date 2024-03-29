package com.ashish.contractedfarming.Farmer.Dashboard.MyRequest.FragmentHelper;



import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Models.RequestModel;
import com.ashish.contractedfarming.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FarmerMyRequestRecycleAdapter extends RecyclerView.Adapter<FarmerMyRequestRecycleAdapter.viewHolder>{
    ArrayList<RequestModel> list;
    Context context;


    public FarmerMyRequestRecycleAdapter(ArrayList<RequestModel> list, Context context) {
        // super();
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FarmerMyRequestRecycleAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_my_request_list,parent,false);
        return new FarmerMyRequestRecycleAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerMyRequestRecycleAdapter.viewHolder holder, int position) {
        RequestModel model = list.get(position);

        String[] req_type=model.getType().toUpperCase().split("_");
        holder.type.setText("REQUEST TYPE :   "+req_type[0]+" ( "+req_type[2]+" ) ");
        holder.sent_to.setText("SENT TO : "+model.getSend_to().toUpperCase());
        holder.date.setText("DATE :  "+ new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(model.getDate_of_creation()) * 1000L));
        holder.id.setText(model.getId());
        holder.status.setText("STATUS : "+model.getChecked());

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

        if(model.getStared().equals("00")){
            holder.starImg.setImageResource(R.drawable.outline_star_unclickerd_24);
        }else{
            holder.starImg.setImageResource(R.drawable.baseline_star_24);
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView status,sent_to,date,id,type;
        ImageView starImg;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            status= itemView.findViewById(R.id.farmer_my_request_view_status);
            sent_to= itemView.findViewById(R.id.farmer_my_request_views_to);
            type= itemView.findViewById(R.id.farmer_my_request_views_type);
            date= itemView.findViewById(R.id.farmer_my_request_views_date);
            id= itemView.findViewById(R.id.farmer_my_request_views_id);
            starImg= itemView.findViewById(R.id.farmer_my_request_star_img);
        }
    }
}