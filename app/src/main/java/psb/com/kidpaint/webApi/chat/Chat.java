package psb.com.kidpaint.webApi.chat;

import psb.com.kidpaint.webApi.chat.Get.GetChat;
import psb.com.kidpaint.webApi.chat.Get.iGetChat;
import psb.com.kidpaint.webApi.chat.sendMessage.SendMessage;
import psb.com.kidpaint.webApi.chat.sendMessage.iSendMessage;

public class Chat implements iChat {
    @Override
    public GetChat getChat() {
        return new GetChat();
    }

    @Override
    public GetChat getChat(iGetChat.iResult iResult) {
        return new GetChat(iResult);
    }

    @Override
    public SendMessage sendMessage() {
        return new SendMessage();
    }

    @Override
    public SendMessage sendMessage(iSendMessage.iResult iResult) {
        return new SendMessage(iResult);
    }
}
