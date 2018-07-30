package psb.com.kidpaint.myMessages;

import android.content.Context;

public interface IMMessages {
    Context getContext();

    void getMessageFromServer();

    void getMessageFromDb();

    void sendMessage(String text);

   int getArrSize();
   int getRowType(int position);

   boolean userIsRegistered();
   String getPositionAtMessage(int position);



}
