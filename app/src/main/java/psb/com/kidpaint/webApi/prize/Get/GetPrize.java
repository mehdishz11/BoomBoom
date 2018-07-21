package psb.com.kidpaint.webApi.prize.Get;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetPrize implements iGetPrize {

    private iResult iResult;

    public GetPrize() {
    }

    public GetPrize(iGetPrize.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doGetPrize() {
        Call<ResponsePrize> call = new WebService().getClient().create(apiRequest.class).getPrize();
        call.enqueue(new Callback<ResponsePrize>() {
            @Override
            public void onResponse(Call<ResponsePrize> call, Response<ResponsePrize> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetPrize(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetPrize(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetPrize(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetPrize(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponsePrize> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetPrize(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
