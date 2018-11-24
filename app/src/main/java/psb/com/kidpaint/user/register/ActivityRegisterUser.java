package psb.com.kidpaint.user.register;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.user.register.register.FragmentUserInfo;
import psb.com.kidpaint.user.register.sendPhoneNumber.FragmentSendPhoneNumber;
import psb.com.kidpaint.user.register.verifyCode.FragmentVerifyCode;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.registerUserInfo.iProfile;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;


public class ActivityRegisterUser extends BaseActivity implements
        FragmentSendPhoneNumber.OnFragmentInteractionListener,
        FragmentVerifyCode.OnFragmentInteractionListener,
        FragmentUserInfo.OnFragmentInteractionListener {

    private int REQUEST_SMS_PERMMISION = 105;
    private ProgressView progressView;
    private String verifyCode;
    private String phoneNumber;
    private FragmentSendPhoneNumber fragmentSendPhoneNumber;
    private FragmentVerifyCode fragmentVerifyCode;
    private FragmentUserInfo fragmentUserInfo;
    private int fragmentState = 0;
    UserProfile userProfile;
    private final String BROADCAST_UPDATE = "BROADCAST_UPDATE";

    private String KEY_FRGSendPhoneNumber = "KEY_FRGSendPhoneNumber";
    private String KEY_FRGVerifyCode = "KEY_FRGVerifyCode";
    private String KEY_FRGUserInfo = "KEY_FRGUserInfo";

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if(getSupportFragmentManager().findFragmentByTag(KEY_FRGUserInfo)!=null){
                finish();
            }else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            finish();
        }
    }

    private void setUpFrg() {
        fragmentSendPhoneNumber = new FragmentSendPhoneNumber();
        fragmentVerifyCode = new FragmentVerifyCode();
        fragmentUserInfo = new FragmentUserInfo();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onGotKey);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onGotKey);
        super.onPause();
    }

    @Override
    protected void onResume() {
        IntentFilter iff = new IntentFilter(BROADCAST_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(onGotKey, iff);
        super.onResume();
    }

    private BroadcastReceiver onGotKey = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getSupportFragmentManager().findFragmentByTag(KEY_FRGVerifyCode) != null){
                fragmentVerifyCode.setCodeToEditTextCode(intent.getStringExtra("code"));
            }
        }
    };

    private static final int RECORD_REQUEST_CODE = 101;

    protected void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS}, RECORD_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Log.d("TAG", "onCreateRegister_user: "+(userProfile!=null));

        IntentFilter iff = new IntentFilter(BROADCAST_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(onGotKey, iff);
        userProfile = new UserProfile(this);
        setUpFrg();
        progressView = findViewById(R.id.progressView);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment, fragmentSendPhoneNumber, KEY_FRGSendPhoneNumber).commit();


        createHelperWnd();
    }

    public static void clearLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMMISION) {
            if (getSupportFragmentManager().findFragmentByTag(KEY_FRGSendPhoneNumber) != null){
                fragmentSendPhoneNumber.getSmsPermissionFromUser();
                IntentFilter iff = new IntentFilter(BROADCAST_UPDATE);
                LocalBroadcastManager.getInstance(this).registerReceiver(onGotKey, iff);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // fragment senPhoneNumber
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onStartSendPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        //progressView.setVisibility(View.VISIBLE);
        //progressView.showProgress();
    }

    @Override
    public void sendPhoneNumberSuccess() {
        fragmentState = 1;
        progressView.setVisibility(View.GONE);
        fragmentVerifyCode = new FragmentVerifyCode().newInstance(phoneNumber);
       // getSupportFragmentManager().beginTransaction().remove(fragmentSendPhoneNumber).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment, fragmentVerifyCode, KEY_FRGVerifyCode).addToBackStack(null).commit();
    }

    @Override
    public void sendPhoneNumberFailed(String msg) {
        progressView.setVisibility(View.VISIBLE);
        progressView.setOnRetryClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSendPhoneNumber.sendPhoneNumberAgain();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // fragmentVerifyCode
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onStartVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        //progressView.setVisibility(View.VISIBLE);
        //progressView.showProgress();
    }

    @Override
    public void VerifyCodeSuccess() {
        Utils.setStringPreference(this, Utils.KEY_REGISTER, Utils.KEY_PHONENUMBER, phoneNumber);
        this.verifyCode = verifyCode;
        progressView.setVisibility(View.VISIBLE);

        new Register().profile(new iProfile.iResult() {
            @Override
            public void onSuccessGetUserInfo(UserInfo userInfo) {
                progressView.setVisibility(View.GONE);
                if ((userInfo.getFirstName().isEmpty() && userInfo.getLastName().isEmpty()) ||
                        (userInfo.getFirstName() == null && userInfo.getLastName() == null)){
                   // getSupportFragmentManager().beginTransaction().remove(fragmentVerifyCode).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragmentUserInfo, KEY_FRGUserInfo).addToBackStack(null).commit();
                } else {

                    userProfile.set_KEY_USER_INFO(userInfo);
                    Intent intent = new Intent();
                    intent.putExtra("First",1);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailedGetUserInfo(int ErrorId, String ErrorMessage) {

            }

            @Override
            public void onSuccessSetUserInfo(UserInfo userInfo) {

            }

            @Override
            public void onFailedSetUserInfo(int ErrorId, String ErrorMessage) {

            }
        }).startGetUserInfo(userProfile.get_KEY_JWT("-1"),phoneNumber);
    }



    ///////////////////////////////////////////////////////////////////////////
    // fragmentUserInfo
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onStartGetUserInfo() {
        progressView.setVisibility(View.VISIBLE);
        progressView.showProgress();
    }

    @Override
    public void getUserInfoSuccess() {
        progressView.setVisibility(View.GONE);
        progressView.showProgress();
    }

    @Override
    public void getUserInfoFailed(String msg) {
        progressView.setVisibility(View.VISIBLE);
        progressView.showError(msg);
        progressView.setOnRetryClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentUserInfo.retryGetProfile();
            }
        });
    }

    @Override
    public void onStartSetUserInfo() {
       /* progressView.setVisibility(View.VISIBLE);
        progressView.showProgress();*/
    }

    @Override
    public void setUserInfoSuccess() {
        progressView.setVisibility(View.GONE);
        progressView.showProgress();

        Intent intent = new Intent();
        intent.putExtra("First",0);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void setUserInfoFailed(String msg) {
        progressView.setVisibility(View.VISIBLE);
        progressView.showError(msg);

        progressView.setOnRetryClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentUserInfo.retrySetProfile();
            }
        });
    }

    @Override
    public void cancelRegister() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
