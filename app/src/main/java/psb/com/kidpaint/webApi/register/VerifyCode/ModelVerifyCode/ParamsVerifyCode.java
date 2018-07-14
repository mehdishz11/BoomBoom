
package psb.com.kidpaint.webApi.register.VerifyCode.ModelVerifyCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsVerifyCode implements Serializable
{

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("code")
    @Expose
    private String code;
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
    private final static long serialVersionUID = -2029644076027878680L;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ParamsVerifyCode withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ParamsVerifyCode withCode(String code) {
        this.code = code;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ParamsVerifyCode withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Long getOsType() {
        return osType;
    }

    public void setOsType(Long osType) {
        this.osType = osType;
    }

    public ParamsVerifyCode withOsType(Long osType) {
        this.osType = osType;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public ParamsVerifyCode withOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public ParamsVerifyCode withFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public ParamsVerifyCode withAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }
}
