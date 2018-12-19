package psb.com.kidpaint.webApi.prize.getDailyPrize;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetDailyPrize implements iGetDailyPrize {

    private iResult iResult;

    public GetDailyPrize() {
    }

    public GetDailyPrize(iGetDailyPrize.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doGetDailyPrize(String mobile) {
        Call<ResponseGetDailyPrize> call = new WebService().getClient().create(apiRequest.class).getDailyPrize(mobile);
        call.enqueue(new Callback<ResponseGetDailyPrize>() {
            @Override
            public void onResponse(Call<ResponseGetDailyPrize> call, Response<ResponseGetDailyPrize> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetDailyPrize(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetDailyPrize(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetDailyPrize(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetDailyPrize(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetDailyPrize> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetDailyPrize(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
