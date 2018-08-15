package psb.com.kidpaint.webApi.offerPackage.Get;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetOfferPackage {

    void doGetOfferPackage(String phoneNumber);

    interface iResult {
        void onSuccessGetOfferPackage(ResponseGetOfferPackage responseGetOfferPackage);

        void onFailedGetOfferPackage(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @GET("OfferPackage/Get")
        Call<ResponseGetOfferPackage> getOfferPackage(@Query("phoneNumber") String phoneNumber);
    }
}
