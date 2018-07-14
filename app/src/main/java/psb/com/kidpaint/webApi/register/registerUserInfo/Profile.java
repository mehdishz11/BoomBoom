package psb.com.kidpaint.webApi.register.registerUserInfo;

import android.util.Log;


import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ResponseUserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mehdi on 1/23/2018 AD.
 */

public class Profile implements iProfile {


    private iResult iResult;

    public Profile(){

    }
    public Profile(iResult iResult){
        this.iResult=iResult;
    }

    @Override
    public void startGetUserInfo(String JWT, String phoneNumber) {
        Log.d("ddd", "startGetUserInfo: "+ JWT);
        Log.d("ddd", "startGetUserInfo: "+ phoneNumber);
        Call<ResponseUserInfo> call = new WebService().getClient().create(apiGetUserInfo.class).getUserInfo(phoneNumber);
        call.enqueue(new Callback<ResponseUserInfo>() {
            @Override
            public void onResponse(Call<ResponseUserInfo> call, Response<ResponseUserInfo> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetUserInfo(response.body().getExtra());
                        }
                    } else {
                        if (iResult != null) {
                            if(response.body().getMessages().size()>0) {
                                iResult.onFailedGetUserInfo(0, response.body().getMessages().get(0).getDescription());
                            }else{
                                iResult.onFailedGetUserInfo(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                            }
                        }
                    }
                }else {
                    if (iResult != null) {
                        iResult.onFailedGetUserInfo(0, ErrorMessage.getErrorByCode(response.code()));
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseUserInfo> call, Throwable t) {
                if (iResult != null) {
                    iResult.onFailedGetUserInfo(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }

    @Override
    public void startSetUserInfo(ParamsRegister paramsRegister) {
        Call<ResponseUserInfo> call = new WebService().getClient().create(apiSetUserInfo.class).setUserInfo(paramsRegister);
        call.enqueue(new Callback<ResponseUserInfo>() {
            @Override
            public void onResponse(Call<ResponseUserInfo> call, Response<ResponseUserInfo> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessSetUserInfo(response.body().getExtra());
                        }
                    } else {
                        if (iResult != null) {
                            if(response.body().getMessages().size()>0) {
                                iResult.onFailedSetUserInfo(0, response.body().getMessages().get(0).getDescription());
                            }else{
                                iResult.onFailedSetUserInfo(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                            }
                        }
                    }
                }else {
                    if (iResult != null) {
                        iResult.onFailedSetUserInfo(0, ErrorMessage.getErrorByCode(response.code()));
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseUserInfo> call, Throwable t) {
                if (iResult != null) {
                    iResult.onFailedSetUserInfo(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
