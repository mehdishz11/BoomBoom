package psb.com.kidpaint.webApi.ScorePackage.buy;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iBuy {

    void doBuy(String mobile, long scorePackageId);

    interface iResult {
        void onSuccessBuy(ResponseBuyScorePackage responseBuyScorePackage);

        void onFailedBuy(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @POST("ScorePackage/Buy")
        Call<ResponseBuyScorePackage> buy(@Query("mobile") String mobile, @Query("scorePackageId") long scorePackageId);
    }
}
