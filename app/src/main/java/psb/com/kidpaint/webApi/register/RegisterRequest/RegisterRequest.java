package psb.com.kidpaint.webApi.register.RegisterRequest;




import android.util.Log;

import com.google.gson.Gson;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.register.RegisterRequest.ModelRegisterRequest.ResponseRegisterRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mehdi on 1/23/2018 AD.
 */

public class RegisterRequest  implements iRegisterRequest {
    
    private iRegisterRequest.iResult iResult;
    public RegisterRequest() {

    }
    public RegisterRequest(iRegisterRequest.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void startSendPhoneNumber(String phoneNumber) {
        Log.d("TAG", "startSendPhoneNumber: "+phoneNumber);

        Call<ResponseRegisterRequest> call = new WebService().getClient().create(apiRequest.class).registerRequest(phoneNumber);

        call.enqueue(new Callback<ResponseRegisterRequest>() {
            @Override
            public void onResponse(Call<ResponseRegisterRequest> call, Response<ResponseRegisterRequest> response) {

                Log.d("TAG", "onResponse: "+new Gson().toJson(response.body()));
                if (response.code() == 200) {


                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessRegisterRequest();
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedRegisterRequest(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedRegisterRequest(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedRegisterRequest(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseRegisterRequest> call, Throwable t) {
                t.printStackTrace();
                if (iResult != null) {
                    iResult.onFailedRegisterRequest(0,  ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
