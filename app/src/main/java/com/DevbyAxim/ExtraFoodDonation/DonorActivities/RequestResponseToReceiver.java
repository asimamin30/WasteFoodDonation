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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RequestResponseToReceiver extends AppCompatActivity {
    String name, email, phone, address,Username,userType;
    TextView RequestStatus;
    String description,Response;
    EditText req_status;
    private SharedPreferences loginPreferences;
    String DonateFoodRequestId,DonateFoodRequestTime,UserName,RequestDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_response_to_receiver);

        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        name = loginPreferences.getString("un", "");

        req_status=findViewById(R.id.req_status);
        RequestStatus=findViewById(R.id.accept_Request);

        Intent i = getIntent();
        DonateFoodRequestId = i.getStringExtra("DonateFoodRequestId");
        DonateFoodRequestTime = i.getStringExtra("DonateFoodRequestTime");
        UserName = i.getStringExtra("name");
        RequestDescription = i.getStringExtra("des");
        userType = i.getStringExtra("ut");
        Response=req_status.getText().toString();
        RequestStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response=req_status.getText().toString();
                if (Response.length()==0){
                    req_status.setError("please write Yes");
                }else{
                    submitRequest();
                }
            }
        });


    }

    private void submitRequest() {

        //For order id and order time
        String timestamp = "" + System.currentTimeMillis();

        //setup order data
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("DonateFoodRequestId", "" + DonateFoodRequestId);
        hashMap.put("DonateFoodRequestTime", "" + DonateFoodRequestTime);
        hashMap.put("RequestDescription", "" + RequestDescription);
        hashMap.put("Response", "" + Response);
        hashMap.put("UserName", "" + UserName);
        hashMap.put("userType", "" + userType);

        //add to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials").child(Username).child("ReceiverRequestForFood");
        ref.child(DonateFoodRequestId).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //order info added now add order items
                        Toast.makeText(getApplicationContext(), "  Request  Response send Successfully...", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(getApplicationContext(), AllReceiverActivity.class);
//                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed placing order
                        Toast.makeText(getApplicationContext(), "Request Response Fail...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}