package psb.com.kidpaint.home;

import android.content.Context;



public class PHome implements IP_Home {
    private IV_Home iv_home;
    private MHome mHome;
    private Context context;

    public PHome(IV_Home iv_home) {
        this.iv_home = iv_home;
        this.context=iv_home.getContext();
        this.mHome=new MHome(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onStartLogout() {
        iv_home.onStartLogout();
        mHome.onStartLogout();
    }

    @Override
    public void onLogoutSuccess() {
        iv_home.onLogoutSuccess();
    }

    @Override
    public void onLogoutFailed(String errorMessage) {
        iv_home.onLogoutFailed(errorMessage);

    }
}
