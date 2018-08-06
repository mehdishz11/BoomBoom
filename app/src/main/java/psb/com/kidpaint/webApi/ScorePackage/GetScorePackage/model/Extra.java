
package psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lastEditDate")
    @Expose
    private String lastEditDate;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("discountPercent")
    @Expose
    private Integer discountPercent;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("sku")
    @Expose
    private String sku;
    private final static long serialVersionUID = -6853707770360695329L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
