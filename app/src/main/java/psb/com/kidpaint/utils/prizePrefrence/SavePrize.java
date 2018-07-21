package psb.com.kidpaint.utils.prizePrefrence;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.webApi.prize.Get.model.Extra;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

public class SavePrize {

    private ResponsePrize responsePrize;
    private Context context;

    private String PRIZE_MASTER_KEY = "PRIZE_MASTER_KEY";

    private String PRIZE_ID_0 = "PRIZE_ID_0";
    private String PRIZE_ID_1 = "PRIZE_ID_1";
    private String PRIZE_ID_2 = "PRIZE_ID_2";

    private String PRIZE_title_0 = "PRIZE_title_0";
    private String PRIZE_title_1 = "PRIZE_title_1";
    private String PRIZE_title_2 = "PRIZE_title_2";

    private String PRIZE_imageUrl_0 = "PRIZE_imageUrl_0";
    private String PRIZE_imageUrl_1 = "PRIZE_imageUrl_1";
    private String PRIZE_imageUrl_2 = "PRIZE_imageUrl_2";

    private String PRIZE_needScore_0 = "PRIZE_needScore_0";
    private String PRIZE_needScore_1 = "PRIZE_needScore_1";
    private String PRIZE_needScore_2 = "PRIZE_needScore_2";

    public SavePrize(Context context){
        this.context = context;
    }

    //////setter/////////

    public void savePrizeToPrefrence(ResponsePrize responsePrize){
        this.responsePrize = responsePrize;
        Utils.setIntegerPreference(context,PRIZE_MASTER_KEY,PRIZE_ID_0, responsePrize.getExtra().get(0).getId());
        Utils.setIntegerPreference(context,PRIZE_MASTER_KEY,PRIZE_ID_1, responsePrize.getExtra().get(1).getId());
        Utils.setIntegerPreference(context,PRIZE_MASTER_KEY,PRIZE_ID_2, responsePrize.getExtra().get(2).getId());

        Utils.setStringPreference(context, PRIZE_MASTER_KEY, PRIZE_title_0, responsePrize.getExtra().get(0).getTitle());
        Utils.setStringPreference(context, PRIZE_MASTER_KEY, PRIZE_title_1, responsePrize.getExtra().get(1).getTitle());
        Utils.setStringPreference(context, PRIZE_MASTER_KEY, PRIZE_title_2, responsePrize.getExtra().get(2).getTitle());

        Utils.setStringPreference(context, PRIZE_MASTER_KEY, PRIZE_imageUrl_0, responsePrize.getExtra().get(0).getImageUrl());
        Utils.setStringPreference(context, PRIZE_MASTER_KEY, PRIZE_imageUrl_1, responsePrize.getExtra().get(1).getImageUrl());
        Utils.setStringPreference(context, PRIZE_MASTER_KEY, PRIZE_imageUrl_2, responsePrize.getExtra().get(2).getImageUrl());

        Utils.setIntegerPreference(context,PRIZE_MASTER_KEY,PRIZE_needScore_0, responsePrize.getExtra().get(0).getNeedScore());
        Utils.setIntegerPreference(context,PRIZE_MASTER_KEY,PRIZE_needScore_1, responsePrize.getExtra().get(1).getNeedScore());
        Utils.setIntegerPreference(context,PRIZE_MASTER_KEY,PRIZE_needScore_2, responsePrize.getExtra().get(2).getNeedScore());
    }

    public ResponsePrize getResponsePrize(){

        ResponsePrize responsePrize = new ResponsePrize();
        List<Extra> extraList = new ArrayList<>();
        extraList.clear();

        Extra extra = new Extra();
        extra.setId(Utils.getIntegerPreference(context, PRIZE_MASTER_KEY, PRIZE_ID_0,0));
        extra.setTitle(Utils.getStringPreference(context, PRIZE_MASTER_KEY, PRIZE_title_0,""));
        extra.setImageUrl(Utils.getStringPreference(context, PRIZE_MASTER_KEY, PRIZE_imageUrl_0,""));
        extra.setNeedScore(Utils.getIntegerPreference(context, PRIZE_MASTER_KEY, PRIZE_needScore_0,0));
        extraList.add(extra);

        extra = new Extra();
        extra.setId(Utils.getIntegerPreference(context, PRIZE_MASTER_KEY, PRIZE_ID_1,0));
        extra.setTitle(Utils.getStringPreference(context, PRIZE_MASTER_KEY, PRIZE_title_1,""));
        extra.setImageUrl(Utils.getStringPreference(context, PRIZE_MASTER_KEY, PRIZE_imageUrl_1,""));
        extra.setNeedScore(Utils.getIntegerPreference(context, PRIZE_MASTER_KEY, PRIZE_needScore_1,0));
        extraList.add(extra);

        extra = new Extra();
        extra.setId(Utils.getIntegerPreference(context, PRIZE_MASTER_KEY, PRIZE_ID_2,0));
        extra.setTitle(Utils.getStringPreference(context, PRIZE_MASTER_KEY, PRIZE_title_2,""));
        extra.setImageUrl(Utils.getStringPreference(context, PRIZE_MASTER_KEY, PRIZE_imageUrl_2,""));
        extra.setNeedScore(Utils.getIntegerPreference(context, PRIZE_MASTER_KEY, PRIZE_needScore_2,0));
        extraList.add(extra);

        responsePrize.setExtra(extraList);
        responsePrize.setOk(true);
        responsePrize.setMessages(null);

        return responsePrize;
    }
}
