package psb.com.kidpaint.webApi.chat.Get;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.chat.Get.model.ResponseMyMessages;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetChat implements iGetChat {

    private iResult iResult;

    public GetChat() {
    }

    public GetChat(iGetChat.iResult iResult) {
        this.iResult = iResult;
    }



    @Override
    public void doGetChat(String deviceId,String mobileNumber, String time) {
        Call<ResponseMyMessages> call = new WebService().getClient().create(apiRequest.class).getChat(deviceId,mobileNumber, time);
        call.enqueue(new Callback<ResponseMyMessages>() {
            @Override
            public void onResponse(Call<ResponseMyMessages> call, Response<ResponseMyMessages> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetChat(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetChat(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetChat(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetChat(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseMyMessages> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetChat(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
