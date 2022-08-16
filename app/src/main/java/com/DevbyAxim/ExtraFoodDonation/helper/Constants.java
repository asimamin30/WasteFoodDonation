package com.DevbyAxim.ExtraFoodDonation.helper;

import com.DevbyAxim.ExtraFoodDonation.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {
    private static final String PACKAGE_NAME = "com.fyp.wastefooddonation";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static final int SUCCESS_RESULT = 1;
    public static final int FAILURE_RESULT = 0;
    // for store data
    public static String fullName, username, nicNo, phNo, email, pwd, userType, userCategory, userCompleteAddress,userOrganizationName;

    // Validation Pattern
    // digit + lowercase char + uppercase char + punctuation + symbol
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    public static final String PHONE_NO_PATTERN = "^((\\+92)?(0092)?(92)?(0)?)(3)([0-9]{9})$";
    public static final String GMAIL_PATTERN = "^[a-z0-9](\\.?[a-z0-9]){5,}@gmail\\.com$";
    public static final String NIC_PATTERN = "^[0-9]{5}[0-9]{7}[0-9]$";

    // Check Pattern
    public static boolean isValidPattern(final String input, final String CHECK_PATTERN) {
        Pattern pattern = Pattern.compile(CHECK_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    // Find View By IDz from R.layout.activity_registration
    public static final int MATERIAL_CATEGORY = R.id.materialCategory;
    public static final int MATERIAL_TYPE = R.id.materialType;
    public static final int MATERIAL_TIL = R.id.materialTIL;
    public static final int MATERIAL_CAT_TIL = R.id.materialCatTIL;
    public static final int NEXT_BTN = R.id.nextBtn;
    public static final int NAME_ET = R.id.nameEt;
    public static final int USERNAME_ET = R.id.usernameEt;
    public static final int NIC_NO_ET = R.id.nicNoEt;
    public static final int PH_NO_ET = R.id.phNoEt;
    public static final int EMAIL_ET = R.id.emailEt;
    public static final int PWD_ET = R.id.pwdEt;

    // Find View By IDz from R.layout.activity_registration_done
    public static final int USER_ORGANIZATION_ET = R.id.orgEt;
    public static final int USER_COMPLETE_ADDRESS_ET = R.id.locSearch;
    public static final int FIND_LOCATION_ET = R.id.fab;
    public static final int SEARCH_LOCATION_IMG = R.id.fab;
    public static final int SIGN_UP_BTN = R.id.signUpBtn;
    public static final int PROGRESS_BAR = R.id.progressBar;

    // Find View By IDz from R.layout.activity_login
    public static final int REMEMBER_ME = R.id.rememberMe;
    public static final int FORGET_PASSWORD_BTN = R.id.forgetPasswordBtn;
    public static final int SIGN_IN_BTN = R.id.signInBtn;
    public static final int GO_TO_SING_UP = R.id.goToSignUp;
}