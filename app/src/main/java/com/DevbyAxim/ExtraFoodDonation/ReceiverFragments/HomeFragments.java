package com.DevbyAxim.ExtraFoodDonation.ReceiverFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.AllDonorActivity;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.DonorFoodActivity;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.DonorsLocations;


public class HomeFragments extends Fragment {
    RelativeLayout Donorlist,DonorReq,ReceiverLoc;
    private SharedPreferences loginPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_fragments, container, false);


        Donorlist =view.findViewById(R.id.donorcard);
        DonorReq =view.findViewById(R.id.DonorReq);
        ReceiverLoc=view.findViewById(R.id.ReceiverLoc);
        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String prefUsername=loginPreferences.getString("un", "");

        Donorlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllDonorActivity.class);
                startActivity(intent);
            }
        });

        DonorReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DonorFoodActivity.class);
                startActivity(intent);
            }
        });
        ReceiverLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DonorsLocations.class);
                startActivity(intent);
            }
        });

        return view;
    }
}