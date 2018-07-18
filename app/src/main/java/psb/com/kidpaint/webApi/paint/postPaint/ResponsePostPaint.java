
package psb.com.kidpaint.webApi.paint.postPaint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import psb.com.kidpaint.webApi.shareModel.Message;


public class ResponsePostPaint implements Serializable
{

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("extra")
    @Expose
    private Integer extra;
    private final static long serialVersionUID = -6976072409833187691L;

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



    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }



}
