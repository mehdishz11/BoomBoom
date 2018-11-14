package psb.com.kidpaint.utils.customView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.webApi.match.Get.model.ResponseGetMatch;
import psb.com.kidpaint.webApi.prize.Get.GetPrize;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class DialogPrizeDescription extends Dialog {

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

    public DialogPrizeDescription(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogPrizeDescription(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogPrizeDescription(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    private void init() {
        setCanceledOnTouchOutside(false);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setContentView(R.layout.content_dialog_prize);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        textExpiredTime = findViewById(R.id.text_expired_time);

        cardPrize1 = findViewById(R.id.card_prize_1);
        imgPrize1 = findViewById(R.id.image_prize_1);
        textPrize1 = findViewById(R.id.text_prize_1);

        cardPrize2 = findViewById(R.id.card_prize_2);
        imgPrize2 = findViewById(R.id.image_prize_2);
        textPrize2 = findViewById(R.id.text_prize_2);

        cardPrize3 = findViewById(R.id.card_prize_3);
        imgPrize3 = findViewById(R.id.image_prize_3);
        textPrize3 = findViewById(R.id.text_prize_3);


        refresh();
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
        }).doGetPrize();

       /* new Match().getMatch(new iGetMatch.iResult() {
            @Override
            public void onSuccessGetMatch(ResponseGetMatch responseGetMatch) {
                setExpiredTime(responseGetMatch);
            }

            @Override
            public void onFailedGetMatch(int errorCode, String ErrorMessage) {

            }
        }).doGetGetMatch(1);*/
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

    public void setExpiredTime(ResponseGetMatch matchModel) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());

        Log.d(App.TAG, "setExpiredTime1: "+currentDateandTime);
        Log.d(App.TAG, "setExpiredTime2: "+matchModel.getExtra().get(0).getEndDate());


        try {
            Date date1 = simpleDateFormat.parse(currentDateandTime);
            Date date2 = simpleDateFormat.parse(matchModel.getExtra().get(0).getEndDate());

            printDifference(date1, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        textExpiredTime.setText(matchModel.getExtra().get(0).getEndDate());
        textExpiredTime.setVisibility(VISIBLE);
    }


    //1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400
    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Log.d(App.TAG, "setExpiredTime: Days: "+elapsedDays);
        Log.d(App.TAG, "setExpiredTime: Hours: "+elapsedHours);
        Log.d(App.TAG, "setExpiredTime: minute: "+elapsedMinutes);
        Log.d(App.TAG, "setExpiredTime: second: "+elapsedSeconds);


    }

}
