package psb.com.kidpaint.webApi.paint.postPaint;

import android.graphics.Bitmap;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.webApi.paint.postPaint.model.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;



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
