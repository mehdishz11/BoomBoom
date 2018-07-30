package psb.com.kidpaint.webApi.prize.myPrizeRequest;

import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.iPrize;
import psb.com.kidpaint.webApi.prize.myPrizeRequest.model.ResponseMyPrizeRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iMyPrizeRequest {

    void doGetMyPrizeRequest(String mobile);

    interface iResult {
        void onSuccessGetMyPrizeRequest(ResponseMyPrizeRequest responseMyPrizeRequest);

        void onFailedGetMyPrizeRequest(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPrize.apiAddress+"MyPrizeRequest")
        Call<ResponseMyPrizeRequest> getMyPrizeRequest(@Query("mobile") String mobile);
    }
}
