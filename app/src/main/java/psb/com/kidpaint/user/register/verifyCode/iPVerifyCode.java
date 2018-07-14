package psb.com.kidpaint.user.register.verifyCode;

import android.content.Context;

/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iPVerifyCode {
    Context getContext();

    void VerifyCode(String phoneNumber, String verifyCode);

    void VerifyCodeSuccess();
    void VerifyCodeFailed(String msg);
}
