package com.DevbyAxim.ExtraFoodDonation.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetActivity extends AppCompatActivity {
    EditText username;
    TextView next,goToLogin;
    String UserType = "", str_username;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        username = findViewById(R.id.username);
        next = findViewById(R.id.checkbtn);
        goToLogin = findViewById(R.id.idNotRegister);

//        UserType = "Receiver";

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });



    }

    public void Next(View view) {

        str_username = username.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(str_username)) {
            username.setError("Enter username");
            username.requestFocus();
        }else {

            reference.child(str_username)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {


                                String fullName = snapshot.child("fullName").getValue(String.class);
                                String username = snapshot.child("username").getValue(String.class);
                                String nicNo = snapshot.child("nicNo").getValue(String.class);
                                String email = snapshot.child("email").getValue(String.class);
                                String phoneNo = snapshot.child("phoneNo").getValue(String.class);
                                String password = snapshot.child("password").getValue(String.class);
                                String userType = snapshot.child("userType").getValue(String.class);
                                String userCategory = snapshot.child("userCategory").getValue(String.class);
                                String userCompleteAddress = snapshot.child("userCompleteAddress").getValue(String.class);
                                 latitude = snapshot.child("latitude").getValue(Double.class);
                                 longitude = (double) snapshot.child("longitude").getValue(Double.class);
                                Boolean isOrganization = snapshot.child("isOrganization").getValue(Boolean.class);


                                Intent intent = new Intent(getApplicationContext(), ForgetActivity02.class);
                                intent.putExtra("fullName", fullName);
                                intent.putExtra("username", username);
                                intent.putExtra("nicNo", nicNo);
                                intent.putExtra("email", email);
                                intent.putExtra("phoneNo", phoneNo);
                                intent.putExtra("password", password);
                                intent.putExtra("userType", userType);
                                intent.putExtra("userCategory", userCategory);
                                intent.putExtra("userCompleteAddress", userCompleteAddress);
                                intent.putExtra("latitude", latitude);
                                intent.putExtra("longitude", longitude);
                                intent.putExtra("isOrganization", isOrganization);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "Record Not Find", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }
    }
}