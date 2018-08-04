
package psb.com.kidpaint.utils.firebase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Push implements Serializable
{

    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = 2925281160491192216L;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
