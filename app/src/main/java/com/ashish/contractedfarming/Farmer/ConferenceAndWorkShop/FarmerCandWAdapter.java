package com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.ashish.contractedfarming.Models.ConferenceModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FarmerCandWAdapter extends RecyclerView.Adapter<FarmerCandWAdapter.viewHolder> {
    Context context;
    ArrayList<ConferenceModel> list;

    public FarmerCandWAdapter(Context context, ArrayList<ConferenceModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FarmerCandWAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_conferences, parent, false);

        return new FarmerCandWAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerCandWAdapter.viewHolder holder, int position) {
        ConferenceModel model = list.get(position);
        //holder.newspic.setImageResource(R.drawable.news);

        if(model.getConf_img_url()!=" ") {
            Picasso.get().load(model.getConf_img_url()).into(holder.conf_img);
        }else {
            holder.conf_img.setImageResource(R.drawable.logo);
        }
        holder.conf_title.setText(model.getConf_topic());
        holder.conf_by.setText("Hosted By : "+model.getBy_name());

        holder.conf_date.setText(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getConf_date()) * 1000L));

        holder.conf_address.setText(model.getConf_venue());


        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIntent(model);
            }
        });



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup_conference, null);


                TextView title, by,at,date;
                ImageView imageView;

                ImageButton share,copy;


                title = promptsView.findViewById(R.id.popup_conf_text_topic);
                 by= promptsView.findViewById(R.id.popup_conf_text_by);
                at = promptsView.findViewById(R.id.popup_conf_text_at);
                date = promptsView.findViewById(R.id.popup_conf_text_date);


                imageView = promptsView.findViewById(R.id.popup_conf_img);

                share=promptsView.findViewById(R.id.popup_conf_img_button_share);
                copy=promptsView.findViewById(R.id.popup_conf_img_button_copy);


                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareIntent(model);

                    }
                });



                title.setText(model.getConf_topic());
                date.setText(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getConf_date()) * 1000L));

                if(model.getConf_img_url()!=" ") {
                    Picasso.get().load(model.getConf_img_url()).into(imageView);
                }else {
                    holder.conf_img.setImageResource(R.drawable.logo);
                }


                at.setText("On : "+model.getConf_venue());
                by.setText("Hosted By : "+model.getBy_name());
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
        TextView conf_title,conf_by,conf_address,conf_date;
        ImageView conf_img;
        CardView cardView;

        ImageButton share_button, copy_button;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= itemView.findViewById(R.id.view_conf_cardview);
            conf_title = itemView.findViewById(R.id.view_conf_text_topic);
            conf_by = itemView.findViewById(R.id.view_conf_text_by);
            conf_address = itemView.findViewById(R.id.view_conf_text_at);
            conf_date = itemView.findViewById(R.id.view_conf_text_date);
            conf_img = itemView.findViewById(R.id.view_conf_img_post);

            share_button = itemView.findViewById(R.id.view_conf_img_button_share);
            copy_button = itemView.findViewById(R.id.view_conf_img_button_share);
        }
    }


    public void shareIntent(ConferenceModel model){
        //Uri imageUri = Uri.parse(pictureFile.getAbsolutePath());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        //Target whatsapp:
        //shareIntent.setPackage("com.whatsapp");
        //Add text and then Image URI
        shareIntent.putExtra(Intent.EXTRA_TITLE, model.getConf_topic());
        shareIntent.putExtra(Intent.EXTRA_TEXT,model.getConf_topic()+"\n"+ "By : "+model.getBy_name()+"\n"+ model.getConf_venue()+"\n"+ new SimpleDateFormat("dd MMM yyyy hh:mm a").format(Long.parseLong(model.getConf_date()) * 1000L));
        //shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(model.getConf_img_url()));
        //shareIntent.setData(Uri.parse(model.getConf_img_url()));
        //shareIntent.setType("image/jpeg");
        shareIntent.setType("text/plain");
        //shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, model.getConf_topic()));
    }
}
