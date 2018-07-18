
package psb.com.kidpaint.webApi.paint.getMyPaints.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import psb.com.kidpaint.webApi.shareModel.Message;

public class ResponseGetMyPaints implements Serializable
{

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("extra")
    @Expose
    private List<MyPaint> myPaint;
    private final static long serialVersionUID = 8868412178320185013L;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<MyPaint> getMyPaint() {
        return myPaint;
    }

    public void setMyPaint(List<MyPaint> myPaint) {
        this.myPaint = myPaint;
    }
}
