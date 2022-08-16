package com.DevbyAxim.ExtraFoodDonation.common;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.DevbyAxim.ExtraFoodDonation.DonorFragmets.DonorHomeFragments;
import com.DevbyAxim.ExtraFoodDonation.DonorFragmets.DonorLogoutFragments;
import com.DevbyAxim.ExtraFoodDonation.DonorFragmets.DonorProfileFragments;
import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DonorDashBoard extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_donor_dash_board);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();



        bottomNavigationView =  findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(DonorDashBoard.this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    Fragment DonorProfileFrag = new DonorProfileFragments();
    Fragment DonorHomeFrag  = new DonorHomeFragments();
    Fragment DonorLogoutFrag  = new DonorLogoutFragments();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.ccontainer, DonorProfileFrag).commit();
                return true;

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.ccontainer, DonorHomeFrag).commit();
                return true;

            case R.id.Logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.ccontainer, DonorLogoutFrag).commit();
                return true;
        }
        return false;
    }
}