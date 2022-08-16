package com.DevbyAxim.ExtraFoodDonation.common;

import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.email;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.phNo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ForgetActivity02 extends AppCompatActivity {
    EditText etpassword;
    TextView changePassword,db_prePassword;
    String fullName,username,nicNo,phoneNo,password,userType,userCategory,userCompleteAddress,
            latitude,longitude,isOrganization,new_password;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget02);


        Intent intent = getIntent();
        fullName = intent.getStringExtra("fullName");
        username = intent.getStringExtra("username");
        nicNo = intent.getStringExtra("nicNo");
        phoneNo = intent.getStringExtra("phoneNo");
        password = intent.getStringExtra("password");
        userType = intent.getStringExtra("userType");
        userCategory = intent.getStringExtra("userCategory");
        userCompleteAddress = intent.getStringExtra("userCompleteAddress");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        isOrganization = intent.getStringExtra("isOrganization");

        Log.d("old",password);

        etpassword = findViewById(R.id.password);
        changePassword = findViewById(R.id.changePassword);
        db_prePassword = findViewById(R.id.db_pre_password);

        db_prePassword.setText(password);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password = String.valueOf(etpassword.getText());

                if(TextUtils.isEmpty(new_password))
                {
                    etpassword.setError("Enter Password For Update");
                    etpassword.requestFocus();
                }else{
                    updatePasswordInDatabase();
                }


            }
        });




    }


    private void updatePasswordInDatabase() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("fullName", fullName);
        data.put("username", username);
        data.put("nicNo", nicNo);
        data.put("phoneNo", phNo);
        data.put("email", email);
        data.put("password", new_password);
        data.put("userType", userType);
        data.put("userCategory", userCategory);
        data.put("userCompleteAddress", userCompleteAddress);
        data.put("latitude", latitude);
        data.put("longitude", longitude);
        data.put("isOrganization", isOrganization);

        reference.child(username).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // open user profile after successful registration
                            Intent intent = new Intent(ForgetActivity02.this, LoginActivity.class);
                            // To prevent user from returning back to Register Activity on pressing back button after registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Password Changed Successfully.", Toast.LENGTH_LONG).show();
                            finish(); // to close Register Activity
                        } else {
                            Toast.makeText(getApplicationContext(), "Password Changed  failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        // Hide ProgressBar whether user creation is successful or failed

                    }
                });


    }



}