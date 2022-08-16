package com.DevbyAxim.ExtraFoodDonation.ReceiverActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.DonorModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverAdapter.GetDonorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllDonorActivity extends AppCompatActivity {
    private SharedPreferences loginPreferences;
    private RecyclerView rcvRv;
    private ArrayList<DonorModel> donorList;
    private GetDonorAdapter geDonorAdapter;
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donor);

        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        Username=loginPreferences.getString("un", "");
        rcvRv = findViewById(R.id.recRv);
        Log.d("user",Username);


        loadAllDonors();

    }

    private void loadAllDonors() {


        donorList = new ArrayList<>();

        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.orderByChild("userType").equalTo("Donor")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        donorList.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            DonorModel modelShop = ds.getValue(DonorModel.class);
                            donorList.add(modelShop);
                            Log.d("receiverlist",Username.toString());
                        }
                        //setup adapter
                        geDonorAdapter = new GetDonorAdapter(AllDonorActivity.this,donorList);
                        //set adapter to recyclerview
                        rcvRv.setAdapter(geDonorAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}