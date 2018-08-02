package psb.com.kidpaint.webApi.chat.sendMessage;

import android.util.Log;

import psb.com.kidpaint.utils.ErrorMessage;
import psb.com.kidpaint.utils.WebService;
import psb.com.kidpaint.webApi.chat.Get.iGetChat;
import psb.com.kidpaint.webApi.chat.Get.model.ResponseMyMessages;
import psb.com.kidpaint.webApi.chat.sendMessage.model.ParamsSendMessage;
import psb.com.kidpaint.webApi.chat.sendMessage.model.ResponseSendMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public class SendMessage implements iSendMessage {

    private iResult iResult;

    public SendMessage() {
    }

    public SendMessage(iSendMessage.iResult iResult) {
        this.iResult = iResult;
    }


    @Override
    public void doSendMessage(ParamsSendMessage paramsSendMessage) {
        Call<ResponseSendMessage> call = new WebService().getClient().create(apiRequest.class).sendMessage(paramsSendMessage);
        call.enqueue(new Callback<ResponseSendMessage>() {
            @Override
            public void onResponse(Call<ResponseSendMessage> call, Response<ResponseSendMessage> response) {
                if (response.code() == 200) {

                    if (response.body().getOk()) {
                        if (iResult != null) {
                            iResult.onSuccessSendMessage(response.body());
                        }
                    } else {
                        if(response.body().getMessages().size()>0) {
                            iResult.onFailedSendMessage(0, response.body().getMessages().get(0).getDescription());
                        }else{
                            iResult.onFailedSendMessage(0, ErrorMessage.ERROR_NETWORK_SERVER_SIDE.getErrorMessage());
                        }
                    }
                } else {
                    if (iResult != null) {
                        iResult.onFailedSendMessage(response.code(),ErrorMessage.getErrorByCode(response.code()));

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseSendMessage> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                t.printStackTrace();
                if (iResult != null && !call.isCanceled()) {
                    iResult.onFailedSendMessage(0, ErrorMessage.ERROR_NETWORK_UNAVALABLE.getErrorMessage());
                }
            }
        });
    }
}
