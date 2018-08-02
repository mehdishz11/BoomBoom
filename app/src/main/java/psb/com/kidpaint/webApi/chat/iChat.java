package psb.com.kidpaint.webApi.chat;

import psb.com.kidpaint.webApi.chat.Get.GetChat;
import psb.com.kidpaint.webApi.chat.Get.iGetChat;
import psb.com.kidpaint.webApi.chat.sendMessage.SendMessage;
import psb.com.kidpaint.webApi.chat.sendMessage.iSendMessage;

public interface iChat {

    GetChat getChat();
    GetChat getChat(iGetChat.iResult iResult);

    SendMessage sendMessage();
    SendMessage sendMessage(iSendMessage.iResult iResult);
}
