package psb.com.kidpaint.myPrize;

import android.content.Context;

public interface IVMyPrize {
    Context getContext();


    void startGetMyPrize(int loadMode);
    void onSuccessGetMyPrize();
    void onFailedGetMyPrize(int errorCode, String errorMessage);





}
