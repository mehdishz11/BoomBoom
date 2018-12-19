package psb.com.kidpaint.webApi.register.fcmToken;


import psb.com.kidpaint.webApi.register.fcmToken.model.ParamsFcmToken;
import psb.com.kidpaint.webApi.register.fcmToken.model.ResponseFcmToken;
import psb.com.kidpaint.webApi.register.iRegister;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mehdi on 1/29/2018 AD.
 */

public interface iFcmToken {

    void startSendFcmToken(String jwt,String token, String phoneNumber);

    interface iResult {
        void onSuccessSendFcmToken();

        void onFailedSendFcmToken(int ErrorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iRegister.apiAddress + "FcmToken")
        Call<ResponseFcmToken> fcmToken(@Body ParamsFcmToken paramsFcmToken);
    }

}
