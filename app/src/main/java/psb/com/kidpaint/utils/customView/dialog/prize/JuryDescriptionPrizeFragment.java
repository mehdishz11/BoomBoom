package psb.com.kidpaint.utils.customView.dialog.prize;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
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

public class JuryDescriptionPrizeFragment extends Fragment {

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

    private CardView cardPrize4;
    private ImageView imgPrize4;
    private TextView textPrize4;

    private CardView cardPrize5;
    private ImageView imgPrize5;
    private TextView textPrize5;

    private CardView cardPrize6;
    private ImageView imgPrize6;
    private TextView textPrize6;

    private HorizontalScrollView horizontalScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pView = inflater.inflate(R.layout.prize_jury_description, container, false);
        initViews();
        refresh();
        return pView;
    }


    private void initViews() {

        horizontalScrollView=pView.findViewById(R.id.horizontal_scroll);

        horizontalScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                horizontalScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        cardPrize1 = pView.findViewById(R.id.card_prize_1);
        imgPrize1 = pView.findViewById(R.id.image_prize_1);
        textPrize1 = pView.findViewById(R.id.text_prize_1);

        cardPrize2 = pView.findViewById(R.id.card_prize_2);
        imgPrize2 = pView.findViewById(R.id.image_prize_2);
        textPrize2 = pView.findViewById(R.id.text_prize_2);

        cardPrize3 = pView.findViewById(R.id.card_prize_3);
        imgPrize3 = pView.findViewById(R.id.image_prize_3);
        textPrize3 = pView.findViewById(R.id.text_prize_3);


        cardPrize4 = pView.findViewById(R.id.card_prize_10);
        imgPrize4 = pView.findViewById(R.id.image_prize_10);
        textPrize4 = pView.findViewById(R.id.text_prize_10);

        cardPrize5 = pView.findViewById(R.id.card_prize_20);
        imgPrize5 = pView.findViewById(R.id.image_prize_20);
        textPrize5 = pView.findViewById(R.id.text_prize_20);

        cardPrize6 = pView.findViewById(R.id.card_prize_30);
        imgPrize6 = pView.findViewById(R.id.image_prize_30);
        textPrize6 = pView.findViewById(R.id.text_prize_30);

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
        }).doGetPrize(GetPrize.TYPE_JUDGE);
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
                    cardPrize4.setVisibility(INVISIBLE);
                    cardPrize5.setVisibility(INVISIBLE);
                    cardPrize6.setVisibility(INVISIBLE);
                    break;
                case (2):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(INVISIBLE);
                    cardPrize4.setVisibility(INVISIBLE);
                    cardPrize5.setVisibility(INVISIBLE);
                    cardPrize6.setVisibility(INVISIBLE);
                    break;
                case (3):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize3);
                    textPrize3.setText(responsePrize.getExtra().get(2).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(VISIBLE);
                    cardPrize4.setVisibility(INVISIBLE);
                    cardPrize5.setVisibility(INVISIBLE);
                    cardPrize6.setVisibility(INVISIBLE);
                    break;
                case (4):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize3);
                    textPrize3.setText(responsePrize.getExtra().get(2).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(3).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize4);
                    textPrize4.setText(responsePrize.getExtra().get(3).getTitle());


                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(VISIBLE);
                    cardPrize4.setVisibility(VISIBLE);
                    cardPrize5.setVisibility(INVISIBLE);
                    cardPrize6.setVisibility(INVISIBLE);
                    break;
                case (5):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize3);
                    textPrize3.setText(responsePrize.getExtra().get(2).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(3).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize4);
                    textPrize4.setText(responsePrize.getExtra().get(3).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(4).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize5);
                    textPrize5.setText(responsePrize.getExtra().get(4).getTitle());


                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(VISIBLE);
                    cardPrize4.setVisibility(VISIBLE);
                    cardPrize5.setVisibility(VISIBLE);
                    cardPrize6.setVisibility(INVISIBLE);
                    break;
                case (6):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize3);
                    textPrize3.setText(responsePrize.getExtra().get(2).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(3).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize4);
                    textPrize4.setText(responsePrize.getExtra().get(3).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(4).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize5);
                    textPrize5.setText(responsePrize.getExtra().get(4).getTitle());


                    Picasso.get().load(responsePrize.getExtra().get(5).getImageUrl()).resize(Value.dp(80), Value.dp(80)).into(imgPrize6);
                    textPrize6.setText(responsePrize.getExtra().get(5).getTitle());


                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(VISIBLE);
                    cardPrize4.setVisibility(VISIBLE);
                    cardPrize5.setVisibility(VISIBLE);
                    cardPrize6.setVisibility(VISIBLE);
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
            cardPrize4.setVisibility(INVISIBLE);
            cardPrize5.setVisibility(INVISIBLE);
            cardPrize6.setVisibility(INVISIBLE);
        }
    }
}
