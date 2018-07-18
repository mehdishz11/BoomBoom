package psb.com.kidpaint.webApi.paint;

import android.graphics.Bitmap;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.webApi.paint.postPaint.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.ResponsePostPaint;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Mehdi on 1/29/2018 AD.
 */

public interface iPostPaint {

    void doPostPaint(ParamsPostPaint paramsPostPaint, Bitmap childBitmap);

    interface iResult {
        void onSuccessPostPaint(ResponsePostPaint responsePostPaint);

        void onFailedPostPaint(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @Multipart
        @POST("Paint")
        Call<ResponsePostPaint> postPaint(
                @PartMap() Map<String, RequestBody> model,
                @Part MultipartBody.Part imageUrl
        );
    }
}
