package psb.com.kidpaint.dailyPrize;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;

public class DialogDailyPrize extends Dialog implements IVDailyPrize {
    private TextView textError, message;

    private Button coin_btn_1, coin_btn_2, coin_btn_3, btnReTry, btn_discard_buy;
    private ProgressBar progressBar;
    private RelativeLayout relContent;
    private PDailyPrize pDailyPrize;

    private String dialogMessage = "";
    private String dialogMode = "";
    private boolean showBtnDiscardBuy = false;

    private ResponseGetDailyPrize mResponseGetDailyPrize;

    private ScorePackageDiscardBtnListener scorePackageDiscardBtnListener;


    private boolean defaultSound = SharePrefrenceHelper.getSoundEffect();
    private boolean defaultMusic = SharePrefrenceHelper.getMusic();

    public DialogDailyPrize(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogDailyPrize(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogDailyPrize(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;

        /*if ("".equals(dialogMessage)) {
            message.setVisibility(View.INVISIBLE);
        } else {
            message.setText(dialogMessage);
            message.setVisibility(View.VISIBLE);
        }*/
    }

    public void setDialogMode(String dialogMode) {
        this.dialogMode = dialogMode;
    }

    public void setShowBtnDiscardBuy(boolean showBtnDiscardBuy) {
        this.showBtnDiscardBuy = showBtnDiscardBuy;
        btn_discard_buy.setVisibility(showBtnDiscardBuy ? View.VISIBLE : View.GONE);

    }

    public void setScorePackageDiscardBtnListener(ScorePackageDiscardBtnListener scorePackageDiscardBtnListener) {
        this.scorePackageDiscardBtnListener = scorePackageDiscardBtnListener;
    }

    public void setResponse(ResponseGetDailyPrize response){
        this.mResponseGetDailyPrize=response;
        setInfo(mResponseGetDailyPrize);
    }

    private void init() {
        setCanceledOnTouchOutside(false);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

        setContentView(R.layout.dialog_daily_prize);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);

                cancel();
            }
        });

        btn_discard_buy = findViewById(R.id.btn_discard_buy);
        btn_discard_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                if (scorePackageDiscardBtnListener != null) {
                    scorePackageDiscardBtnListener.btnDiscardBuySelect();
                }
                cancel();
            }
        });


        progressBar = findViewById(R.id.progressBar);
        message = findViewById(R.id.message);

        relContent = findViewById(R.id.rel_content);
        textError = findViewById(R.id.TextError);
        btnReTry = findViewById(R.id.btn_retry);


        coin_btn_1 = findViewById(R.id.btn_buy_1);
        coin_btn_2 = findViewById(R.id.btn_buy_2);
        coin_btn_3 = findViewById(R.id.btn_buy_3);


        btnReTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        relContent.setVisibility(View.INVISIBLE);

        pDailyPrize = new PDailyPrize(this);

    }

    void setInfo(ResponseGetDailyPrize responseGetDailyPrize) {

        if (responseGetDailyPrize.getExtra().size() >= 3) {

            //===================== btn  ================================================

            coin_btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mResponseGetDailyPrize.getExtra().get(0).getScore() > 0) {
                        pDailyPrize.doBuyDailyPrize(mResponseGetDailyPrize.getExtra().get(0).getId(),mResponseGetDailyPrize.getExtra().get(0).getScore());
                    } else {
                        if (scorePackageDiscardBtnListener != null) {
                            scorePackageDiscardBtnListener.onSuccessBuyDailyPrizeText(mResponseGetDailyPrize.getExtra().get(0).getTitle());
                            cancel();

                        }
                    }

                }
            });
            coin_btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mResponseGetDailyPrize.getExtra().get(1).getScore() > 0) {
                        pDailyPrize.doBuyDailyPrize(mResponseGetDailyPrize.getExtra().get(1).getId(),mResponseGetDailyPrize.getExtra().get(1).getScore());
                    } else {
                        if (scorePackageDiscardBtnListener != null) {
                            scorePackageDiscardBtnListener.onSuccessBuyDailyPrizeText(mResponseGetDailyPrize.getExtra().get(1).getTitle());
                            cancel();

                        }
                    }
                }
            });

            coin_btn_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mResponseGetDailyPrize.getExtra().get(2).getScore() > 0) {
                        pDailyPrize.doBuyDailyPrize(mResponseGetDailyPrize.getExtra().get(2).getId(),mResponseGetDailyPrize.getExtra().get(2).getScore());
                    } else {
                        if (scorePackageDiscardBtnListener != null) {
                            scorePackageDiscardBtnListener.onSuccessBuyDailyPrizeText(mResponseGetDailyPrize.getExtra().get(2).getTitle());
                            cancel();

                        }
                    }
                }
            });


        }


        relContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }




    @Override
    public void startBuyDailyPrize() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessBuyDailyPrize(int prize, ResponseBuyDailyPrize responseBuyDailyPrize) {
        //  Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

        if (scorePackageDiscardBtnListener != null) {
            scorePackageDiscardBtnListener.onSuccessBuyDailyPrizeCoin(prize, responseBuyDailyPrize.getExtra());
            cancel();

        }
    }

    @Override
    public void onFailedBuyDailyPrize(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        if (showBtnDiscardBuy && scorePackageDiscardBtnListener != null) {
            /*scorePackageDiscardBtnListener.onFailedBuyScorePackage();
            cancel();*/

        }
    }

    public interface ScorePackageDiscardBtnListener {
        void btnDiscardBuySelect();

        void onSuccessBuyDailyPrizeCoin(int prize, int totalCoin);

        void onSuccessBuyDailyPrizeText(String text);

        void onFailedBuyScorePackage();
    }
}
