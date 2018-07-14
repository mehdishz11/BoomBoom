package psb.com.kidpaint.user.register.sendPhoneNumber;

import android.content.Context;

/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iVSendPhoneNumber {
    Context getContext();

    void onStartSendPhoneNumber();
    void sendPhoneNumberSuccess();
    void sendPhoneNumberFailed(String msg);
}
