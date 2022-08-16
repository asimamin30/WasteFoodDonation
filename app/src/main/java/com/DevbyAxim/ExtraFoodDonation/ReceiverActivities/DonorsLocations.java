package com.DevbyAxim.ExtraFoodDonation.ReceiverActivities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.DevbyAxim.ExtraFoodDonation.Models.UserModel;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorsLocations extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<UserModel> usermodellist;
    Double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_locations);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


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

                            Log.d("lat",lat.toString());
                            Log.d("lon",lon.toString());

                            mMap = googleMap;
                            // Add a marker in Sydney and move the camera
                            LatLng TutorialsPoint = new LatLng(lat, lon);
                            mMap.addMarker(new
                                    MarkerOptions().position(TutorialsPoint).title("Donors Locations"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));


                        }
                        //setup adapter
                        Log.d("lat",lat.toString());
                        Log.d("lon",lon.toString());


//                        Toast.makeText(DonorsLocations.this, "whts", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}