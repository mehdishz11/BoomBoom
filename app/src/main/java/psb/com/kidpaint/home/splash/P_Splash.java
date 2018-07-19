package psb.com.kidpaint.home.splash;

import android.content.Context;

public class P_Splash implements IP_Splash {

    private Context context;
    private IV_Splash ivSplash;
    private M_Splash mSplash;

    public P_Splash(IV_Splash ivSplash){
        this.context = ivSplash.getContext();
        this.ivSplash = ivSplash;
        mSplash = new M_Splash(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        mSplash.getStickers();
    }

    @Override
    public void getStickersSuccess() {
        ivSplash.getStickersSuccess();
    }

    @Override
    public void getStickersFailed(String msg) {
        ivSplash.getStickersFailed(msg);
    }
}
