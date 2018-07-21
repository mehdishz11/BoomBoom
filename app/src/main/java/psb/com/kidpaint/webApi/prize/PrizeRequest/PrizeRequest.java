package psb.com.kidpaint.webApi.prize.PrizeRequest;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ResponsePrizeRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class PrizeRequest implements iPrizeRequest {

    private iResult iResult;

    public PrizeRequest() {
    }

    public PrizeRequest(iPrizeRequest.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doPrizeRequest(ParamsPrizeRequest paramsPrizeRequest) {
        Call<ResponsePrizeRequest> call = new WebService().getClient().create(apiRequest.class).requestPrize(paramsPrizeRequest);
        call.enqueue(new Callback<ResponsePrizeRequest>() {
            @Override
            public void onResponse(Call<ResponsePrizeRequest> call, Response<ResponsePrizeRequest> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessPrizeRequest(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedPrizeRequest(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedPrizeRequest(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedPrizeRequest(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponsePrizeRequest> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedPrizeRequest(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
