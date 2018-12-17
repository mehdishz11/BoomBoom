package psb.com.kidpaint.webApi.offerPackage.buy;

import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iBuyOfferPackage {

    void doBuyOfferPackage(String phoneNumber,int offerPackageId);

    interface iResult {
        void onSuccessBuyOfferPackage(ResponseBuyOfferPackage responseBuyOfferPackage);

        void onFailedBuyOfferPackage(int errorCode, String ErrorMessage);
    }

    interface apiRequest {
        @POST("OfferPackage/Buy")
        Call<ResponseBuyOfferPackage> buyOfferPackage(@Query("mobile") String phoneNumber,@Query("offerPackageId") int offerPackageId);
    }
}
