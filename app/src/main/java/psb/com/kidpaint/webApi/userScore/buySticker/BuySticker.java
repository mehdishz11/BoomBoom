package psb.com.kidpaint.webApi.userScore.buySticker;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class BuySticker implements iBuySticker {

    private iResult iResult;

    public BuySticker() {
    }

    public BuySticker(iBuySticker.iResult iResult) {
        this.iResult = iResult;
    }



    @Override
    public void doBuy(String mobile, long score) {
        Call<ResponseBuySticker> call = new WebService().getClient().create(apiRequest.class).buy(mobile, score);
        call.enqueue(new Callback<ResponseBuySticker>() {
            @Override
            public void onResponse(Call<ResponseBuySticker> call, Response<ResponseBuySticker> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessBuy(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedBuy(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedBuy(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedBuy(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBuySticker> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedBuy(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
