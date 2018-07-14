package psb.com.kidpaint.webApi.register.RegisterRequest;



import psb.com.kidpaint.webApi.register.RegisterRequest.ModelRegisterRequest.ResponseRegisterRequest;
import psb.com.kidpaint.webApi.register.iRegister;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iRegisterRequest {

    void startSendPhoneNumber(String phoneNumber);

    interface iResult {
        void onSuccessRegisterRequest();

        void onFailedRegisterRequest(int ErrorId, String ErrorMessage);
    }

    interface apiRequest {
        @Headers("Content-Type: application/json")
        @POST(iRegister.apiAddress + "RegisterRequest")
        Call<ResponseRegisterRequest> registerRequest(@Query("mobileNumber") String phoneNumber);
    }
}
