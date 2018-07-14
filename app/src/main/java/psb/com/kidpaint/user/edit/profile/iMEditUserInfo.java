package psb.com.kidpaint.user.edit.profile;

import android.content.Context;

import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;


/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iMEditUserInfo {
    Context getContext();

    void setUserInfo(ParamsRegister paramsRegister);

    void getUserInfo(String phoneNumber);

}
