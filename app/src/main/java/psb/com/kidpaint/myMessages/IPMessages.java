package psb.com.kidpaint.myMessages;

import android.content.Context;

import psb.com.kidpaint.myMessages.adapter.ViewHolder_Message;

public interface IPMessages {
    Context getContext();

    void getMessageFromServer(int loadMode);
    void onSuccessGetMessageFromServer();
    void onFailedGetMessageFromServer(int errorCode,String errorMessage);

    void getMessageFromDb();
    void onSuccessGetMessageFromDb();
    void onFailedGetMessageFromDb(int errorCode,String errorMessage);

    void sendMessage(String text);
    void onSuccessSendMessage();
    void onFailedSendMessage(int errorCode,String errorMessage);

    void onBindView_Message(ViewHolder_Message holder, int position);

    boolean userIsRegistered();

   int getArrSize();
   int getRowType(int position);



}
