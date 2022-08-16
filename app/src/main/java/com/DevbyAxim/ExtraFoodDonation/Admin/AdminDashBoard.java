package com.DevbyAxim.ExtraFoodDonation.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.common.RegistrationActivity;

public class AdminDashBoard extends AppCompatActivity {

    RelativeLayout ReceiverProfiles,DonorProfiles,RegisterNgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        ReceiverProfiles=findViewById(R.id.ReceiverProfiles);
        DonorProfiles=findViewById(R.id.DonorProfiles);
        RegisterNgo=findViewById(R.id.RegisterNgo);


        ReceiverProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), com.DevbyAxim.ExtraFoodDonation.Admin.ReceiverProfiles.class);
                startActivity(intent);


            }
        });
        DonorProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.DevbyAxim.ExtraFoodDonation.Admin.DonorProfiles.class);
                startActivity(intent);



            }
        });

        RegisterNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);


            }
        });

    }
}