package psb.com.kidpaint.score;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public class DialogScorePackage extends Dialog implements IVScorePackage {
    private TextView coin_title_1, coin_title_2, coin_title_3, textError, message;
    private TextView coin_title_1_1, coin_title_2_1, coin_title_3_1;
    private TextView coin_coin_1, coin_coin_2, coin_coin_3;

    private TextView coin_discount_1, coin_discount_2, coin_discount_3;
    private ImageView coin_image_1, coin_image_2, coin_image_3;
    private Button coin_btn_1, coin_btn_2, coin_btn_3, btnReTry, btn_discard_buy;
    private ProgressBar progressBar;
    private RelativeLayout relContent;
    private PScorePackage pScorePackage;

    private RelativeLayout relDiscount1,relDiscount2,relDiscount3;


    private String dialogMessage = "";
    private String dialogMode = "";
    private boolean showBtnDiscardBuy = false;

    private ScorePackageDiscardBtnListener scorePackageDiscardBtnListener;


    private boolean defaultSound = SharePrefrenceHelper.getSoundEffect();
    private boolean defaultMusic = SharePrefrenceHelper.getMusic();

    public DialogScorePackage(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogScorePackage(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogScorePackage(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;

        if ("".equals(dialogMessage)) {
            message.setVisibility(View.INVISIBLE);
        } else {
            message.setText(dialogMessage);
            message.setVisibility(View.VISIBLE);
        }
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

        setContentView(R.layout.dialog_score_package);
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
                    scorePackageDiscardBtnListener.btnDiscardBuySelect(dialogMode);
                }
                cancel();
            }
        });


        progressBar = findViewById(R.id.progressBar);
        message = findViewById(R.id.message);

        relContent = findViewById(R.id.rel_content);
        textError = findViewById(R.id.TextError);
        btnReTry = findViewById(R.id.btn_retry);

        coin_title_1 = findViewById(R.id.coin_title_1_1);
        coin_title_2 = findViewById(R.id.coin_title_2_1);
        coin_title_3 = findViewById(R.id.coin_title_3_1);


        coin_title_1_1 = findViewById(R.id.coin_title_1_2);
        coin_title_2_1 = findViewById(R.id.coin_title_2_2);
        coin_title_3_1 = findViewById(R.id.coin_title_3_2);



        coin_coin_1 = findViewById(R.id.coin_coin_1);
        coin_coin_2 = findViewById(R.id.coin_coin_2);
        coin_coin_3 = findViewById(R.id.coin_coin_3);


        coin_discount_1 = findViewById(R.id.coin_discount_1);
        coin_discount_2 = findViewById(R.id.coin_discount_2);
        coin_discount_3 = findViewById(R.id.coin_discount_3);

        coin_image_1 = findViewById(R.id.coin_image_1);
        coin_image_2 = findViewById(R.id.coin_image_2);
        coin_image_3 = findViewById(R.id.coin_image_3);

        coin_btn_1 = findViewById(R.id.btn_buy_1);
        coin_btn_2 = findViewById(R.id.btn_buy_2);
        coin_btn_3 = findViewById(R.id.btn_buy_3);

        relDiscount1= findViewById(R.id.rel_discount_1);
        relDiscount2 = findViewById(R.id.rel_discount_2);
        relDiscount3 = findViewById(R.id.rel_discount_3);


        btnReTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pScorePackage.getScorePackage();

            }
        });
        relContent.setVisibility(View.INVISIBLE);

        pScorePackage = new PScorePackage(this);
        pScorePackage.getScorePackage();

    }

    void setInfo(ResponseGetScorePackage responseGetScorePackage) {

        if (responseGetScorePackage.getExtra().size() >= 3) {
            coin_title_1.setText(responseGetScorePackage.getExtra().get(0).getTitle());
            coin_title_2.setText(responseGetScorePackage.getExtra().get(1).getTitle());
            coin_title_3.setText(responseGetScorePackage.getExtra().get(2).getTitle());

            coin_title_1_1.setText(responseGetScorePackage.getExtra().get(0).getTitle());
            coin_title_2_1.setText(responseGetScorePackage.getExtra().get(1).getTitle());
            coin_title_3_1.setText(responseGetScorePackage.getExtra().get(2).getTitle());

            coin_coin_1.setText( Utils.LongToCurrency(responseGetScorePackage.getExtra().get(0).getScore())+" "+ getContext().getString(R.string.coin));
            coin_coin_2.setText(Utils.LongToCurrency(responseGetScorePackage.getExtra().get(1).getScore())+" "+ getContext().getString(R.string.coin));
            coin_coin_3.setText(Utils.LongToCurrency(responseGetScorePackage.getExtra().get(2).getScore())+" "+ getContext().getString(R.string.coin));

            coin_btn_1.setText(responseGetScorePackage.getExtra().get(0).getPrice() +" "+ getContext().getString(R.string.price_unit));
            coin_btn_2.setText(responseGetScorePackage.getExtra().get(1).getPrice() +" "+ getContext().getString(R.string.price_unit));
            coin_btn_3.setText(responseGetScorePackage.getExtra().get(2).getPrice() +" "+ getContext().getString(R.string.price_unit));

//////////////////// discount 1 ////////////////////////////////////////
            if (responseGetScorePackage.getExtra().get(0).getDiscountPercent() > 0) {
                int dis = (int) ((responseGetScorePackage.getExtra().get(0).getPrice() / 100.0f) * responseGetScorePackage.getExtra().get(0).getDiscountPercent());
                int lastPrice = responseGetScorePackage.getExtra().get(0).getPrice() - dis;

                coin_discount_1.setText("تخفیف\n" + (Utils.LongToCurrency(dis)) + " " + getContext().getString(R.string.price_unit));
                coin_btn_1.setText(Utils.LongToCurrency(lastPrice) + " " + getContext().getString(R.string.price_unit));
                relDiscount1.setVisibility(View.VISIBLE);


            }

//////////////////// discount 2 ////////////////////////////////////////

            if (responseGetScorePackage.getExtra().get(1).getDiscountPercent() > 0) {
                int dis =(int) ((responseGetScorePackage.getExtra().get(1).getPrice() / 100.0f) * responseGetScorePackage.getExtra().get(1).getDiscountPercent());
                int lastPrice=responseGetScorePackage.getExtra().get(1).getPrice() -  dis;


                coin_discount_2.setText("تخفیف\n"+(Utils.LongToCurrency(dis)) +" "+ getContext().getString(R.string.price_unit));
                coin_btn_2.setText(Utils.LongToCurrency(lastPrice) +" "+getContext().getString(R.string.price_unit));
                relDiscount2.setVisibility(View.VISIBLE);



            }

//////////////////// discount 3 ////////////////////////////////////////
            if (responseGetScorePackage.getExtra().get(2).getDiscountPercent() > 0) {
                int dis = (int)((responseGetScorePackage.getExtra().get(2).getPrice() / 100.0f) * responseGetScorePackage.getExtra().get(2).getDiscountPercent());
                int lastPrice=responseGetScorePackage.getExtra().get(2).getPrice() -  dis;

                coin_discount_3.setText("تخفیف\n"+(Utils.LongToCurrency(dis)) +" "+ getContext().getString(R.string.price_unit));
                coin_btn_3.setText(Utils.LongToCurrency(lastPrice) +" "+getContext().getString(R.string.price_unit));
                relDiscount3.setVisibility(View.VISIBLE);



            }


//===================== images  ========================================================

            if (responseGetScorePackage.getExtra().get(0).getImageUrl() != null && !responseGetScorePackage.getExtra().get(0).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(0).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_1);
            }
            if (responseGetScorePackage.getExtra().get(1).getImageUrl() != null && !responseGetScorePackage.getExtra().get(1).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(1).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_2);
            }
            if (responseGetScorePackage.getExtra().get(2).getImageUrl() != null && !responseGetScorePackage.getExtra().get(2).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(2).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_3);
            }

            //===================== btn  ================================================

            coin_btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pScorePackage.doBuyScorePackage(0);

                }
            });
            coin_btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pScorePackage.doBuyScorePackage(1);

                }
            });

            coin_btn_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pScorePackage.doBuyScorePackage(2);
                }
            });


        }


        relContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void startGetScorePackage() {
        progressBar.setVisibility(View.VISIBLE);
        btnReTry.setVisibility(View.GONE);
        textError.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessGetScorePackage(ResponseGetScorePackage responseGetScorePackage) {
        setInfo(responseGetScorePackage);
    }

    @Override
    public void onFailedGetScorePackage(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

        btnReTry.setVisibility(View.VISIBLE);
        textError.setVisibility(View.VISIBLE);

        textError.setText(errorMessage);
    }

    @Override
    public void startBuyScorePackage() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessBuyScorePackage(ResponseBuyScorePackage responseBuyScorePackage) {
        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

        if (showBtnDiscardBuy && scorePackageDiscardBtnListener != null) {
            scorePackageDiscardBtnListener.onSuccessBuyScorePackage(responseBuyScorePackage.getExtra());
            cancel();

        }

    }

    @Override
    public void onFailedBuyScorePackage(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        if (showBtnDiscardBuy && scorePackageDiscardBtnListener != null) {
            /*scorePackageDiscardBtnListener.onFailedBuyScorePackage();
            cancel();*/

        }
    }

    public interface ScorePackageDiscardBtnListener {
        void btnDiscardBuySelect(String mode);

        void onSuccessBuyScorePackage(int totalCoin);

        void onFailedBuyScorePackage();
    }
}
