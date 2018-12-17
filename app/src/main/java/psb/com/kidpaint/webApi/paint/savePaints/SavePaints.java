package psb.com.kidpaint.webApi.paint.savePaints;

import android.graphics.Bitmap;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.savePaints.model.ParamsSavePaint;
import psb.com.kidpaint.webApi.paint.savePaints.model.ResponseSavePaint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class SavePaints implements iSavePaints {

    private iResult iResult;

    public SavePaints() {
    }

    public SavePaints(iSavePaints.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doSavePaint(ParamsSavePaint paramsSavePaint, Bitmap childBitmap) {
        RequestBody requestBody=RequestBody.create(MultipartBody.FORM, new Gson().toJson(paramsSavePaint));
        final MultipartBody.Part userImage = Value.prepareFilePart(App.getContext(),"image",childBitmap);

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(userImage);
        HashMap<String, RequestBody> model = new HashMap<>();
        model.put("model", requestBody);


        Call<ResponseSavePaint> call = new WebService().getClient().create(apiRequest.class).postPaint(model,parts);

        call.enqueue(new Callback<ResponseSavePaint>() {
            @Override
            public void onResponse(Call<ResponseSavePaint> call, Response<ResponseSavePaint> response) {
                if (response.code() == 200) {
                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessSavePaint(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedSavePaint(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedSavePaint(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedSavePaint(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseSavePaint> call, Throwable t) {
                t.printStackTrace();
                if (iResult != null) {
                    iResult.onFailedSavePaint(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
