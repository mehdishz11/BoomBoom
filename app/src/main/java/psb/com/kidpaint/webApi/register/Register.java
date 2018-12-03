package psb.com.kidpaint.webApi.register;


import psb.com.kidpaint.webApi.register.VerifyCode.VerifyCode;
import psb.com.kidpaint.webApi.register.VerifyCode.iVerifyCode;
import psb.com.kidpaint.webApi.register.fcmToken.FcmToken;
import psb.com.kidpaint.webApi.register.fcmToken.iFcmToken;
import psb.com.kidpaint.webApi.register.logout.Logout;
import psb.com.kidpaint.webApi.register.logout.iLogout;
import psb.com.kidpaint.webApi.register.registerUserInfo.Profile;
import psb.com.kidpaint.webApi.register.registerUserInfo.iProfile;
import psb.com.kidpaint.webApi.register.vasVerify.VasVerifyCode;
import psb.com.kidpaint.webApi.register.vasVerify.iVasVerifyCode;

public class Register implements iRegister {
    @Override
    public VerifyCode verifyCode() {
        return new VerifyCode();
    }

    @Override
    public VerifyCode verifyCode(iVerifyCode.iResult iResult) {
        return new VerifyCode(iResult);
    }

    @Override
    public VasVerifyCode vasVerifyCode() {
        return new VasVerifyCode();
    }

    @Override
    public VasVerifyCode vasVerifyCode(iVasVerifyCode.iResult iResult) {
        return new VasVerifyCode(iResult);
    }

    @Override
    public Profile profile() {
        return new Profile();
    }

    @Override
    public Profile profile(iProfile.iResult iResult) {
        return new Profile(iResult);
    }

    @Override
    public Logout logout() {
        return new Logout();
    }

    @Override
    public Logout logout(iLogout.iResult iResult) {
        return new Logout(iResult);
    }

    @Override
    public FcmToken fcmToken() {
        return new FcmToken();
    }

    @Override
    public FcmToken fcmToken(iFcmToken.iResult iResult) {
        return new FcmToken(iResult);
    }
}
