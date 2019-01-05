
package psb.com.kidpaint.webApi.register.fcmToken.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsFcmToken implements Serializable
{

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("osType")
    @Expose
    private Long osType;
    @SerializedName("osVersion")
    @Expose
    private String osVersion;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("jwtToken")
    @Expose
    private String jwtToken;
    @SerializedName("isVas")
    @Expose
    private boolean isVas;

    private final static long serialVersionUID = -2029644076027878680L;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ParamsFcmToken withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ParamsFcmToken withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Long getOsType() {
        return osType;
    }

    public void setOsType(Long osType) {
        this.osType = osType;
    }

    public ParamsFcmToken withOsType(Long osType) {
        this.osType = osType;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public ParamsFcmToken withOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public ParamsFcmToken withFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public ParamsFcmToken withAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public boolean isVas() {
        return isVas;
    }

    public void setVas(boolean vas) {
        isVas = vas;
    }
}
