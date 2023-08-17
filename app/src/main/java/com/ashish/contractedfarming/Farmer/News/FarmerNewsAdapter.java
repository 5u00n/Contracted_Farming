package com.ashish.contractedfarming.Farmer.News;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FarmerNewsAdapter extends RecyclerView.Adapter<FarmerNewsAdapter.viewHolder> {
    Context context;
    ArrayList<AdminNewsModel> list;

    public FarmerNewsAdapter(Context context, ArrayList<AdminNewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FarmerNewsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_news, parent, false);

        return new FarmerNewsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerNewsAdapter.viewHolder holder, int position) {
        AdminNewsModel model = list.get(position);
        //holder.newspic.setImageResource(R.drawable.news);

        Picasso.get().load(model.getImgurl()).into(holder.newspic);
        holder.id.setText(model.getId());
        holder.topic.setText(model.getTopic());

        holder.time.setText((CharSequence) new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getDate()) * 1000L));

        holder.data.setVisibility(View.GONE);

        if(model.getData().length()>50) {
            holder.data.setText(model.getData().substring(0, 50) + "... ");
        }else {
            holder.data.setText(model.getData()+ "... ");
        }

        holder.newspic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup_news, null);


                TextView id, topic, date, data, imgurl;
                ImageView imageView;
                id = promptsView.findViewById(R.id.popup_news_id);
                topic = promptsView.findViewById(R.id.popup_news_title);
                date = promptsView.findViewById(R.id.popup_news_time);
                data = promptsView.findViewById(R.id.popup_news_description);


                imageView = promptsView.findViewById(R.id.popup_news_img);


                id.setText(model.getId());
                data.setText(model.getData());
                date.setText((CharSequence) new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getDate()) * 1000L));

                    Picasso.get().load(model.getImgurl()).into(imageView);


                topic.setText(model.getTopic());
                //topic.setVisibility(View.GONE);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                //alertDialogBuilder.setTitle(model.getTopic());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                // set dialog message
                alertDialogBuilder.setCancelable(false).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView time, id, topic,data;
        ImageView newspic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.view_farmer_news_time);
            id = itemView.findViewById(R.id.view_farmer_news_id);
            topic = itemView.findViewById(R.id.view_farmer_news_title);
            newspic = itemView.findViewById(R.id.view_farmer_news_img);
            data=itemView.findViewById(R.id.view_farmer_news_data);
        }
    }
}
