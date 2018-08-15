package psb.com.kidpaint.offerPackage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.score.IVScorePackage;
import psb.com.kidpaint.score.PScorePackage;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;

public class DialogOfferPackage extends Dialog implements IVOfferPackage {
    private TextView coin_title_1, message;
    private TextView coin_coin_1;
    private TextView coin_price_1;
    private TextView coin_discount_1;
    private ImageView coin_image_1;
    private Button coin_btn_1, btn_discard_buy;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private POfferPackage pOfferPackage;
    private ResponseGetOfferPackage mResponseGetOfferPackagel;


    private String dialogMessage = "";
    private boolean showBtnDiscardBuy = false;

    private offerPackageDiscardBtnListener offerPackageDiscardBtnListener;


    private boolean defaultSound = SharePrefrenceHelper.getSoundEffect();
    private boolean defaultMusic = SharePrefrenceHelper.getMusic();

    public DialogOfferPackage(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogOfferPackage(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogOfferPackage(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;

        if ("".equals(dialogMessage)) {
            message.setVisibility(View.GONE);
        } else {
            message.setText(dialogMessage);
            message.setVisibility(View.VISIBLE);
        }
    }

    public void setOfferResponse(ResponseGetOfferPackage response){
        this.mResponseGetOfferPackagel=response;

        setInfo(mResponseGetOfferPackagel);

    }

    public void setShowBtnDiscardBuy(boolean showBtnDiscardBuy) {
        this.showBtnDiscardBuy = showBtnDiscardBuy;
        btn_discard_buy.setVisibility(showBtnDiscardBuy ? View.VISIBLE : View.GONE);

    }

    public void setScorePackageDiscardBtnListener(offerPackageDiscardBtnListener offerPackageDiscardBtnListener) {
        this.offerPackageDiscardBtnListener = offerPackageDiscardBtnListener;


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


        setContentView(R.layout.dialog_offer_package);
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
                if (offerPackageDiscardBtnListener != null) {
                    offerPackageDiscardBtnListener.btnDiscardBuySelect();
                }
                cancel();
            }
        });


        progressBar = findViewById(R.id.progressBar);
        message = findViewById(R.id.message);
        message.setVisibility(View.GONE);

        linearLayout = findViewById(R.id.rel_content);

        coin_title_1 = findViewById(R.id.coin_title_1);


        coin_coin_1 = findViewById(R.id.coin_coin_1);


        coin_price_1 = findViewById(R.id.coin_price_1);


        coin_discount_1 = findViewById(R.id.coin_discount_1);


        coin_image_1 = findViewById(R.id.coin_image_1);


        coin_btn_1 = findViewById(R.id.btn_buy_1);




        linearLayout.setVisibility(View.GONE);

        pOfferPackage = new POfferPackage(this);
    }

    void setInfo(final ResponseGetOfferPackage responseGetScorePackage) {


            coin_title_1.setText(responseGetScorePackage.getExtra().get(0).getTitle());


            coin_coin_1.setText(responseGetScorePackage.getExtra().get(0).getScore() + " سکه");


            coin_price_1.setText(responseGetScorePackage.getExtra().get(0).getPrice() + "");


//////////////////// discount 1 ////////////////////////////////////////

                coin_price_1.setText(responseGetScorePackage.getExtra().get(0).getPrice() + "");
                coin_discount_1.setVisibility(View.GONE);





//===================== images  ========================================================
//======================================================================================
            if (responseGetScorePackage.getExtra().get(0).getImageUrl() != null && !responseGetScorePackage.getExtra().get(0).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(0).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_1);
            }


            //===================== btn  ========================================================
            //=================================================================================
            coin_btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pOfferPackage.doBuyOfferPackage(responseGetScorePackage.getExtra().get(0).getId());

                }
            });





        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }




    @Override
    public void startBuyOfferPackage() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessBuyOfferPackage(ResponseBuyOfferPackage responseBuyOfferPackage) {
        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

        if (showBtnDiscardBuy && offerPackageDiscardBtnListener != null) {
            offerPackageDiscardBtnListener.onSuccessBuyOfferPackage(responseBuyOfferPackage.getExtra());
            cancel();

        }

    }

    @Override
    public void onFailedBuyOfferPackage(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        if (showBtnDiscardBuy && offerPackageDiscardBtnListener != null) {
            /*scorePackageDiscardBtnListener.onFailedBuyScorePackage();
            cancel();*/

        }
    }

    public interface offerPackageDiscardBtnListener {
        void btnDiscardBuySelect();

        void onSuccessBuyOfferPackage(int totalCoin);

        void onFailedBuyScorePackage();
    }
}
