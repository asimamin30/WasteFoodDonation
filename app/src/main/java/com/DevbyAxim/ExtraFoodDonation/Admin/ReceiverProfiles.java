package com.DevbyAxim.ExtraFoodDonation.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.ReceiverModel;
import com.DevbyAxim.ExtraFoodDonation.Models.UserModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceiverProfiles extends AppCompatActivity {
    private SharedPreferences loginPreferences;
    private RecyclerView rcvRv;
    private ArrayList<ReceiverModel> receiverList;
    private GetReceiverProfiles getReceiverProfiles;
    String Username;
    Double lat,lon;
    private ArrayList<UserModel> usermodellist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_profiles);


        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        Username=loginPreferences.getString("un", "");
        rcvRv = findViewById(R.id.recRv);
        Log.d("user",Username);
        loadAllReceiver();
        usermodellist = new ArrayList<>();
    }

    private void loadAllReceiver() {

        receiverList = new ArrayList<>();

        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.orderByChild("userType").equalTo("Receiver")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        receiverList.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            ReceiverModel modelShop = ds.getValue(ReceiverModel.class);
                            receiverList.add(modelShop);
                            Log.d("receiverlist",Username.toString());
                        }
                        //setup adapter
                        getReceiverProfiles = new GetReceiverProfiles(ReceiverProfiles.this,receiverList);
                        //set adapter to recyclerview
                        rcvRv.setAdapter(getReceiverProfiles);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    }
