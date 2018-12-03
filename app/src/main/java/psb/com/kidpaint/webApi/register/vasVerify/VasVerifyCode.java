package psb.com.kidpaint.webApi.register.vasVerify;

import android.os.Build;
import android.util.Log;

import psb.com.kidpaint.App;
import psb.com.kidpaint.BuildConfig;
import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.vasVerify.modelVasVerifyCode.ParamsVasVerifyCode;
import psb.com.kidpaint.webApi.register.vasVerify.modelVasVerifyCode.ResponseVasVerifyCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mehdi on 1/23/2018 AD.
 */

public class VasVerifyCode implements iVasVerifyCode {


    private iResult iResult;

    public VasVerifyCode(){

    }
    public VasVerifyCode(iResult iResult){
        this.iResult=iResult;
    }

    @Override
    public void startSendVerifyCode(final String token,final String phoneNumber, final String referenceCode,final String irancellToken) {

        ParamsVasVerifyCode paramsVasVerifyCode =new ParamsVasVerifyCode();
        paramsVasVerifyCode.setPhoneNumber(phoneNumber);
        paramsVasVerifyCode.setRefrenceCode(referenceCode);
        paramsVasVerifyCode.setIrancellToken(irancellToken);

        paramsVasVerifyCode.setAppVersion(""+ BuildConfig.VERSION_CODE);
        paramsVasVerifyCode.setDeviceId(Utils.getDeviceId(App.getContext()));
//        paramsVasVerifyCode.setDeviceModel(Value.getDeviceModel());
        paramsVasVerifyCode.setOsType("0");
        paramsVasVerifyCode.setOsVersion(""+ Build.VERSION.SDK_INT);
        paramsVasVerifyCode.setFcmToken(token);

        Call<ResponseVasVerifyCode> call = new WebService().getClient().create(apiRequest.class).verifyCode(paramsVasVerifyCode);


        call.enqueue(new Callback<ResponseVasVerifyCode>() {
            @Override
            public void onResponse(Call<ResponseVasVerifyCode> call, Response<ResponseVasVerifyCode> response) {
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
            public void onFailure(Call<ResponseVasVerifyCode> call, Throwable t) {
                Log.d("TAG", " onFailure onResponseVerifyCode: "+(iResult == null));
                t.printStackTrace();
                if (iResult != null) {
                    iResult.onFailedSendVerifyCode(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }





}
