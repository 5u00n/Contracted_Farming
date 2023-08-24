package com.ashish.contractedfarming.Admin.Requests;



import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(context).inflate(R.layout.view_my_request_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RequestModel model = list.get(position);

        String[] req_type=model.getType().toUpperCase().split("_");
        holder.type.setText("REQUEST TYPE :   "+req_type[0]+" ( "+req_type[2]+" ) ");
        holder.sent_to.setText("SENT TO : "+model.getSend_to().toUpperCase());
        holder.date.setText("DATE :  "+ new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(model.getDate_of_creation()) * 1000L));
        holder.id.setText(model.getId());
        holder.status.setText("STATUS : "+model.getChecked());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView status,sent_to,date,id,type;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            status= itemView.findViewById(R.id.farmer_my_request_view_status);
            sent_to= itemView.findViewById(R.id.farmer_my_request_views_to);
            type= itemView.findViewById(R.id.farmer_my_request_views_type);
            date= itemView.findViewById(R.id.farmer_my_request_views_date);
            id= itemView.findViewById(R.id.farmer_my_request_views_id);
        }
    }
}