package com.ashish.contractedfarming.Admin.Dashboard.Home;

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

import java.util.ArrayList;

public class CListAdapter extends ArrayAdapter<ListModel>{

    public CListAdapter(@NonNull Context context, ArrayList<ListModel> arrayList) {
        super(context, 0,arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_dash_status, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        ListModel currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.view_dash_list_img);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(currentNumberPosition.getImg());

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.view_dash_list_title);
        textView1.setText(currentNumberPosition.getName());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.view_dash_list_count);
        textView2.setText(currentNumberPosition.getCount());

        // then return the recyclable view
        return currentItemView;
    }
}