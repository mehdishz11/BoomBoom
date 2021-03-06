package psb.com.kidpaint.offerPackage;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.OnPaymentResult;
import com.helper.PaymentHelper;
import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;

public class DialogOfferPackage extends Dialog implements IVOfferPackage {
    private TextView message;
    private TextView coin_coin_1;
    private TextView coin_discount_1;
    private RelativeLayout relDiscount;
    private ImageView coin_image_1;
    private Button coin_btn_1;
    private ProgressBar progressBar;
    private RelativeLayout relContent;
    private POfferPackage pOfferPackage;
    private ResponseGetOfferPackage mResponseGetOfferPackagel;
    private PaymentHelper paymentHelper;
    private Activity activity;


    private String dialogMessage = "";
    private boolean showBtnDiscardBuy = false;

    private offerPackageDiscardBtnListener offerPackageDiscardBtnListener;
    private OnPaymentResult.OnPaymentFinished onPaymentFinished;

    private boolean defaultSound = SharePrefrenceHelper.getSoundEffect();
    private boolean defaultMusic = SharePrefrenceHelper.getMusic();

    public DialogOfferPackage(@NonNull Context context) {
        super(context);
        if (context instanceof Activity) {
            this.activity= (Activity) context;
        }
        init();
    }

    public DialogOfferPackage(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        if (context instanceof Activity) {
            this.activity= (Activity) context;
        }
        init();
    }

    protected DialogOfferPackage(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        if (context instanceof Activity) {
            this.activity= (Activity) context;
        }
        init();
    }

    public void setOnPaymentFinished(OnPaymentResult.OnPaymentFinished onPaymentFinished){
        this.onPaymentFinished=onPaymentFinished;

        if (paymentHelper!=null) {
            paymentHelper.setOnPaymentFinished(onPaymentFinished);
        }

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

    public void setOfferResponse(ResponseGetOfferPackage response) {
        this.mResponseGetOfferPackagel = response;

        setInfo(mResponseGetOfferPackagel);

    }

    public void setShowBtnDiscardBuy(boolean showBtnDiscardBuy) {
        this.showBtnDiscardBuy = showBtnDiscardBuy;


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


        progressBar = findViewById(R.id.progressBar);
        message = findViewById(R.id.message);
        message.setVisibility(View.GONE);

        relContent = findViewById(R.id.rel_content);


        coin_coin_1 = findViewById(R.id.coin_coin_1);


        coin_discount_1 = findViewById(R.id.coin_discount_1);
        relDiscount = findViewById(R.id.rel_discount_1);


        coin_image_1 = findViewById(R.id.coin_image_1);


        coin_btn_1 = findViewById(R.id.btn_buy_1);


        relContent.setVisibility(View.GONE);

        pOfferPackage = new POfferPackage(this);


        final ProgressDialog pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("در حال بررسی اطلاعات ...");

        paymentHelper = new PaymentHelper();

        paymentHelper.setOnSetupFinished(new OnPaymentResult.OnSetupFinished() {
            @Override
            public void onSuccess() {
                pDialog.cancel();
            }

            @Override
            public void onFailed(String message) {
                pDialog.cancel();

                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                cancel();

            }
        });

        pDialog.show();
        paymentHelper.init(activity);

    }

    void setInfo(final ResponseGetOfferPackage responseGetScorePackage) {


        setDialogMessage(responseGetScorePackage.getExtra().get(0).getTitle());

        coin_coin_1.setText(Utils.LongToCurrency(responseGetScorePackage.getExtra().get(0).getScore()) + " " + getContext().getString(R.string.coin));

        String btnText=(responseGetScorePackage.getExtra().get(0).getPrice()==0?"دریافت":Utils.LongToCurrency(responseGetScorePackage.getExtra().get(0).getPrice()) + " " + getContext().getString(R.string.price_unit));

        coin_btn_1.setText(btnText);


//////////////////// discount 1 ////////////////////////////////////////


        relDiscount.setVisibility(View.GONE);


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
                if (paymentHelper.isSetupFinished()) {
                    paymentHelper.buyProduct(activity,321,responseGetScorePackage.getExtra().get(0).getSku());
                }
            }
        });


        relContent.setVisibility(View.VISIBLE);
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

    public void onSuccessPayment(String sku) {
        if (pOfferPackage!=null) {
            if (mResponseGetOfferPackagel!=null&& mResponseGetOfferPackagel.getExtra().size()>0) {
                for (int i = 0; i <mResponseGetOfferPackagel.getExtra().size() ; i++) {
                    if (sku.equals(mResponseGetOfferPackagel.getExtra().get(i).getSku())) {
                        pOfferPackage.doBuyOfferPackage(mResponseGetOfferPackagel.getExtra().get(i).getId());
                        break;
                    }

                }
            }
        }
    }


    public void onFailedPayment(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    public interface offerPackageDiscardBtnListener {
        void btnDiscardBuySelect();

        void onSuccessBuyOfferPackage(int totalCoin);

        void onFailedBuyScorePackage();
        void startBuyOfferPackage(String sku);
    }
}
