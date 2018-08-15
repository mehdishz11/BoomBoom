package psb.com.kidpaint.webApi.offerPackage.buy;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.offerPackage.Get.iGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 8/15/2018 AD.
 */

public class BuyOfferPackage implements iBuyOfferPackage {

    private iResult iResult;

    public BuyOfferPackage() {
    }

    public BuyOfferPackage(iBuyOfferPackage.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doBuyOfferPackage(String phoneNumber, int offerPackageId) {
        Call<ResponseBuyOfferPackage> call = new WebService().getClient().create(apiRequest.class).buyOfferPackage(phoneNumber,offerPackageId);
        call.enqueue(new Callback<ResponseBuyOfferPackage>() {
            @Override
            public void onResponse(Call<ResponseBuyOfferPackage> call, Response<ResponseBuyOfferPackage> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessBuyOfferPackage(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedBuyOfferPackage(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedBuyOfferPackage(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedBuyOfferPackage(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBuyOfferPackage> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedBuyOfferPackage(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
