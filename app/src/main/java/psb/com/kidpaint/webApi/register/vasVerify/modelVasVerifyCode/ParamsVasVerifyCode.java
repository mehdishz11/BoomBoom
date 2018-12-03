
package psb.com.kidpaint.webApi.register.vasVerify.modelVasVerifyCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsVasVerifyCode implements Serializable
{
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("refrenceCode")
    @Expose
    private String refrenceCode;
    @SerializedName("irancellToken")
    @Expose
    private String irancellToken;
    @SerializedName("deviceModel")
    @Expose
    private String deviceModel;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("osType")
    @Expose
    private String osType;
    @SerializedName("osVersion")
    @Expose
    private String osVersion;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    private final static long serialVersionUID = -4846983007236128103L;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRefrenceCode() {
        return refrenceCode;
    }

    public void setRefrenceCode(String refrenceCode) {
        this.refrenceCode = refrenceCode;
    }

    public String getIrancellToken() {
        return irancellToken;
    }

    public void setIrancellToken(String irancellToken) {
        this.irancellToken = irancellToken;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
