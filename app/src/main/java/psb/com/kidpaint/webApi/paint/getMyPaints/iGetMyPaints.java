package psb.com.kidpaint.webApi.paint.getMyPaints;

import android.graphics.Bitmap;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.iPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iGetMyPaints {

    void doGetMyPaints(String mobile);

    interface iResult {
        void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints);

        void onFailedGetMyPaints(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @GET(iPaint.apiAddress+"GetMyPaints")
        Call<ResponseGetMyPaints> getMyPaints( @Query("mobile") String mobile);
    }
}
