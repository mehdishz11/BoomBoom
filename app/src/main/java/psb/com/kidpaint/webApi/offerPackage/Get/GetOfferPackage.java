package psb.com.kidpaint.webApi.offerPackage.Get;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.iGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class GetOfferPackage implements iGetOfferPackage {

    private iResult iResult;

    public GetOfferPackage() {
    }

    public GetOfferPackage(iGetOfferPackage.iResult iResult) {
        this.iResult = iResult;
    }




    @Override
    public void doGetOfferPackage(String phoneNumber) {
        Call<ResponseGetOfferPackage> call = new WebService().getClient().create(apiRequest.class).getOfferPackage(phoneNumber);
        call.enqueue(new Callback<ResponseGetOfferPackage>() {
            @Override
            public void onResponse(Call<ResponseGetOfferPackage> call, Response<ResponseGetOfferPackage> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessGetOfferPackage(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedGetOfferPackage(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedGetOfferPackage(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedGetOfferPackage(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGetOfferPackage> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedGetOfferPackage(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
