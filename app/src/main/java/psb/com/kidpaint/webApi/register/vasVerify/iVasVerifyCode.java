package psb.com.kidpaint.webApi.register.vasVerify;


import psb.com.kidpaint.webApi.register.iRegister;
import psb.com.kidpaint.webApi.register.vasVerify.modelVasVerifyCode.ParamsVasVerifyCode;
import psb.com.kidpaint.webApi.register.vasVerify.modelVasVerifyCode.ResponseVasVerifyCode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mehdi on 1/29/2018 AD.
 */

public interface iVasVerifyCode {

    void startSendVerifyCode( String token, String phoneNumber,  String referenceCode, String irancellToken);

    interface iResult {
        void onSuccessSendVerifyCode(String jwt);

        void onFailedSendVerifyCode(int ErrorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iRegister.apiAddress + "VasVerify")
        Call<ResponseVasVerifyCode> verifyCode(@Body ParamsVasVerifyCode paramsVasVerifyCode);
    }

}
