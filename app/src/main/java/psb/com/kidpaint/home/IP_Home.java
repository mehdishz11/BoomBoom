package psb.com.kidpaint.home;

import android.content.Context;


public interface IP_Home {
    Context getContext();



    void onStartLogout();
    void onLogoutSuccess();
    void onLogoutFailed(String errorMessage);
}
