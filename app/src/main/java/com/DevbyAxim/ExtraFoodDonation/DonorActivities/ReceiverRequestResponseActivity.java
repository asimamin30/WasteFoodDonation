package com.DevbyAxim.ExtraFoodDonation.DonorActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.RcFoodReqModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverAdapter.RequestResponseFromDonorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceiverRequestResponseActivity extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private RecyclerView rcvRv;
    private ArrayList<RcFoodReqModel> rcFoodReqModels;
    private RequestResponseFromDonorAdapter requestResponseFromDonorAdapter;
    String uu;
    String name,email,phone,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_request_response);

        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        uu=loginPreferences.getString("un", "");
        rcvRv = findViewById(R.id.foodrec);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        email = i.getStringExtra("email");
        phone = i.getStringExtra("phone");
        address = i.getStringExtra("address");

        Log.d("user",uu);

        loadResponse();
    }

    private void loadResponse() {


        rcFoodReqModels = new ArrayList<>();

        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials").child(name).child("DonorRequestForDonateFood");
        reference.orderByChild("userType").equalTo("Donor")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        rcFoodReqModels.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            RcFoodReqModel modelShop = ds.getValue(RcFoodReqModel.class);
                            rcFoodReqModels.add(modelShop);
                            Log.d("fls",rcFoodReqModels.toString());
                        }
                        //setup adapter
                        requestResponseFromDonorAdapter = new RequestResponseFromDonorAdapter(ReceiverRequestResponseActivity.this,rcFoodReqModels);
                        //set adapter to recyclerview
                        rcvRv.setAdapter(requestResponseFromDonorAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }
    }
