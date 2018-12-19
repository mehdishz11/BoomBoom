package psb.com.kidpaint.webApi.register.fcmToken;

import android.os.Build;
import android.util.Log;

import psb.com.kidpaint.App;
import psb.com.kidpaint.BuildConfig;
import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.fcmToken.model.ParamsFcmToken;
import psb.com.kidpaint.webApi.register.fcmToken.model.ResponseFcmToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mehdi on 1/23/2018 AD.
 */

public class FcmToken implements iFcmToken {


    private iResult iResult;

    public FcmToken(){

    }
    public FcmToken(iFcmToken.iResult iResult){
        this.iResult=iResult;
    }




    @Override
    public void startSendFcmToken(String jwt,String token, String phoneNumber) {
        ParamsFcmToken paramsVerifyCode=new ParamsFcmToken();
        paramsVerifyCode.setPhoneNumber(phoneNumber);
        paramsVerifyCode.setAppVersion(""+ BuildConfig.VERSION_CODE);
        paramsVerifyCode.setDeviceId(Utils.getDeviceId(App.getContext()));
//        paramsVerifyCode.setDeviceModel(Value.getDeviceModel());
        paramsVerifyCode.setOsType(Long.valueOf(0));
        paramsVerifyCode.setOsVersion(""+ Build.VERSION.SDK_INT);
        paramsVerifyCode.setFcmToken(token);
        paramsVerifyCode.setJwtToken(jwt);

        Call<ResponseFcmToken> call = new WebService().getClient().create(apiRequest.class).fcmToken(paramsVerifyCode);


        call.enqueue(new Callback<ResponseFcmToken>() {
            @Override
            public void onResponse(Call<ResponseFcmToken> call, Response<ResponseFcmToken> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessSendFcmToken();
                        }
                    } else {
                        if (iResult != null) {
                            if(response.body().getMessages().size()>0) {
                                iResult.onFailedSendFcmToken(0, response.body().getMessages().get(0).getDescription());
                            }else{
                                iResult.onFailedSendFcmToken(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                            }
                        }
                    }
                }else {

                    if (iResult != null) {
                        iResult.onFailedSendFcmToken(0, ErrorMessage.getErrorByCode(response.code()));
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseFcmToken> call, Throwable t) {
                Log.d("TAG", " onFailure onResponseVerifyCode: "+(iResult == null));
                t.printStackTrace();
                if (iResult != null) {
                    iResult.onFailedSendFcmToken(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
