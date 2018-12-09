package psb.com.kidpaint.user.register;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.helper.PaymentHelper;
import com.rasa.statistics.Statistics;

import ir.dorsa.totalpayment.payment.Payment;
import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.user.register.register.FragmentUserInfo;
import psb.com.kidpaint.user.register.sendPhoneNumber.FragmentSendPhoneNumber;
import psb.com.kidpaint.user.register.verifyCode.FragmentVerifyCode;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.registerUserInfo.iProfile;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;
import psb.com.kidpaint.webApi.register.vasVerify.iVasVerifyCode;

import static psb.com.kidpaint.App.getContext;


public class ActivityRegisterUser extends BaseActivity implements
        FragmentSendPhoneNumber.OnFragmentInteractionListener,
        FragmentVerifyCode.OnFragmentInteractionListener,
        FragmentUserInfo.OnFragmentInteractionListener {

    private static final int REQUEST_CODE_REGISTER = 20;
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

        IntentFilter iff = new IntentFilter(BROADCAST_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(onGotKey, iff);
        userProfile = new UserProfile(this);
        setUpFrg();
        progressView = findViewById(R.id.progressView);

        Log.d(App.TAG, "onCreate:Payment "+PaymentHelper.isAgrigator());

        if(PaymentHelper.isAgrigator()){
            Payment payment=new Payment(this);
            Intent intentDorsaPayment = payment.getPaymentIntent(
                    true,
                    "متن ارسال شماره موبایل",
                    App.appCode,
                    App.productCode,
                    App.irancellSku,
                    new int[]{}
            );
            startActivityForResult(intentDorsaPayment, REQUEST_CODE_REGISTER);
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment, fragmentSendPhoneNumber, KEY_FRGSendPhoneNumber).commit();
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_REGISTER){
            if(resultCode==Activity.RESULT_OK){
                final Payment payment=new Payment(this);

                new Statistics(this,App.MERKETER_ID).active(payment.getPhoneNumber(),payment.getReferenceCode(),payment.getIrancelToken());
                final String time=Utils.getCurrentTime();
               long dbId = new Database().tblMessage(getContext()).insertMyMessage(0,"لغو اشتراک متن", "admin", "success", time,"لغو اشتراک",false);


                final ProgressDialog pDialog=new ProgressDialog(this);
                pDialog.setMessage("درحال دریافت اطلاعات ...");
                pDialog.setCancelable(false);
                pDialog.show();

                new Register().vasVerifyCode(new iVasVerifyCode.iResult() {
                    @Override
                    public void onSuccessSendVerifyCode(String jwt) {
                        userProfile.set_KEY_JWT(jwt);
                        ActivityRegisterUser.this.phoneNumber=payment.getPhoneNumber();
                        Utils.setStringPreference(ActivityRegisterUser.this, Utils.KEY_REGISTER, Utils.KEY_PHONENUMBER, payment.getPhoneNumber());
                        getUserInfo();
                        pDialog.cancel();
                    }

                    @Override
                    public void onFailedSendVerifyCode(int ErrorId, String ErrorMessage) {
                        pDialog.cancel();
                        onBackPressed();
                    }
                }).startSendVerifyCode(
                        userProfile.get_KEY_FCM(""),
                        payment.getPhoneNumber(),
                        payment.getReferenceCode(),
                        payment.getIrancelToken()
                );

            }else{
                setResult(Activity.RESULT_CANCELED);
                finish();
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
        new Statistics(this,App.MERKETER_ID).active(phoneNumber);
        getUserInfo();
    }


    ///////////////////////////////////////////////////////////////////////////
    // fragmentUserInfo
    ///////////////////////////////////////////////////////////////////////////
    private void getUserInfo(){
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
