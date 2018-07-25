package psb.com.kidpaint.user.edit;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import psb.com.kidpaint.R;
import psb.com.kidpaint.user.edit.profile.FragmentEditUserInfo;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;


public class ActivityEditProfile extends BaseActivity implements
        FragmentEditUserInfo.OnFragmentInteractionListener {
    private ProgressView progressView;
    private FragmentEditUserInfo fragmentEditUserInfo;
    UserProfile userProfile;

    private String KEY_FRGEditUserInfo = "KEY_FRGEditUserInfo";

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    private void setUpFrg() {
        fragmentEditUserInfo = new FragmentEditUserInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
      /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.md_blue_700));

        }*/
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ToolbarHandler.setToolbarColor(this,getWindow(),getWindow().getDecorView(), R.color.color_black_trans,false);
        ToolbarHandler.setNavigationColor(this,getWindow(),getWindow().getDecorView(), R.color.color_black_trans,false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = this.getWindow();
            clearLightStatusBar(window.getDecorView());
        }
        userProfile = new UserProfile(this);
        setUpFrg();
        progressView = findViewById(R.id.progressView);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment, fragmentEditUserInfo, KEY_FRGEditUserInfo).commit();

        createHelperWnd();
    }

    public static void clearLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // fragmentEditUserInfo
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
                fragmentEditUserInfo.retryGetProfile();
            }
        });
    }

    @Override
    public void onStartSetUserInfo() {
        progressView.setVisibility(View.VISIBLE);
        progressView.showProgress();
    }

    @Override
    public void setUserInfoSuccess() {
        progressView.setVisibility(View.GONE);
        progressView.showProgress();

        Intent intent = new Intent();
        intent.putExtra("Refresh",true);
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
                fragmentEditUserInfo.retrySetProfile();
            }
        });
    }
}
