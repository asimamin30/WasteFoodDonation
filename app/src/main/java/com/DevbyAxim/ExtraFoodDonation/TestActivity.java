package com.DevbyAxim.ExtraFoodDonation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.Models.UserModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private ArrayList<UserModel> usermodellist;
    Double lat;
    Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        usermodellist = new ArrayList<>();

        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.orderByChild("userType").equalTo("Donor")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        usermodellist.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {

                            UserModel modelShop = ds.getValue(UserModel.class);
                            usermodellist.add(modelShop);
                            lat=modelShop.getLatitude();
                            lon=modelShop.getLongitude();
//                            lat= Double.valueOf(modelShop.getLatitude());
//                            lon= Double.valueOf(modelShop.getLongitude());

                            Log.d("lat",lat.toString());
//                            Log.d("lon",lon.toString());



                        }
                        //setup adapter
                        Log.d("lat",lat.toString());
//                        Log.d("lon",lon.toString());
//                        Toast.makeText(getApplicationContext(), "whts", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}