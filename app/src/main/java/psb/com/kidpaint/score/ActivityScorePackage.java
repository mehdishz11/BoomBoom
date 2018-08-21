package psb.com.kidpaint.score;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public class ActivityScorePackage extends BaseActivity implements IVScorePackage, OnPaymentResult.OnPaymentFinished {
    private TextView coin_title_1, coin_title_2, coin_title_3, textError, message;
    private TextView coin_title_1_1, coin_title_2_1, coin_title_3_1;
    private TextView coin_coin_1, coin_coin_2, coin_coin_3;

    private TextView coin_discount_1, coin_discount_2, coin_discount_3;
    private ImageView coin_image_1, coin_image_2, coin_image_3;
    private Button coin_btn_1, coin_btn_2, coin_btn_3, btnReTry, btn_discard_buy;
    private ProgressBar progressBar;
    private RelativeLayout relContent;
    private PScorePackage pScorePackage;

    private RelativeLayout relDiscount1, relDiscount2, relDiscount3;

    private int buyPackagePosition=-1;


    private String dialogMessage = "";
    private String dialogMode = "";
    private boolean showBtnDiscardBuy = false;
    private PaymentHelper paymentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_package);



        paymentHelper = new PaymentHelper();
        paymentHelper.setOnPaymentFinished(this);

        Log.d(App.TAG, "onSuccess: 1234567890");

        final ProgressDialog pDialog=new ProgressDialog(ActivityScorePackage.this);
        pDialog.setMessage("در حال بررسی اطلاعات ...");
        paymentHelper.setOnSetupFinished(new OnPaymentResult.OnSetupFinished() {
            @Override
            public void onSuccess() {
                pDialog.cancel();

            }

            @Override
            public void onFailed(String message) {
                pDialog.cancel();
                Toast.makeText(ActivityScorePackage.this, "اشکال در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        pDialog.show();
        paymentHelper.init(this);

        dialogMessage = getIntent().getStringExtra("dialogMessage");
        dialogMode = getIntent().getStringExtra("dialogMode");
        showBtnDiscardBuy = getIntent().getBooleanExtra("showBtnDiscardBuy", false);

        init();


        if ("".equals(dialogMessage)) {
            message.setVisibility(View.INVISIBLE);
        } else {
            message.setText(dialogMessage);
            message.setVisibility(View.VISIBLE);
        }
        btn_discard_buy.setVisibility(showBtnDiscardBuy ? View.VISIBLE : View.GONE);


        createHelperWnd();
    }

    private void init() {
        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);

                finish();
            }
        });

        btn_discard_buy = findViewById(R.id.btn_discard_buy);
        btn_discard_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);

                Intent intent = new Intent();
                intent.putExtra("dialogMode", dialogMode);
                setResult(Activity.RESULT_OK, intent);
                finish();
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

        relDiscount1 = findViewById(R.id.rel_discount_1);
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

    void setInfo(final ResponseGetScorePackage responseGetScorePackage) {

        if (responseGetScorePackage.getExtra().size() >= 3) {
            coin_title_1.setText(responseGetScorePackage.getExtra().get(0).getTitle());
            coin_title_2.setText(responseGetScorePackage.getExtra().get(1).getTitle());
            coin_title_3.setText(responseGetScorePackage.getExtra().get(2).getTitle());

            coin_title_1_1.setText(responseGetScorePackage.getExtra().get(0).getTitle());
            coin_title_2_1.setText(responseGetScorePackage.getExtra().get(1).getTitle());
            coin_title_3_1.setText(responseGetScorePackage.getExtra().get(2).getTitle());

            coin_coin_1.setText(Utils.LongToCurrency(responseGetScorePackage.getExtra().get(0).getScore()) + " " + getContext().getString(R.string.coin));
            coin_coin_2.setText(Utils.LongToCurrency(responseGetScorePackage.getExtra().get(1).getScore()) + " " + getContext().getString(R.string.coin));
            coin_coin_3.setText(Utils.LongToCurrency(responseGetScorePackage.getExtra().get(2).getScore()) + " " + getContext().getString(R.string.coin));

            coin_btn_1.setText(responseGetScorePackage.getExtra().get(0).getPrice() + " " + getContext().getString(R.string.price_unit));
            coin_btn_2.setText(responseGetScorePackage.getExtra().get(1).getPrice() + " " + getContext().getString(R.string.price_unit));
            coin_btn_3.setText(responseGetScorePackage.getExtra().get(2).getPrice() + " " + getContext().getString(R.string.price_unit));

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
                int dis = (int) ((responseGetScorePackage.getExtra().get(1).getPrice() / 100.0f) * responseGetScorePackage.getExtra().get(1).getDiscountPercent());
                int lastPrice = responseGetScorePackage.getExtra().get(1).getPrice() - dis;


                coin_discount_2.setText("تخفیف\n" + (Utils.LongToCurrency(dis)) + " " + getContext().getString(R.string.price_unit));
                coin_btn_2.setText(Utils.LongToCurrency(lastPrice) + " " + getContext().getString(R.string.price_unit));
                relDiscount2.setVisibility(View.VISIBLE);


            }

//////////////////// discount 3 ////////////////////////////////////////
            if (responseGetScorePackage.getExtra().get(2).getDiscountPercent() > 0) {
                int dis = (int) ((responseGetScorePackage.getExtra().get(2).getPrice() / 100.0f) * responseGetScorePackage.getExtra().get(2).getDiscountPercent());
                int lastPrice = responseGetScorePackage.getExtra().get(2).getPrice() - dis;

                coin_discount_3.setText("تخفیف\n" + (Utils.LongToCurrency(dis)) + " " + getContext().getString(R.string.price_unit));
                coin_btn_3.setText(Utils.LongToCurrency(lastPrice) + " " + getContext().getString(R.string.price_unit));
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
                    buyPackagePosition=0;
                    Log.d("TAG", "onClick: "+paymentHelper.isSetupFinished());
                    if (paymentHelper.isSetupFinished()) {
                        paymentHelper.buyProduct(ActivityScorePackage.this,321,responseGetScorePackage.getExtra().get(0).getSku());
                    }

                }
            });
            coin_btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //pScorePackage.doBuyScorePackage(1);
                    buyPackagePosition=1;

                    if (paymentHelper.isSetupFinished()) {
                        paymentHelper.buyProduct(ActivityScorePackage.this,321,responseGetScorePackage.getExtra().get(1).getSku());
                    }
                }
            });

            coin_btn_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // pScorePackage.doBuyScorePackage(2);
                    buyPackagePosition=2;
                    if (paymentHelper.isSetupFinished()) {
                        paymentHelper.buyProduct(ActivityScorePackage.this,321,responseGetScorePackage.getExtra().get(2).getSku());
                    }
                }
            });


        }


        relContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (paymentHelper != null && paymentHelper.checkActivityResult(requestCode, resultCode, data)) {
            return;
        }


        super.onActivityResult(requestCode, resultCode, data);
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
        //  Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);


        Intent intent = new Intent();
        intent.putExtra("SuccessBuy", responseBuyScorePackage.getExtra());
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    @Override
    public void onFailedBuyScorePackage(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }


    public static void clearLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    @Override
    public void onSuccessPayment(String sku) {
        Log.d("TAG", "onSuccessPayment: ");
        pScorePackage.doBuyScorePackage(buyPackagePosition);

    }

    @Override
    public void onFailedPayment(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }
}
