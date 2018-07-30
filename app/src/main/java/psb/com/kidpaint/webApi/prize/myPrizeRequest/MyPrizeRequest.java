package psb.com.kidpaint.webApi.prize.myPrizeRequest;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.model.ResponseMyPrizeRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class MyPrizeRequest implements iMyPrizeRequest {

    private iResult iResult;

    public MyPrizeRequest() {
    }

    public MyPrizeRequest(iMyPrizeRequest.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doGetMyPrizeRequest(String mobile) {
        Call<ResponseMyPrizeRequest> call = new WebService().getClient().create(apiRequest.class).getMyPrizeRequest(mobile);
        call.enqueue(new Callback<ResponseMyPrizeRequest>() {
            @Override
            public void onResponse(Call<ResponseMyPrizeRequest> call, Response<ResponseMyPrizeRequest> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetMyPrizeRequest(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetMyPrizeRequest(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetMyPrizeRequest(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetMyPrizeRequest(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseMyPrizeRequest> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetMyPrizeRequest(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
