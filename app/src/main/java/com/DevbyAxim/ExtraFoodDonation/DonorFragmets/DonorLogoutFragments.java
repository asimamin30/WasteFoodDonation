package com.DevbyAxim.ExtraFoodDonation.DonorFragmets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.common.LoginActivity;


public class DonorLogoutFragments extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donor_logout_fragments, container, false);
         Button btn=view.findViewById(R.id.Logout);
             btn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(getActivity(), LoginActivity.class);
                     DonorLogoutFragments.this.startActivity(intent);
                     getActivity().finish();
                 }
             });

        return view;
    }
}