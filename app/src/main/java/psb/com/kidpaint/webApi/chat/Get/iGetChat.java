package psb.com.kidpaint.webApi.chat.Get;

import psb.com.kidpaint.webApi.chat.Get.model.ResponseMyMessages;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.iPrize;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetChat {

    void doGetChat(String deviceId,String mobileNumber,String time);

    interface iResult {
        void onSuccessGetChat(ResponseMyMessages responseMyMessages);

        void onFailedGetChat(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET("Chat")
        Call<ResponseMyMessages> getChat(@Query("deviceId") String deviceId,@Query("mobileNumber") String mobileNumber,@Query("time")String time);
    }
}
