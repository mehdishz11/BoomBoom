package psb.com.kidpaint.webApi.ScorePackage.GetScorePackage;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.chat.Get.iGetChat;
import psb.com.kidpaint.webApi.chat.Get.model.ResponseMyMessages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetScorePackage implements iGetScorePackage {

    private iResult iResult;

    public GetScorePackage() {
    }

    public GetScorePackage(iGetScorePackage.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doGetScorePackage() {
        Call<ResponseGetScorePackage> call = new WebService().getClient().create(apiRequest.class).getScorePackage();
        call.enqueue(new Callback<ResponseGetScorePackage>() {
            @Override
            public void onResponse(Call<ResponseGetScorePackage> call, Response<ResponseGetScorePackage> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetScorePackage(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetScorePackage(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetScorePackage(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetScorePackage(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetScorePackage> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetScorePackage(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
