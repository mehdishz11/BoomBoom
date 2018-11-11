package psb.com.kidpaint.utils.customView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

public class BasketPrize extends RelativeLayout {

    private CardView cardPrize1;
    private ImageView imgPrize1;
    private TextView textPrize1;

    private CardView cardPrize2;
    private ImageView imgPrize2;
    private TextView textPrize2;

    private CardView cardPrize3;
    private ImageView imgPrize3;
    private TextView textPrize3;


    public BasketPrize(Context context) {
        super(context);
        init();
    }

    public BasketPrize(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasketPrize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.rel_basket_prize, this);

        cardPrize1 = findViewById(R.id.card_prize_1);
        imgPrize1 = findViewById(R.id.image_prize_1);
        textPrize1 = findViewById(R.id.text_prize_1);

        cardPrize2 = findViewById(R.id.card_prize_2);
        imgPrize2 = findViewById(R.id.image_prize_2);
        textPrize2 = findViewById(R.id.text_prize_2);

        cardPrize3 = findViewById(R.id.card_prize_3);
        imgPrize3 = findViewById(R.id.image_prize_3);
        textPrize3 = findViewById(R.id.text_prize_3);


    }

    public void setPrizeList(ResponsePrize responsePrize) {
        if (responsePrize != null && responsePrize.getExtra() != null && responsePrize.getExtra().size() > 0) {
            switch (responsePrize.getExtra().size()) {
                case (1):

                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80),Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(INVISIBLE);
                    cardPrize3.setVisibility(INVISIBLE);
                    break;
                case (2):
                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80),Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80),Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(INVISIBLE);
                    break;
                default:

                    Picasso.get().load(responsePrize.getExtra().get(0).getImageUrl()).resize(Value.dp(80),Value.dp(80)).into(imgPrize1);
                    textPrize1.setText(responsePrize.getExtra().get(0).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(1).getImageUrl()).resize(Value.dp(80),Value.dp(80)).into(imgPrize2);
                    textPrize2.setText(responsePrize.getExtra().get(1).getTitle());

                    Picasso.get().load(responsePrize.getExtra().get(2).getImageUrl()).resize(Value.dp(80),Value.dp(80)).into(imgPrize3);
                    textPrize3.setText(responsePrize.getExtra().get(2).getTitle());

                    cardPrize1.setVisibility(VISIBLE);
                    cardPrize2.setVisibility(VISIBLE);
                    cardPrize3.setVisibility(VISIBLE);

                    break;

            }

        }
    }

}
