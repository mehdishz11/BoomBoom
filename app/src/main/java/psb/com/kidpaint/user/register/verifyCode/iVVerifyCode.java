package psb.com.kidpaint.user.register.verifyCode;

import android.content.Context;

/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iVVerifyCode {
    Context getContext();

    void onStartVerifyCode();
    void VerifyCodeSuccess();
    void VerifyCodeFailed(String msg);
}
