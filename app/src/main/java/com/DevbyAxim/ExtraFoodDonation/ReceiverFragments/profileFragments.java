package com.DevbyAxim.ExtraFoodDonation.ReceiverFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class profileFragments extends Fragment {

    private SharedPreferences loginPreferences;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
    EditText name, usernam, phoneno, Email, pass, address, nic;
    String fullName, username, nicNo, email, phoneNo, userType, userCategory, userCompleteAddress, password;
    Double latitude, longitude;
    Boolean isOrganization;

    String fullname, uname, nicno, ph, em, newpass, usertyp, usercate, useradd;
    Double lat, lon;
    Boolean orig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginPreferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        String prefUsername = loginPreferences.getString("un", "");


        View view = inflater.inflate(R.layout.fragment_profile_fragments, container, false);
        name = view.findViewById(R.id.name);
        usernam = view.findViewById(R.id.username);
        phoneno = view.findViewById(R.id.phoneNo);
        Email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        address = view.findViewById(R.id.Address);
        nic = view.findViewById(R.id.nicNo);

//        usernam.setText(prefUsername);

        OpneDatabase(prefUsername);
        TextView tv_Update=view.findViewById(R.id.tv_Update);
        tv_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfile();
            }
        });

        return view;
    }

    private void OpneDatabase(String prefUsername) {

        reference.child(prefUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {


                            fullName = snapshot.child("fullName").getValue(String.class);
                            username = snapshot.child("username").getValue(String.class);
                            nicNo = snapshot.child("nicNo").getValue(String.class);
                            email = snapshot.child("email").getValue(String.class);
                            phoneNo = snapshot.child("phoneNo").getValue(String.class);
                            password = snapshot.child("password").getValue(String.class);
                            userType = snapshot.child("userType").getValue(String.class);
                            userCategory = snapshot.child("userCategory").getValue(String.class);
                            userCompleteAddress = snapshot.child("userCompleteAddress").getValue(String.class);
                            latitude = snapshot.child("latitude").getValue(Double.class);
                            longitude = snapshot.child("longitude").getValue(Double.class);
                            isOrganization = snapshot.child("isOrganization").getValue(Boolean.class);

//                            EditText name, usernam, phoneNo, email, password, ConfirmPassword, nicNo;

                            name.setText(fullName);
                            usernam.setText(username);
                            phoneno.setText(phoneNo);
                            Email.setText(email);
                            pass.setText(password);
                            nic.setText(nicNo);
                            address.setText(userCompleteAddress);





                        } else {
                            Toast.makeText(getContext(), "Record Not Find", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void UpdateProfile() {




        fullname=name.getText().toString();
        uname=usernam.getText().toString();
        ph=phoneno.getText().toString();
        em=Email.getText().toString();
        newpass=pass.getText().toString();
        useradd=address.getText().toString();
        nicno=nic.getText().toString();
        usertyp=userType;
        usercate=userCategory;
        lat=latitude;
        lon=longitude;
        orig=isOrganization;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("fullName", fullname);
        data.put("username", uname);
        data.put("nicNo", nicno);
        data.put("phoneNo", ph);
        data.put("email", em);
        data.put("password", newpass);
        data.put("userType", usertyp);
        data.put("userCategory", usercate);
        data.put("userCompleteAddress", userCompleteAddress);
        data.put("latitude", lat);
        data.put("longitude", lon);
        data.put("isOrganization", orig);

        reference.child(username).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getContext(), "Profile Updated Successfully.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Profile Updated failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        // Hide ProgressBar whether user creation is successful or failed

                    }
                });


    }

}
