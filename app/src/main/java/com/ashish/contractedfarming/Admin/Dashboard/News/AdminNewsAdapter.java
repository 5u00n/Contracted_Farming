package com.ashish.contractedfarming.Admin.Dashboard.News;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminNewsAdapter extends ArrayAdapter<AdminNewsModel> {
    public AdminNewsAdapter(@NonNull Context context, List<AdminNewsModel> arr) {
        super(context, 0, arr);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdminNewsModel a = getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.view_admin_news,parent,false);
        TextView  pl,time;
        pl= convertView.findViewById(R.id.view_admin_news_title);
        ImageView img1 = convertView.findViewById(R.id.view_admin_news_img);
        time= convertView.findViewById(R.id.view_admin_news_time);
        pl.setText(a.topic);
         time.setText(getDate(Long.parseLong(a.date)));
        if(a.getImgurl()!=null) {
            Picasso.get().load(a.getImgurl()).into(img1);
        }
        return convertView;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
