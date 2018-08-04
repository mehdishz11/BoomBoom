package psb.com.kidpaint.webApi.register;


import psb.com.kidpaint.webApi.register.VerifyCode.VerifyCode;
import psb.com.kidpaint.webApi.register.VerifyCode.iVerifyCode;
import psb.com.kidpaint.webApi.register.fcmToken.FcmToken;
import psb.com.kidpaint.webApi.register.fcmToken.iFcmToken;
import psb.com.kidpaint.webApi.register.logout.Logout;
import psb.com.kidpaint.webApi.register.logout.iLogout;
import psb.com.kidpaint.webApi.register.registerUserInfo.Profile;
import psb.com.kidpaint.webApi.register.registerUserInfo.iProfile;

public interface iRegister {
    String apiAddress = "register/";

    VerifyCode verifyCode();
    VerifyCode verifyCode(iVerifyCode.iResult iResult);

    Profile profile();
    Profile profile(iProfile.iResult iResult);

    Logout logout();
    Logout logout(iLogout.iResult iResult);

    FcmToken fcmToken();
    FcmToken fcmToken(iFcmToken.iResult iResult);
}
