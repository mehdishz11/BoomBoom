package psb.com.kidpaint.user.register.sendPhoneNumber;

import android.content.Context;

import psb.com.kidpaint.webApi.register.RegisterRequest.RegisterRequest;
import psb.com.kidpaint.webApi.register.RegisterRequest.iRegisterRequest;


public class  MSendPhoneNumber implements iMSendPhoneNumber {

    private iPSendPhoneNumber iPSendPhoneNumber;

    public MSendPhoneNumber(iPSendPhoneNumber iPSendPhoneNumber) {
        this.iPSendPhoneNumber = iPSendPhoneNumber;
    }

    @Override
    public Context getContext() {
        return iPSendPhoneNumber.getContext();
    }

    @Override
    public void sendPhoneNumber(String phoneNumber) {
        RegisterRequest registerRequest = new RegisterRequest(new iRegisterRequest.iResult() {
            @Override
            public void onSuccessRegisterRequest() {
                iPSendPhoneNumber.sendPhoneNumberSuccess();
            }

            @Override
            public void onFailedRegisterRequest(int ErrorId, String ErrorMessage) {

                iPSendPhoneNumber.sendPhoneNumberFailed(ErrorMessage);
            }
        });
        registerRequest.startSendPhoneNumber(phoneNumber);
    }
}
