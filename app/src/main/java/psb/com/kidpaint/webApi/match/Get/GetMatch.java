package psb.com.kidpaint.webApi.match.Get;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.match.Get.model.ResponseGetMatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetMatch implements iGetMatch {

    private iResult iResult;

    public GetMatch() {
    }

    public GetMatch(iGetMatch.iResult iResult) {
        this.iResult = iResult;
    }
    

    @Override
    public void doGetGetMatch(int level) {
        Call<ResponseGetMatch> call = new WebService().getClient().create(apiRequest.class).getMatch(level);
        call.enqueue(new Callback<ResponseGetMatch>() {
            @Override
            public void onResponse(Call<ResponseGetMatch> call, Response<ResponseGetMatch> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetMatch(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetMatch(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetMatch(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetMatch(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetMatch> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetMatch(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
