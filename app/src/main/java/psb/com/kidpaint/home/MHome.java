package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.logout.iLogout;


public class MHome implements IM_Home {

    private Context context;
    private IP_Home ip_home;
    private UserProfile userProfile;

    public MHome(IP_Home ip_home) {
        this.ip_home = ip_home;
        this.context = ip_home.getContext();
        this.userProfile=new UserProfile(context);
    }

    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public void onStartLogout() {
        new Register().logout(new iLogout.iResult() {
            @Override
            public void onSuccessLogout() {
                userProfile.REMOVE_KEY_USER_INFO();
                ip_home.onLogoutSuccess();
            }

            @Override
            public void onFailedLogout(int errorId, String errorMessage) {
                ip_home.onLogoutFailed(errorMessage);

            }
        }).doLogout(userProfile.get_KEY_PHONE_NUMBER(""), Utils.getDeviceId(getContext()));
    }
}
