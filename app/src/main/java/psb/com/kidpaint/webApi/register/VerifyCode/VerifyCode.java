package psb.com.kidpaint.webApi.register.VerifyCode;

import android.os.Build;
import android.util.Log;


import com.helper.PaymentHelper;

import psb.com.kidpaint.App;
import psb.com.kidpaint.BuildConfig;
import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.VerifyCode.ModelVerifyCode.ParamsVerifyCode;
import psb.com.kidpaint.webApi.register.VerifyCode.ModelVerifyCode.ResponseVerifyCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mehdi on 1/23/2018 AD.
 */

public class VerifyCode implements iVerifyCode {


    private iResult iResult;

    public VerifyCode(){

    }
    public VerifyCode(iResult iResult){
        this.iResult=iResult;
    }

    @Override
    public void startSendVerifyCode(final String token,final String phoneNumber, final String smsCode) {

        ParamsVerifyCode paramsVerifyCode=new ParamsVerifyCode();
        paramsVerifyCode.setPhoneNumber(phoneNumber);
        paramsVerifyCode.setCode(smsCode);
        paramsVerifyCode.setAppVersion(""+ BuildConfig.VERSION_CODE);
        paramsVerifyCode.setDeviceId(Utils.getDeviceId(App.getContext()));
        paramsVerifyCode.setOsType(Long.valueOf(0));
        paramsVerifyCode.setOsVersion(""+ Build.VERSION.SDK_INT);
        paramsVerifyCode.setFcmToken(token);

        paramsVerifyCode.setIsVas(PaymentHelper.isAgrigator());

        Call<ResponseVerifyCode> call = new WebService().getClient().create(apiRequest.class).verifyCode(paramsVerifyCode);


        call.enqueue(new Callback<ResponseVerifyCode>() {
            @Override
            public void onResponse(Call<ResponseVerifyCode> call, Response<ResponseVerifyCode> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        Log.d("ffff", "onResponse: " + response.body().getOk());
                        if (iResult != null) {
                            iResult.onSuccessSendVerifyCode(response.body().getExtra());
                        }
                    } else {
                        Log.d("TAG", "onResponseVerifyCode: "+(iResult == null));
                        if (iResult != null) {
                            if(response.body().getMessages().size()>0) {
                                iResult.onFailedSendVerifyCode(0, response.body().getMessages().get(0).getDescription());
                            }else{
                                iResult.onFailedSendVerifyCode(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                            }
                        }
                    }
                }else {
                    Log.d("TAG", " onResponseVerifyCode !200: "+(iResult == null));

                    if (iResult != null) {
                        iResult.onFailedSendVerifyCode(0, ErrorMessage.getErrorByCode(response.code()));
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseVerifyCode> call, Throwable t) {
                Log.d("TAG", " onFailure onResponseVerifyCode: "+(iResult == null));
                t.printStackTrace();
                if (iResult != null) {
                    iResult.onFailedSendVerifyCode(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }





}
