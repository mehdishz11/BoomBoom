package psb.com.kidpaint.utils.customView.dialog.prize;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.prize.Get.GetPrize;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SeasonDescriptionPrizeFragment extends Fragment {

    private View pView;
    private CardView cardPrize1;
    private ImageView imgPrize1;
    private TextView textPrize1;

    private CardView cardPrize2;
    private ImageView imgPrize2;
    private TextView textPrize2;

    private CardView cardPrize3;
    private ImageView imgPrize3;
    private TextView textPrize3;

    private TextView textExpiredTime;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.prize_desc_season, container, false);
        initViews();
        refresh();
        return pView;
    }
    
    
    private void initViews(){
        textExpiredTime = pView.findViewById(R.id.text_expired_time);

        cardPrize1 = pView.findViewById(R.id.card_prize_1);
        imgPrize1 = pView.findViewById(R.id.image_prize_1);
        textPrize1 = pView.findViewById(R.id.text_prize_1);

        cardPrize2 = pView.findViewById(R.id.card_prize_2);
        imgPrize2 = pView.findViewById(R.id.image_prize_2);
        textPrize2 = pView.findViewById(R.id.text_prize_2);

        cardPrize3 = pView.findViewById(R.id.card_prize_3);
        imgPrize3 = pView.findViewById(R.id.image_prize_3);
        textPrize3 = pView.findViewById(R.id.text_prize_3);

    }


    public void refresh() {
        new GetPrize(new iGetPrize.iResult() {
            @Override
            public void onSuccessGetPrize(ResponsePrize responsePrize) {
                setPrizeList(responsePrize);
            }

            @Override
            public void onFailedGetPrize(int errorId, String ErrorMessage) {

            }
        }).doGetPrize(GetPrize.TYPE_NORMAL);
    }


    public void setPrizeList(ResponsePrize responsePrize) {
        if (responsePrize != null && responsePrize.getExtra() != null && responsePrize.getExtra().size() > 0) {
            switch (responsePrize.getExtra().size()) {
                case (1):

                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(INVISIBLE);
                    cardPrize3.setVisibility(INVISIBLE);
                    break;
                case (2):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(INVISIBLE);
                    break;
                default:

                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize3);
                    textPrize3.setText(responsePrize.getExtra().get(2).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(VISIBLE);

                    break;

            }

        } else {
            cardPrize1.setVisibility(INVISIBLE);
            cardPrize2.setVisibility(INVISIBLE);
            cardPrize3.setVisibility(INVISIBLE);
        }
    }
}
