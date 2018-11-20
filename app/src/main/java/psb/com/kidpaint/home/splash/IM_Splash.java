package psb.com.kidpaint.home.splash;

import android.content.Context;


public interface IM_Splash {
    Context getContext();

    void getStickers();

    void getRank();

    boolean userIsRegistered();
    void updateFcmToken();

    void getOfferPackage();
    void getDailyPrize();
    void getMessage();
    void getProfile();

    void getMyPaints();




}
