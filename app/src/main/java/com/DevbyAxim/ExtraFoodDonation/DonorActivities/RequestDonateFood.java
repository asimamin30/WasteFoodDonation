package com.DevbyAxim.ExtraFoodDonation.DonorActivities;

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

import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverActivities.DonorFoodActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RequestDonateFood extends AppCompatActivity {


    String name, email, phone, address,Username,userType;
    TextView nam, eml, ph, add, donatereq,RequestStatus;
    String description,Response;
    EditText des;
    private SharedPreferences loginPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_donate_food);

        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        Username = loginPreferences.getString("un", "");

        nam = findViewById(R.id.username);
        eml = findViewById(R.id.email);
        ph = findViewById(R.id.phone);
        add = findViewById(R.id.address);
        RequestStatus = findViewById(R.id.RequestStatus);
        donatereq = findViewById(R.id.DonateFoodRequest);
        des = findViewById(R.id.des);


        Intent i = getIntent();
        name = i.getStringExtra("name");
        email = i.getStringExtra("email");
        phone = i.getStringExtra("phone");
        address = i.getStringExtra("address");


        nam.setText(name);
        eml.setText(email);
        ph.setText(phone);
        add.setText(address);



        donatereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                description = des.getText().toString();
                if (description.length()==0){
                    des.setError("Please Enter Request");
                }else{ submitRequest();}

            }
        });

        RequestStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DonorFoodActivity.class);
                startActivity(intent);
                finish();
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
        hashMap.put("userType", "" + "Donor");

        //add to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials").child(name).child("DonorRequestForDonateFood");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //order info added now add order items
                        Toast.makeText(getApplicationContext(), " Food Donate Request send Successfully...", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),AllReceiverActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed placing order
                        Toast.makeText(getApplicationContext(), "Food Donate Request Fail...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}