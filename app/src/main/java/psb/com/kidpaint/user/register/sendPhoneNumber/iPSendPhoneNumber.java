package psb.com.kidpaint.user.register.sendPhoneNumber;

import android.content.Context;

/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iPSendPhoneNumber {
    Context getContext();

    void sendPhoneNumber(String phoneNumber);

    void sendPhoneNumberSuccess();
    void sendPhoneNumberFailed(String msg);
}
