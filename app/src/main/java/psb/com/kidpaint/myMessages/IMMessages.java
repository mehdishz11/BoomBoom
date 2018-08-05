package psb.com.kidpaint.myMessages;

import android.content.Context;

import psb.com.kidpaint.webApi.chat.Get.model.Extra;

public interface IMMessages {
    Context getContext();

    void getMessageFromServer();

    void getMessageFromDb(int loadMod);

    void sendMessage(String text);

   int getArrSize();
   int getRowType(int position);

   boolean userIsRegistered();
   Extra getPositionAtMessage(int position);

   String getUserName();
   String getUserImage();

    void deleteMessage(long messageId,int position);

    int getFirstUnreadMessagePosition();
    void setAllMessageToRead();



}
