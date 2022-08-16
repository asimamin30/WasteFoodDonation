package com.DevbyAxim.ExtraFoodDonation.DonorFragmets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.DevbyAxim.ExtraFoodDonation.DonorActivities.AllReceiverActivity;
import com.DevbyAxim.ExtraFoodDonation.DonorActivities.ReceiverFoodRequestActivity;
import com.DevbyAxim.ExtraFoodDonation.DonorActivities.ReceiverLocations;
import com.DevbyAxim.ExtraFoodDonation.R;

public class DonorHomeFragments extends Fragment {
    RelativeLayout receiverprof,DonorReq,ReceiverLoc;
    private SharedPreferences loginPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donor_home_fragments, container, false);

        receiverprof=view.findViewById(R.id.receiverCard);
        DonorReq=view.findViewById(R.id.ReceiverReq);
        ReceiverLoc=view.findViewById(R.id.ReceiverLoc);
        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String prefUsername=loginPreferences.getString("un", "");

        receiverprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllReceiverActivity.class);
                startActivity(intent);
            }
        });
        DonorReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReceiverFoodRequestActivity.class);
                startActivity(intent);
            }
        });
        ReceiverLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReceiverLocations.class);
                startActivity(intent);
            }
        });

        return view;
    }
}