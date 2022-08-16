package com.DevbyAxim.ExtraFoodDonation.ReceiverActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.DonorActivities.ReceiverFoodRequestActivity;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FoodResponseToDonor extends AppCompatActivity {
    String name, email, phone, address,Username;
    TextView nam, eml, ph, add, donatereq,RequestStatus;
    String description,Response;
    EditText des;
    private SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_response_to_donor);

        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        Username = loginPreferences.getString("un", "");

        nam = findViewById(R.id.username);
        eml = findViewById(R.id.email);
        ph = findViewById(R.id.phone);
        add = findViewById(R.id.address);
        donatereq = findViewById(R.id.DonateFoodRequest);
        des = findViewById(R.id.des);
        RequestStatus = findViewById(R.id.RequestStatus);


        Intent i = getIntent();
        name = i.getStringExtra("name");
        email = i.getStringExtra("email");
        phone = i.getStringExtra("phone");
        address = i.getStringExtra("address");


        nam.setText(name);
        eml.setText(email);
        ph.setText(phone);
        add.setText(address);


        RequestStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ReceiverFoodRequestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        donatereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                description = des.getText().toString();
                if (description.length()==0){
                    des.setError("Please Enter Request");
                }else{ submitRequest();}

            }
        });

    }

    private void submitRequest() {

        //For order id and order time
        String timestamp = "" + System.currentTimeMillis();

        //setup order data
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("DonateFoodRequestId", "" + timestamp);
        hashMap.put("DonateFoodRequestTime", "" + timestamp);
        hashMap.put("RequestDescription", "" + description);
        hashMap.put("Response", "" + Response);
        hashMap.put("UserName", "" + Username);
        hashMap.put("userType", "" + "Receiver");

        //add to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials").child(name).child("ReceiverSendResponseAboutFood");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //order info added now add order items
                        Toast.makeText(getApplicationContext(), " Response send Successfully...", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), AllDonorActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed placing order
                        Toast.makeText(getApplicationContext(), " Response Fail...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}