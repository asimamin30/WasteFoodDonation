package com.DevbyAxim.ExtraFoodDonation.common;

import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.FORGET_PASSWORD_BTN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.GO_TO_SING_UP;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PASSWORD_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PWD_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.REMEMBER_ME;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.SIGN_IN_BTN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.USERNAME_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.USERNAME_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.isValidPattern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.Admin.AdminLogin;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEt, pwdEt;
    private CheckBox rememberMeCheckBox;
    private Button forgetPwdBtn, signInBtn;
    private TextView gotoSignUpTv;

    private String usernameStr, pwdStr;
    private String typereceiver="Receiver";

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    ImageView lock;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");

    // Progress bar
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    CountDownTimer receiverCDT;
    CountDownTimer DonorCDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hook Views
        progressBar = findViewById(R.id.progressBar);
        usernameEt = findViewById(USERNAME_ET);
        pwdEt = findViewById(PWD_ET);
        rememberMeCheckBox = findViewById(REMEMBER_ME);
        forgetPwdBtn = findViewById(FORGET_PASSWORD_BTN);
        signInBtn = findViewById(SIGN_IN_BTN);
        gotoSignUpTv = findViewById(GO_TO_SING_UP);
        lock = findViewById(R.id.lock);

        lock.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent=new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(intent);
                return false;
            }
        });

        receiverCDT = new CountDownTimer(11*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                CurrentProgress = CurrentProgress + 10;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);

            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                GotoReceiverDashBoard(String.valueOf(usernameEt.getText()));
            }
        };
        DonorCDT = new CountDownTimer(11*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                CurrentProgress = CurrentProgress + 10;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);

            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                GotoDonorDashBoard(String.valueOf(usernameEt.getText()));
            }
        };

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            usernameEt.setText(loginPreferences.getString("username", ""));
            pwdEt.setText(loginPreferences.getString("password", ""));
            rememberMeCheckBox.setChecked(true);
          //  GotoReceiverDashBoard(String.valueOf(usernameEt.getText()));

        }

        // Listeners
        forgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
                finish();

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameStr = String.valueOf(usernameEt.getText());
                pwdStr = String.valueOf(pwdEt.getText());
                if (isValidate()) {
                    if (rememberMeCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", usernameStr);
                        loginPrefsEditor.putString("password", pwdStr);
                    } else {
                        loginPrefsEditor.clear();
                    }
                    loginPrefsEditor.apply();
                    OpenDBAndCheckCredentials();

                }
            }
        });

        gotoSignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
                startActivity(intent);
            }
        });


    }


    private void OpenDBAndCheckCredentials() {
        ref.child(usernameStr).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pwd = snapshot.child("password").getValue(String.class);
                    String userType=snapshot.child("userType").getValue(String.class);
                    if (pwd.equals(pwdStr)) {
                        if (userType.equals(typereceiver)){
                            usernameEt.setText(loginPreferences.getString("username", ""));
                            loginPrefsEditor.putString("un",usernameStr);
                            loginPrefsEditor.apply();
                            progressBar.setVisibility(View.VISIBLE);
                            receiverCDT.start();
                        }else{
                            progressBar.setVisibility(View.VISIBLE);
                            loginPrefsEditor.putString("un",usernameStr);
                            loginPrefsEditor.apply();
                            DonorCDT.start();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid credentials. Kindly, check and re-enter.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"User does not exists or is no longer valid. Please register again.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GotoDonorDashBoard(String valueOf) {
        startActivity(new Intent(LoginActivity.this, DonorDashBoard.class));
        overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
        finish();
    }
    private void GotoReceiverDashBoard(String s) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
        finish();
    }

    private boolean isValidate() {
        return (validateUsername() | validatePassword());
    }

    private boolean validatePassword() {
       if (usernameStr.isEmpty()){
           usernameEt.setError("Required Field");
           usernameEt.requestFocus();
           return false;
       }
        else if (pwdStr.isEmpty()) {
            pwdEt.setError("Required Field!");
            pwdEt.requestFocus();
            return false;
        } else if (pwdStr.length() < 8) {
            pwdEt.setError("Password must more then 8");
            pwdEt.requestFocus();
            return false;
        } else if (!isValidPattern(pwdStr, PASSWORD_PATTERN)) {
            pwdEt.setError("Please enter strong password");
            pwdEt.requestFocus();
            return false;
        } else {
            pwdEt.setError(null);
        }

        return true;
    }

    private boolean validateUsername() {
        if (usernameStr.isEmpty()) {
            usernameEt.setError("Required Field!");
            usernameEt.requestFocus();
            return false;
        } else if (usernameStr.length() < 5) {
            usernameEt.setError("username must more then 5");
            usernameEt.requestFocus();
            return false;
        } else if (!isValidPattern(usernameStr, USERNAME_PATTERN)) {
            usernameEt.setError("username is not valid");
            usernameEt.requestFocus();
            return false;
        } else {
            usernameEt.setError(null);
        }
        return true;
    }

    public void forgetpass(View view) {

    }
}