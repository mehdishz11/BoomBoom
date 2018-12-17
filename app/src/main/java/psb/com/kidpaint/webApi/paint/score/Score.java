package psb.com.kidpaint.webApi.paint.score;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class Score implements iScore {

    private iResult iResult;

    public Score() {
    }

    public Score(iScore.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doScore(ParamsScore paramsScore) {
        Call<ResponseScore> call = new WebService().getClient().create(apiRequest.class).setScore(paramsScore);
        call.enqueue(new Callback<ResponseScore>() {
            @Override
            public void onResponse(Call<ResponseScore> call, Response<ResponseScore> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessScore(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedScore(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedScore(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedScore(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseScore> call, Throwable t) {
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedScore(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
