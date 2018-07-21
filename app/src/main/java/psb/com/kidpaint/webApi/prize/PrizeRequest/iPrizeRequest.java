package psb.com.kidpaint.webApi.prize.PrizeRequest;

import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ResponsePrizeRequest;
import psb.com.kidpaint.webApi.prize.iPrize;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iPrizeRequest {

    void doPrizeRequest(ParamsPrizeRequest paramsPrizeRequest);

    interface iResult {
        void onSuccessPrizeRequest(ResponsePrizeRequest responsePrizeRequest);

        void onFailedPrizeRequest(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST(iPrize.apiAddress+"PrizeRequest")
        Call<ResponsePrizeRequest> requestPrize(@Body ParamsPrizeRequest paramsPrizeRequest);
    }
}
