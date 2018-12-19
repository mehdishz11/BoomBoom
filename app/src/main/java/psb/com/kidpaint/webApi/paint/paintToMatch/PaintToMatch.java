package psb.com.kidpaint.webApi.paint.paintToMatch;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.paintToMatch.model.ResponsePaintToMatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class PaintToMatch implements iPaintToMatch {

    private iResult iResult;

    public PaintToMatch() {
    }

    public PaintToMatch(iPaintToMatch.iResult iResult) {
        this.iResult = iResult;
    }

    @Override
    public void doPaintToMatch(String mobile, int paintId) {
        Call<ResponsePaintToMatch> call = new WebService().getClient().create(apiRequest.class).paintToMatch(mobile,paintId);
        call.enqueue(new Callback<ResponsePaintToMatch>() {
            @Override
            public void onResponse(Call<ResponsePaintToMatch> call, Response<ResponsePaintToMatch> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessPaintToMatch(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedPaintToMatch(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedPaintToMatch(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedPaintToMatch(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponsePaintToMatch> call, Throwable t) {
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedPaintToMatch(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
