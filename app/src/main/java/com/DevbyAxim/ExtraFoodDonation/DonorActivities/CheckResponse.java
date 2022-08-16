package com.DevbyAxim.ExtraFoodDonation.DonorActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.DonorAdapter.ResponseAdapter;
import com.DevbyAxim.ExtraFoodDonation.Models.RcFoodReqModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckResponse extends AppCompatActivity {
    private SharedPreferences loginPreferences;
    private RecyclerView rcvRv;
    private ArrayList<RcFoodReqModel> rcFoodReqModels;
    private ResponseAdapter responseAdapter;
    String uu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_response);


        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        uu = loginPreferences.getString("un", "");
        rcvRv = findViewById(R.id.foodrec);
        Log.d("user", uu);

        LoadFoodRequestFromReceiver();

    }
        private void LoadFoodRequestFromReceiver() {


            rcFoodReqModels = new ArrayList<>();

            //get all shops
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials").child(uu).child("ReceiverSendResponseAboutFood");
            reference.orderByChild("userType").equalTo("Receiver")
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
                            responseAdapter = new ResponseAdapter(CheckResponse.this,rcFoodReqModels);
                            //set adapter to recyclerview
                            rcvRv.setAdapter(responseAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




        }
    }