package com.DevbyAxim.ExtraFoodDonation.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.ReceiverFragments.HomeFragments;
import com.DevbyAxim.ExtraFoodDonation.ReceiverFragments.LogoutFragments;
import com.DevbyAxim.ExtraFoodDonation.ReceiverFragments.profileFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button logOutBtn;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    // Progress bar
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    CountDownTimer countDownTimer;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();



         bottomNavigationView =  findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void GoBackToLogin() {
        loginPrefsEditor.clear();
        loginPrefsEditor.apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
        finish();
    }
    Fragment ProfileFrag = new profileFragments();
    Fragment HomeFrag  = new HomeFragments();
    Fragment LogoutFrag  = new LogoutFragments();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.rcontainer, ProfileFrag).commit();
                return true;

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.rcontainer, HomeFrag).commit();
                return true;

            case R.id.Logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.rcontainer, LogoutFrag).commit();
                return true;
        }
        return false;

    }
}