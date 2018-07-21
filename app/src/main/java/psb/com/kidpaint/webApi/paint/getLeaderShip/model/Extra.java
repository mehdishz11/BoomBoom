
package psb.com.kidpaint.webApi.paint.getLeaderShip.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra implements Serializable
{

    @SerializedName("model")
    @Expose
    private List<LeaderModel> leaderModel = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("myRank")
    @Expose
    private LeaderModel myRank;
    @SerializedName("myScore")
    @Expose
    private int myScore;
    private final static long serialVersionUID = -5377366626874763874L;

    public List<LeaderModel> getLeaderModel() {
        return leaderModel;
    }

    public void setLeaderModel(List<LeaderModel> leaderModel) {
        this.leaderModel = leaderModel;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LeaderModel getMyRank() {
        return myRank;
    }

    public void setMyRank(LeaderModel myRank) {
        this.myRank = myRank;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }
}
