package com.ashish.contractedfarming.Admin.Dashboard.Manager;

import android.content.Context;
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

import java.util.List;

public class AdminManagerAdapter extends ArrayAdapter<AdminManagerModel> {
    public AdminManagerAdapter(@NonNull Context context, List<AdminManagerModel> arr) {
        super(context,0,arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdminManagerModel a= getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.view_admin_agents,parent,false);
        TextView nm, pl,id;
        nm= convertView.findViewById(R.id.adminagentname);
        pl= convertView.findViewById(R.id.adminagentplace);
        id=convertView.findViewById(R.id.admin_agent_id);
        ImageView img1 = convertView.findViewById(R.id.adminagenticon);
        ImageView img2 = convertView.findViewById(R.id.adminagentplay);
        img2.setImageResource(R.drawable.baseline_play_arrow_24);
        nm.setText(a.name);
        pl.setText(a.place);
        id.setText(a.id);

        if(a.getImgurl()!=null) {
            Picasso.get().load(a.getImgurl()).into(img1);
        }
        return convertView;
    }
}
