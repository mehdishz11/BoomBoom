package psb.com.kidpaint.user.register.sendPhoneNumber;

import android.content.Context;


/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public interface iMSendPhoneNumber {
    Context getContext();

    void sendPhoneNumber(String phoneNumber);

}
