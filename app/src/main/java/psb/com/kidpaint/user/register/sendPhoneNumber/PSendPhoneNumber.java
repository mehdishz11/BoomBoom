package psb.com.kidpaint.user.register.sendPhoneNumber;

import android.content.Context;

/**
 * Created by Mehdi on 6/29/2017 AD.
 */

public class PSendPhoneNumber implements iPSendPhoneNumber{

    private iVSendPhoneNumber iVSendPhoneNumber;
    private MSendPhoneNumber mSendPhoneNumber;

    public PSendPhoneNumber(iVSendPhoneNumber iVSendPhoneNumber) {
        this.iVSendPhoneNumber = iVSendPhoneNumber;
        mSendPhoneNumber=new MSendPhoneNumber(this);
    }

    @Override
    public Context getContext() {
        return iVSendPhoneNumber.getContext();
    }

    @Override
    public void sendPhoneNumber(String phoneNumber) {
        iVSendPhoneNumber.onStartSendPhoneNumber();
        mSendPhoneNumber.sendPhoneNumber(phoneNumber);
    }

    @Override
    public void sendPhoneNumberSuccess() {
        iVSendPhoneNumber.sendPhoneNumberSuccess();
    }

    @Override
    public void sendPhoneNumberFailed(String msg) {
        iVSendPhoneNumber.sendPhoneNumberFailed(msg);
    }

}
