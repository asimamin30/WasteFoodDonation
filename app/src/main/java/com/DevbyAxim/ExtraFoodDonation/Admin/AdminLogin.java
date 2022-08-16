package com.DevbyAxim.ExtraFoodDonation.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.R;

public class AdminLogin extends AppCompatActivity {

    EditText UserName, Pass;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        UserName = findViewById(R.id.usernameEt);
        Pass = findViewById(R.id.pwdEt);



        
        Button signInBtn=findViewById(R.id.signInBtn);
        
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = UserName.getText().toString();
                password = Pass.getText().toString();

                if (username.isEmpty()) {
                    UserName.setError("Please Enter UserName");
                } else if (password.isEmpty()) {
                    Pass.setError("Please Enter Password");
                } else {
                    if (username.equals("admin") && password.equals("admin")) {
                        Intent intent = new Intent(getApplicationContext(), AdminDashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        TextView gotoback=findViewById(R.id.gotoback);
        gotoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button forget=findViewById(R.id.forgetPasswordBtn);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminLogin.this, "Contact with Developer Team", Toast.LENGTH_SHORT).show();
            }
        });



    }
}