package psb.com.kidpaint.user.register.verifyCode;

import android.content.Context;

/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public class PVerifyCode implements iPVerifyCode {
    private iVVerifyCode iVVerifyCode;
    private MVerifyCode mVerifyCode;

    public PVerifyCode(iVVerifyCode iVVerifyCode) {
        this.iVVerifyCode = iVVerifyCode;
        mVerifyCode=new MVerifyCode(this);
    }

    @Override
    public Context getContext() {
        return iVVerifyCode.getContext();
    }

    @Override
    public void VerifyCode(String phoneNumber, String verifyCode) {
        iVVerifyCode.onStartVerifyCode();
        mVerifyCode.VerifyCode(phoneNumber,verifyCode);
    }

    @Override
    public void VerifyCodeSuccess() {
        iVVerifyCode.VerifyCodeSuccess();
    }

    @Override
    public void VerifyCodeFailed(String msg) {
        iVVerifyCode.VerifyCodeFailed(msg);
    }

}
