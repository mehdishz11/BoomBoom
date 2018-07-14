package psb.com.kidpaint.user.edit.profile;

import android.content.Context;

import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;


/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iVEditUserInfo {
    Context getContext();

    void onStartSetUserInfo();
    void setUserInfoSuccess(UserInfo userInfo);
    void setUserInfoFailed(String msg);

    void onStartGetUserInfo();
    void getUserInfoSuccess(UserInfo userInfo);
    void getUserInfoFailed(String msg);
}
