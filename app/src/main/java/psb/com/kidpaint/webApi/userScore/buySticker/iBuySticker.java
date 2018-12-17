package psb.com.kidpaint.webApi.userScore.buySticker;

import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iBuySticker {

    void doBuy(String mobile, long score);

    interface iResult {
        void onSuccessBuy(ResponseBuySticker responseBuySticker);

        void onFailedBuy(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @POST("UserScore/BuySticker")
        Call<ResponseBuySticker> buy(@Query("mobile") String mobile, @Query("score") long score);
    }
}
