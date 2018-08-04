package psb.com.kidpaint.user.register.verifyCode;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.VerifyCode.iVerifyCode;


public class  MVerifyCode implements iMVerifyCode {

    private iPVerifyCode iPVerifyCode;
    private UserProfile userProfile;

    public MVerifyCode(iPVerifyCode iPVerifyCode) {
        this.iPVerifyCode = iPVerifyCode;
        userProfile = new UserProfile(getContext());
    }

    @Override
    public Context getContext() {
        return iPVerifyCode.getContext();
    }

    @Override
    public void VerifyCode(String phoneNumber, String verifyCodeString) {
        new Register().verifyCode(new iVerifyCode.iResult() {
            @Override
            public void onSuccessSendVerifyCode(String jwt) {
                userProfile.set_KEY_JWT(jwt);
                iPVerifyCode.VerifyCodeSuccess();
            }

            @Override
            public void onFailedSendVerifyCode(int ErrorId, String ErrorMessage) {
                iPVerifyCode.VerifyCodeFailed(ErrorMessage);
            }
        }).startSendVerifyCode(userProfile.get_KEY_FCM(""),phoneNumber, verifyCodeString);
    }
}
