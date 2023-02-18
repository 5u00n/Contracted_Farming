package com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantPopUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ashish.contractedfarming.R;

import androidx.fragment.app.Fragment;

public class PlantPopupFirstFragment extends Fragment {


    public PlantPopupFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_popup_first, container, false);
    }
}