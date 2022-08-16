package com.DevbyAxim.ExtraFoodDonation.common;

import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.FIND_LOCATION_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.LOCATION_DATA_EXTRA;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PROGRESS_BAR;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.RECEIVER;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.RESULT_DATA_KEY;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.SEARCH_LOCATION_IMG;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.SIGN_UP_BTN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.SUCCESS_RESULT;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.USER_COMPLETE_ADDRESS_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.USER_ORGANIZATION_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.email;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.fullName;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.nicNo;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.phNo;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.pwd;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.userCategory;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.userType;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.username;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.DevbyAxim.ExtraFoodDonation.helper.FetchAddressIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RegistrationDoneActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mGoogleMap;
    private FloatingActionButton fab;
    private FusedLocationProviderClient mLocationClient;
    private EditText locSearch, userOrganizationEt;
    private ImageView searchIcon;
    private ResultReceiver resultReceiver;
    private Button singUpBtn;
    private String completeAddressStr, userOrganizationStr;
    private boolean isCheckOrganization = false;
    private ProgressBar progressBar;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_done);

        resultReceiver = new AddressResultReceiver(new Handler());

        Toast.makeText(getApplicationContext(),
                "name: " + fullName + "\nusername: " + username + "\nNIC: " + nicNo + "\nPhone No. " + phNo + "\nEmail: " + email + "\nPwd: " + pwd + "\nUT: " + userType + "\nUC: " + userCategory
                , Toast.LENGTH_SHORT).show();

        fab = findViewById(FIND_LOCATION_ET);
        locSearch = findViewById(USER_COMPLETE_ADDRESS_ET);
        searchIcon = findViewById(SEARCH_LOCATION_IMG);
        userOrganizationEt = findViewById(USER_ORGANIZATION_ET);
        singUpBtn = findViewById(SIGN_UP_BTN);
        progressBar = findViewById(PROGRESS_BAR);

        CheckUserCategory();

        iniMap();

        mLocationClient = new FusedLocationProviderClient(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "In Fab Button", Toast.LENGTH_SHORT).show();
                getCurLoc();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocation();
            }
        });

        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeAddressStr = String.valueOf(locSearch.getText());
                userOrganizationStr = String.valueOf(userOrganizationEt.getText());

                if (isCheckOrganization) {
                    if (userOrganizationStr.isEmpty()) {
                        userOrganizationEt.setError("Please Enter Your Organization NAme");
                        userOrganizationEt.requestFocus();
                        return;
                    } else {
                        userOrganizationEt.setError(null);
                    }
                }
                if (completeAddressStr.isEmpty()) {
                    locSearch.setError("Please Enter Your Organization NAme");
                    locSearch.requestFocus();
                    return;
                } else {
                    locSearch.setError(null);
                }
                OpenDatabaseToSaveData();
            }
        });

    }

    private void CheckUserCategory() {
        if (userCategory.equals("Organization")) {
            userOrganizationEt.setVisibility(View.VISIBLE);
            isCheckOrganization = true;
        } else {
            userOrganizationEt.setVisibility(View.GONE);
            isCheckOrganization = false;
        }
    }

    private void OpenDatabaseToSaveData() {

        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("fullName", fullName);
        data.put("username", username);
        data.put("nicNo", nicNo);
        data.put("phoneNo", phNo);
        data.put("email", email);
        data.put("password", pwd);
        data.put("userType", userType);
        data.put("userCategory", userCategory);
        data.put("userCompleteAddress", completeAddressStr);
        data.put("latitude", lat);
        data.put("longitude", lng);
        data.put("userCompleteAddress", completeAddressStr);
        data.put("isOrganization", isCheckOrganization);
        if (isCheckOrganization) {
            data.put("userOrganization", userOrganizationStr);
        }

        reference.child(username).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // open user profile after successful registration
//                            Intent intent = new Intent(RegistrationDoneActivity.this, LoginActivity.class);
                            // To prevent user from returning back to Register Activity on pressing back button after registration
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
//                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "User Registered successfully.", Toast.LENGTH_LONG).show();
                            finish(); // to close Register Activity
                        } else {
                            Toast.makeText(getApplicationContext(), "User Registered failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        // Hide ProgressBar whether user creation is successful or failed
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void geoLocation() {
        String locationName = locSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                gotoLocation(address.getLatitude(), address.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurLoc() {
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "To Get Current Location", Toast.LENGTH_SHORT).show();
                Location location = task.getResult();
                gotoLocation(location.getLatitude(), location.getLongitude());
                geoLocation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchAddressFromLatLong(location);
                    }
                }, 1500);
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        lat = latitude;
        lng = longitude;
    }

    private void iniMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        supportMapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        getCurLoc();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(RECEIVER, resultReceiver);
        intent.putExtra(LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == SUCCESS_RESULT) {
                locSearch.setText(resultData.getString(RESULT_DATA_KEY));
            } else {
                Toast.makeText(RegistrationDoneActivity.this, resultData.getString(RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
