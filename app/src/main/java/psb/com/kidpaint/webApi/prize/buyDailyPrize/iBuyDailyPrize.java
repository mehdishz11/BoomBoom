package psb.com.kidpaint.webApi.prize.buyDailyPrize;

import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;
import psb.com.kidpaint.webApi.prize.iPrize;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iBuyDailyPrize {

    void doBuyDailyPrize(String mobile,int id);

    interface iResult {
        void onSuccessBuyDailyPrize(ResponseBuyDailyPrize responseBuyDailyPrize);

        void onFailedBuyDailyPrize(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iPrize.apiAddress+"DailyPrize")
        Call<ResponseBuyDailyPrize> buyDailyPrize(@Query("mobile") String mobile, @Query("id") int id);
    }
}
