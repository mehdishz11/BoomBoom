package psb.com.kidpaint.user.edit.profile;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.registerUserInfo.Profile;
import psb.com.kidpaint.webApi.register.registerUserInfo.iProfile;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;


public class MEditUserInfo implements iMEditUserInfo {

    private iPUEditUserInfo iPUserInfo;
    private Profile profile;
    private UserProfile userProfile;

    public MEditUserInfo(iPUEditUserInfo iPUserInfo) {
        this.iPUserInfo = iPUserInfo;
        userProfile = new UserProfile(getContext());
    }

    @Override
    public Context getContext() {
        return iPUserInfo.getContext();
    }

    @Override
    public void setUserInfo(ParamsRegister paramsRegister) {

            new Register().profile(new iProfile.iResult() {
                @Override
                public void onSuccessGetUserInfo(UserInfo userInfo) {
                    userProfile.set_KEY_USER_INFO(userInfo);
                    iPUserInfo.getUserInfoSuccess(userInfo);
                }

                @Override
                public void onFailedGetUserInfo(int ErrorId, String ErrorMessage) {
                    iPUserInfo.getUserInfoFailed(ErrorMessage);
                }

                @Override
                public void onSuccessSetUserInfo(UserInfo userInfo) {
                    userProfile.set_KEY_USER_INFO(userInfo);
                    iPUserInfo.setUserInfoSuccess(userInfo);

                }

                @Override
                public void onFailedSetUserInfo(int ErrorId, String ErrorMessage) {
                    iPUserInfo.setUserInfoFailed(ErrorMessage);

                }
            }).startSetUserInfo(paramsRegister);

    }


    @Override
    public void getUserInfo(String phoneNumber) {
        profile.startGetUserInfo(userProfile.get_KEY_JWT("-1"),phoneNumber);
    }
}
