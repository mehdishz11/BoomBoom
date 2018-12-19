package psb.com.kidpaint.webApi.prize.getDailyPrize;

import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;
import psb.com.kidpaint.webApi.prize.iPrize;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetDailyPrize {

    void doGetDailyPrize(String mobile);

    interface iResult {
        void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize);

        void onFailedGetDailyPrize(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPrize.apiAddress+"DailyPrize")
        Call<ResponseGetDailyPrize> getDailyPrize(@Query("mobile") String mobile);
    }
}
