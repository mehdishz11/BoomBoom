package psb.com.kidpaint.myPrize;

import android.content.Context;

import psb.com.kidpaint.myPrize.adapter.ViewHolder_MyPrize;

public interface IPMyPrize {
    Context getContext();

    void getMyPrize(int loadMode);
    void onSuccessGetMyPrize();
    void onFailedGetMyPrize(int errorCode, String errorMessage);

    void onBindView_MyPrize(ViewHolder_MyPrize holder, int position);

   int getArrSize();



}
