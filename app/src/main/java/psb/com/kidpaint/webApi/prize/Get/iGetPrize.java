package psb.com.kidpaint.webApi.prize.Get;

import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.iPrize;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetPrize {

    int TYPE_NORMAL=0;
    int TYPE_JUDGE=1;

    void doGetPrize(int type);

    interface iResult {
        void onSuccessGetPrize(ResponsePrize responsePrize);

        void onFailedGetPrize(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPrize.apiAddress+"Get")
        Call<ResponsePrize> getPrize(@Query("type") int type);
    }
}
