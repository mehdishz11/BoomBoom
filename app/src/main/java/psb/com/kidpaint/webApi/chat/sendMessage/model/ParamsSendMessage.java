
package psb.com.kidpaint.webApi.chat.sendMessage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsSendMessage implements Serializable
{

    @SerializedName("chatId")
    @Expose
    private Long chatId;
    @SerializedName("listPosition")
    @Expose
    private Integer listPosition;
    @SerializedName("dbId")
    @Expose
    private Long dbId;
    @SerializedName("deviceId")
    @Expose
    private String securityCode;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("description")
    @Expose
    private String description;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getListPosition() {
        return listPosition;
    }

    public void setListPosition(Integer listPosition) {
        this.listPosition = listPosition;
    }
}
