package com.DevbyAxim.ExtraFoodDonation.ReceiverActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.DevbyAxim.ExtraFoodDonation.Models.FoodRequestModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverAdapter.FoodRequestFromDonorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorFoodActivity extends AppCompatActivity {
    private SharedPreferences loginPreferences;
    private SharedPreferences recPreferences;
    private RecyclerView rcvRv;
    private ArrayList<FoodRequestModel> foodreqlist;
    private FoodRequestFromDonorAdapter foodRequestFromDonorAdapter;
    private SharedPreferences.Editor recPrefsEditor;
    private SharedPreferences.Editor loginPrefsEditor;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_food);


        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        Username=loginPreferences.getString("un", "");

        rcvRv = findViewById(R.id.recRv);
        Log.d("user",Username);

        LoadFoodRequestFromDonor();
    }

    private void LoadFoodRequestFromDonor() {

        foodreqlist = new ArrayList<>();

        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials").child(Username).child("DonorRequestForDonateFood");
        reference.orderByChild("userType").equalTo("Donor")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        foodreqlist.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            FoodRequestModel modelShop = ds.getValue(FoodRequestModel.class);
                            foodreqlist.add(modelShop);
                            Log.d("list",foodreqlist.toString());
                        }
                        //setup adapter
                        foodRequestFromDonorAdapter = new FoodRequestFromDonorAdapter(DonorFoodActivity.this,foodreqlist);
                        //set adapter to recyclerview
                        rcvRv.setAdapter(foodRequestFromDonorAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }
}