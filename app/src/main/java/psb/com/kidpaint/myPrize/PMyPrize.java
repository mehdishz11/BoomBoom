package psb.com.kidpaint.myPrize;

import android.content.Context;

import psb.com.kidpaint.myPrize.adapter.ViewHolder_MyPrize;

public class PMyPrize implements IPMyPrize {
    private Context context;
    private IVMyPrize ivMyPrize;
    private MMyPrize mMyPrize;

    public PMyPrize(IVMyPrize ivMyPrize) {
        this.ivMyPrize = ivMyPrize;
        this.context=ivMyPrize.getContext();
        this.mMyPrize=new MMyPrize(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getMyPrize(int loadMode) {
      ivMyPrize.startGetMyPrize(loadMode);
      mMyPrize.getMyPrize();
    }

    @Override
    public void onSuccessGetMyPrize() {
           ivMyPrize.onSuccessGetMyPrize();
    }

    @Override
    public void onFailedGetMyPrize(int errorCode, String errorMessage) {
    ivMyPrize.onFailedGetMyPrize(errorCode, errorMessage);
    }

    @Override
    public void onBindView_MyPrize(ViewHolder_MyPrize holder, int position) {

    }

    @Override
    public int getArrSize() {
        return mMyPrize.getArrSize();
    }
}
