package psb.com.kidpaint.webApi.paint.getMyPaints;

import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.iPaint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetMyPaints {

    void doGetMyPaints(String mobile,boolean isMatch);

    interface iResult {
        void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

        void onFailedGetMyPaints(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPaint.apiAddress+"GetMyPaints")
        Call<ResponseGetMyPaints> getMyPaints( @Query("mobile") String mobile ,@Query("isMatch") boolean isMatch);
    }
}
