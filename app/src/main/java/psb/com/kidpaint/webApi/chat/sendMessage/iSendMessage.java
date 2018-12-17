package psb.com.kidpaint.webApi.chat.sendMessage;

import psb.com.kidpaint.webApi.chat.sendMessage.model.ParamsSendMessage;
import psb.com.kidpaint.webApi.chat.sendMessage.model.ResponseSendMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by morteza on 1/29/2018 AD.
 */

public interface iSendMessage {

    void doSendMessage(ParamsSendMessage paramsSendMessage);

    interface iResult {
        void onSuccessSendMessage(ResponseSendMessage responseSendMessage);

        void onFailedSendMessage(int errorId, String ErrorMessage);
    }

    interface apiRequest {
        @POST("Chat")
        Call<ResponseSendMessage> sendMessage(@Body()ParamsSendMessage paramsSendMessage);
    }
}
