package psb.com.kidpaint.myMessages;

import android.content.Context;

public interface IVMessages {
    Context getContext();

    void startGetMessageFromServer(int loadMode);
    void onSuccessGetMessageFromServer();
    void onFailedGetMessageFromServer(int errorCode,String errorMessage);

    void startGetMessageFromDb(int loadMode);
    void onSuccessGetMessageFromDb();
    void onFailedGetMessageFromDb(int errorCode,String errorMessage);


    void startSendMessage();
    void onSuccessSendMessage(int position,boolean sendToServer);
    void onFailedSendMessage(int errorCode,String errorMessage,int position);;


    void setSendMessage(String text);
    void onSuccessDeleteMessage(int position);

    void startActionView(String url);


}
