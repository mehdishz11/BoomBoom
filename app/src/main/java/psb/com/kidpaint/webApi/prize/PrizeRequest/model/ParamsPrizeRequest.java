package psb.com.kidpaint.webApi.prize.PrizeRequest.model;

public class ParamsPrizeRequest {

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    private String mobile;
    private int prizeId;
}
