package psb.com.kidpaint.myMessages;

import android.content.Context;

import psb.com.kidpaint.myMessages.adapter.ViewHolder_Message;

public class PMessages implements IPMessages  {
    private Context context;
    private IVMessages ivMessages;
    private MMessages mMessage;

    public PMessages(IVMessages ivMessages) {
        this.ivMessages = ivMessages;
        this.context=ivMessages.getContext();
        this.mMessage=new MMessages(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getMessageFromServer(int loadMode) {
       ivMessages.startGetMessageFromServer(loadMode);
       mMessage.getMessageFromServer();
    }

    @Override
    public void onSuccessGetMessageFromServer() {
        ivMessages.onSuccessGetMessageFromServer();

    }

    @Override
    public void onFailedGetMessageFromServer(int errorCode,String errorMessage) {
        ivMessages.onFailedGetMessageFromServer(errorCode, errorMessage);

    }

    @Override
    public void getMessageFromDb() {
        ivMessages.startGetMessageFromDb();
        mMessage.getMessageFromDb();

    }

    @Override
    public void onSuccessGetMessageFromDb() {
        ivMessages.onSuccessGetMessageFromDb();

    }

    @Override
    public void onFailedGetMessageFromDb(int errorCode,String errorMessage) {
        ivMessages.onFailedGetMessageFromDb(errorCode, errorMessage);

    }

    @Override
    public void sendMessage(String text) {
     ivMessages.startSendMessage();
     mMessage.sendMessage(text);
    }

    @Override
    public void onSuccessSendMessage() {
       ivMessages.onSuccessSendMessage();
    }

    @Override
    public void onFailedSendMessage(int errorCode,String errorMessage) {
    ivMessages.onFailedSendMessage(errorCode, errorMessage);
    }

    @Override
    public void onBindView_Message(ViewHolder_Message holder, int position) {

    }

    @Override
    public boolean userIsRegistered() {
        return mMessage.userIsRegistered();
    }

    @Override
    public int getArrSize() {
        return mMessage.getArrSize();
    }

    @Override
    public int getRowType(int position) {
        return mMessage.getRowType(position);
    }
}
