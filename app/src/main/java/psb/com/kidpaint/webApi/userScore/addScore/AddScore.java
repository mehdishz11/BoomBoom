package psb.com.kidpaint.webApi.userScore.addScore;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.iBuyDailyPrize;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;
import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class AddScore implements iAddScore {

    private iResult iResult;

    public AddScore() {
    }

    public AddScore(iAddScore.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doAddScore(String mobile, int code) {
        Call<ResponseAddScore> call = new WebService().getClient().create(apiRequest.class).addScore(mobile,code);
        call.enqueue(new Callback<ResponseAddScore>() {
            @Override
            public void onResponse(Call<ResponseAddScore> call, Response<ResponseAddScore> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessAddScore(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedAddScore(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedAddScore(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedAddScore(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseAddScore> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedAddScore(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
