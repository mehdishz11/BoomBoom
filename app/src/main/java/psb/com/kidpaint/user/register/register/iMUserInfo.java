package psb.com.kidpaint.user.register.register;

import android.content.Context;

import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;


/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iMUserInfo {
    Context getContext();

    void setUserInfo(ParamsRegister paramsRegister);

    void getUserInfo(String phoneNumber);

}
