package psb.com.kidpaint.webApi.register.registerUserInfo;



import psb.com.kidpaint.webApi.register.iRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ParamsRegister;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.ResponseUserInfo;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Mehdi on 1/29/2018 AD.
 */

public interface iProfile {

    void startGetUserInfo(String JWT, String phoneNumber);
    void startSetUserInfo(ParamsRegister paramsRegister);

    interface iResult {
        void onSuccessGetUserInfo(UserInfo userInfo);
        void onFailedGetUserInfo(int ErrorId, String ErrorMessage);


        void onSuccessSetUserInfo(UserInfo userInfo);
        void onFailedSetUserInfo(int ErrorId, String ErrorMessage);
    }


    interface apiGetUserInfo {
        @Headers("Content-Type: application/json")
        @GET(iRegister.apiAddress + "GetProfile")
        Call<ResponseUserInfo> getUserInfo(@Query("mobile") String mobile);
    }

    interface apiSetUserInfo {
        @Headers("Content-Type: application/json")
        @POST(iRegister.apiAddress + "SetProfile")
        Call<ResponseUserInfo> setUserInfo(@Body ParamsRegister paramsRegister);
    }

}
