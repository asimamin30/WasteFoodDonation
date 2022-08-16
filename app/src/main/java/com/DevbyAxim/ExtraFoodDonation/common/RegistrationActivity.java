package com.DevbyAxim.ExtraFoodDonation.common;

import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.EMAIL_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.GMAIL_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.MATERIAL_CATEGORY;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.MATERIAL_CAT_TIL;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.MATERIAL_TIL;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.MATERIAL_TYPE;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.NAME_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.NEXT_BTN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.NIC_NO_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.NIC_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PASSWORD_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PHONE_NO_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PH_NO_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.PWD_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.USERNAME_ET;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.USERNAME_PATTERN;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.email;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.fullName;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.isValidPattern;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.nicNo;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.phNo;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.pwd;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.userCategory;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.userType;
import static com.DevbyAxim.ExtraFoodDonation.helper.Constants.username;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.DevbyAxim.ExtraFoodDonation.R;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout materialTIL, materialCatTIL;
    private AutoCompleteTextView materialType, materialCategory;
    private Button nextBtn;
    private int GPS_REQUEST_CODE = 9001;

    private EditText nameEt, usernameEt, nicNoEt, phNoEt, emailEt, pwdEt;
    private String nameStr, usernameStr, nicNoStr, phNoStr, emailStr, pwdStr;
    private String selectedMaterialCategory = "", selectedMaterialType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Hook views
        materialCategory = findViewById(MATERIAL_CATEGORY);
        materialType = findViewById(MATERIAL_TYPE);
        materialTIL = findViewById(MATERIAL_TIL);
        materialCatTIL = findViewById(MATERIAL_CAT_TIL);
        nextBtn = findViewById(NEXT_BTN);

        nameEt = findViewById(NAME_ET);
        usernameEt = findViewById(USERNAME_ET);
        nicNoEt = findViewById(NIC_NO_ET);
        phNoEt = findViewById(PH_NO_ET);
        emailEt = findViewById(EMAIL_ET);
        pwdEt = findViewById(PWD_ET);


        materialType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMaterialType = (String) adapterView.getItemAtPosition(i);
            }
        });

        materialCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMaterialCategory = (String) adapterView.getItemAtPosition(i);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameStr = String.valueOf(nameEt.getText());
                usernameStr = String.valueOf(usernameEt.getText());
                nicNoStr = String.valueOf(nicNoEt.getText());
                phNoStr = String.valueOf(phNoEt.getText());
                emailStr = String.valueOf(emailEt.getText());
                pwdStr = String.valueOf(pwdEt.getText());
                if (isValidate()) {
                    if (selectedMaterialType.isEmpty()) {
                        Toast.makeText(RegistrationActivity.this, "Please Select User Type before Proceed!", Toast.LENGTH_SHORT).show();
                    } else if (selectedMaterialCategory.isEmpty()) {
                        Toast.makeText(RegistrationActivity.this, "Please Select User Category before Proceed!", Toast.LENGTH_SHORT).show();
                    } else {
                        fullName = nameStr;
                        username = usernameStr;
                        nicNo = nicNoStr;
                        phNo = phNoStr;
                        email = emailStr;
                        pwd = pwdStr;
                        userType = selectedMaterialType;
                        userCategory = selectedMaterialCategory;
                        checkPermission();
                    }
                }
            }
        });
    }

    private boolean isValidate() {
        return (validateName() && validateUsername() && validateNicNo() && validatePhNo() && validateEmail() && validatePwd());
    }

    private boolean validatePwd() {
        if (pwdStr.isEmpty()) {
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

    private boolean validateEmail() {
        if (emailStr.isEmpty()) {
            emailEt.setError("Required Field!");
            emailEt.requestFocus();
            return false;
        } else if (!isValidPattern(emailStr, GMAIL_PATTERN)) {
            emailEt.setError("Gmail is not valid");
            emailEt.requestFocus();
            return false;
        } else if (isCheckEmail()) {
            emailEt.setError("Already available");
            emailEt.requestFocus();
            return false;
        } else {
            emailEt.setError(null);
        }
        return true;
    }

    private boolean isCheckEmail() {

        return false;
    }

    private boolean validatePhNo() {
        if (phNoStr.isEmpty()) {
            phNoEt.setError("Required Field!");
            phNoEt.requestFocus();
            return false;
        } else if (!isValidPattern(phNoStr, PHONE_NO_PATTERN)) {
            phNoEt.setError("Contact No. is not valid");
            phNoEt.requestFocus();
            return false;
        } else {
            phNoEt.setError(null);
        }
        return true;
    }

    private boolean validateNicNo() {
        if (nicNoStr.isEmpty()) {
            nicNoEt.setError("Required Field!");
            nicNoEt.requestFocus();
            return false;
        } else if (!isValidPattern(nicNoStr, NIC_PATTERN)) {
            nicNoEt.setError("NIC is not valid");
            nicNoEt.requestFocus();
            return false;
        } else {
            nicNoEt.setError(null);
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

    private boolean validateName() {
        if (nameStr.isEmpty()) {
            nameEt.setError("Required Field!");
            nameEt.requestFocus();
            return false;
        } else if (String.valueOf(nameEt.getText()).length() < 4) {
            nameEt.setError("Please Enter Full Name");
            nameEt.requestFocus();
            return false;
        } else {
            nameEt.setError(null);
        }
        return true;
    }

    private void checkPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (isGPSEnable()) {
                            Intent intent = new Intent(RegistrationActivity.this, RegistrationDoneActivity.class);
/*                            intent.putExtra(nameStr, "fullName");
                            intent.putExtra(usernameStr, "username");
                            intent.putExtra(nicNoStr, "nicNo");
                            intent.putExtra(phNoStr, "phNo");
                            intent.putExtra(emailStr, "email");
                            intent.putExtra(pwdStr, "pwd");
                            intent.putExtra(selectedMaterialType, "userType");
                            intent.putExtra(selectedMaterialCategory, "userCategory");*/
                            overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom);
                            startActivity(intent);
                            finish();

                           /* Toast.makeText(getApplicationContext(),
                                    "name: " + fullName + "\nusername: " + username + "\nNIC: " + nicNo + "\nPhone No. " + phNo + "\nEmail: " + email + "\nPwd: " + pwd + "\nUT: " + userType + "\nUC: " + userCategory
                                    , Toast.LENGTH_SHORT).show();*/
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), "");
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private boolean isGPSEnable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            return true;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app to work. Please enable GPS")
                    .setPositiveButton("Yes", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] postTypeStr = getResources().getStringArray(R.array.user_type);
        String[] postCategoryStr = getResources().getStringArray(R.array.user_category);
        ArrayAdapter<String> postTypeAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, postTypeStr);
        ArrayAdapter<String> postCategoryAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, postCategoryStr);
        materialType.setAdapter(postTypeAdapter);
        materialCategory.setAdapter(postCategoryAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnable) {
                // Toast.makeText(this, "GPS is Enable", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS is not Enable ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}