package psb.com.kidpaint.webApi.ScorePackage.buy;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class Buy implements iBuy {

    private iResult iResult;

    public Buy() {
    }

    public Buy(iBuy.iResult iResult) {
        this.iResult = iResult;
    }



    @Override
    public void doBuy(String mobile, long scorePackageId) {
        Call<ResponseBuyScorePackage> call = new WebService().getClient().create(apiRequest.class).buy(mobile, scorePackageId);
        call.enqueue(new Callback<ResponseBuyScorePackage>() {
            @Override
            public void onResponse(Call<ResponseBuyScorePackage> call, Response<ResponseBuyScorePackage> response) {
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
            public void onFailure(Call<ResponseBuyScorePackage> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedBuy(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
