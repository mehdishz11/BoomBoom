package psb.com.kidpaint.webApi.prize.buyDailyPrize;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class BuyDailyPrize implements iBuyDailyPrize {

    private iResult iResult;

    public BuyDailyPrize() {
    }

    public BuyDailyPrize(iBuyDailyPrize.iResult iResult) {
        this.iResult = iResult;
    }



    @Override
    public void doBuyDailyPrize(String mobile, int id) {
        Call<ResponseBuyDailyPrize> call = new WebService().getClient().create(apiRequest.class).buyDailyPrize(mobile,id);
        call.enqueue(new Callback<ResponseBuyDailyPrize>() {
            @Override
            public void onResponse(Call<ResponseBuyDailyPrize> call, Response<ResponseBuyDailyPrize> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessBuyDailyPrize(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedBuyDailyPrize(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedBuyDailyPrize(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedBuyDailyPrize(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBuyDailyPrize> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedBuyDailyPrize(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
