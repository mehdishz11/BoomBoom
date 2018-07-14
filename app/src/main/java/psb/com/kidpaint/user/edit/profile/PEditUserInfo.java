package psb.com.kidpaint.user.edit.profile;

import android.content.Context;

import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;


public class PEditUserInfo implements iPUEditUserInfo {

    private iVEditUserInfo iVUserInfo;
    private MEditUserInfo mUserInfo;

    public PEditUserInfo(iVEditUserInfo iVUserInfo) {
        this.iVUserInfo = iVUserInfo;
        mUserInfo=new MEditUserInfo(this);
    }

    @Override
    public Context getContext() {
        return iVUserInfo.getContext();
    }

    @Override
    public void setUserInfo(ParamsRegister paramsRegister) {
        iVUserInfo.onStartSetUserInfo();
        mUserInfo.setUserInfo(paramsRegister);
    }

    @Override
    public void setUserInfoSuccess(UserInfo userInfo) {
        iVUserInfo.setUserInfoSuccess(userInfo);
    }

    @Override
    public void setUserInfoFailed(String msg) {
        iVUserInfo.setUserInfoFailed(msg);
    }

    @Override
    public void getUserInfo(String phoneNumber) {
        mUserInfo.getUserInfo(phoneNumber);
    }

    @Override
    public void getUserInfoSuccess(UserInfo userInfo) {
        iVUserInfo.getUserInfoSuccess(userInfo);
    }

    @Override
    public void getUserInfoFailed(String msg) {
        iVUserInfo.getUserInfoFailed(msg);
    }
}