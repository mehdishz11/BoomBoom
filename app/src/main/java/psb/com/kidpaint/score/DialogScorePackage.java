package psb.com.kidpaint.score;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;
import com.squareup.picasso.Picasso;

import psb.com.cview.IconFont;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public class DialogScorePackage extends Dialog implements IVScorePackage{
  private TextView coin_title_1,coin_title_2,coin_title_3,textError;
  private TextView coin_coin_1,coin_coin_2,coin_coin_3;
  private TextView coin_price_1,coin_price_2,coin_price_3;
  private TextView coin_discount_1,coin_discount_2,coin_discount_3;
  private ImageView coin_image_1,coin_image_2,coin_image_3;
  private Button coin_btn_1,coin_btn_2,coin_btn_3,btnReTry;
  private ProgressBar progressBar;
  private LinearLayout linearLayout;
  private PScorePackage pScorePackage;

    private Button save;


    private boolean defaultSound =SharePrefrenceHelper.getSoundEffect();
    private boolean defaultMusic =SharePrefrenceHelper.getMusic();

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



    private void init(){
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


        setContentView(R.layout.dialog_score_package);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);

                cancel();
            }
        });


          progressBar=findViewById(R.id.progressBar);
          linearLayout=findViewById(R.id.rel_content);
          textError=findViewById(R.id.TextError);
          btnReTry=findViewById(R.id.btn_retry);

          coin_title_1=findViewById(R.id.coin_title_1);
          coin_title_2=findViewById(R.id.coin_title_2);
          coin_title_3=findViewById(R.id.coin_title_3);

          coin_coin_1=findViewById(R.id.coin_coin_1);
          coin_coin_2=findViewById(R.id.coin_coin_2);
          coin_coin_3=findViewById(R.id.coin_coin_3);

          coin_price_1=findViewById(R.id.coin_price_1);
          coin_price_2=findViewById(R.id.coin_price_2);
          coin_price_3=findViewById(R.id.coin_price_3);

          coin_discount_1=findViewById(R.id.coin_discount_1);
          coin_discount_2=findViewById(R.id.coin_discount_2);
          coin_discount_3=findViewById(R.id.coin_discount_3);

          coin_image_1=findViewById(R.id.coin_image_1);
          coin_image_2=findViewById(R.id.coin_image_2);
          coin_image_3=findViewById(R.id.coin_image_3);

          coin_btn_1=findViewById(R.id.btn_buy_1);
          coin_btn_2=findViewById(R.id.btn_buy_2);
          coin_btn_3=findViewById(R.id.btn_buy_3);



  btnReTry.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          pScorePackage.getScorePackage();

      }
  });
        linearLayout.setVisibility(View.GONE);

        pScorePackage=new PScorePackage(this);
        pScorePackage.getScorePackage();

    }

    void setInfo(ResponseGetScorePackage responseGetScorePackage){

        if (responseGetScorePackage.getExtra().size()>=3) {
            coin_title_1.setText(responseGetScorePackage.getExtra().get(0).getTitle());
            coin_title_2.setText(responseGetScorePackage.getExtra().get(1).getTitle());
            coin_title_3.setText(responseGetScorePackage.getExtra().get(2).getTitle());

            coin_coin_1.setText(responseGetScorePackage.getExtra().get(0).getScore()+" سکه");
            coin_coin_2.setText(responseGetScorePackage.getExtra().get(1).getScore()+" سکه");
            coin_coin_3.setText(responseGetScorePackage.getExtra().get(2).getScore()+" سکه");

            coin_price_1.setText(responseGetScorePackage.getExtra().get(0).getPrice()+"");
            coin_price_2.setText(responseGetScorePackage.getExtra().get(1).getPrice()+"");
            coin_price_3.setText(responseGetScorePackage.getExtra().get(2).getPrice()+"");

//////////////////// discount 1 ////////////////////////////////////////
            if (responseGetScorePackage.getExtra().get(0).getDiscountPercent()>0) {
                float dis=((responseGetScorePackage.getExtra().get(0).getPrice()/100.0f)*responseGetScorePackage.getExtra().get(0).getDiscountPercent());
                coin_discount_1.setText(responseGetScorePackage.getExtra().get(0).getPrice() - (int)dis+"");
                coin_discount_1.setVisibility(View.VISIBLE);
                coin_price_1.setPaintFlags( coin_price_1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            }else{
                coin_price_1.setText(responseGetScorePackage.getExtra().get(0).getPrice()+"");
                coin_discount_1.setVisibility(View.GONE);
            }

//////////////////// discount 2 ////////////////////////////////////////

            if (responseGetScorePackage.getExtra().get(1).getDiscountPercent()>0) {
                float dis=((responseGetScorePackage.getExtra().get(1).getPrice()/100.0f)*responseGetScorePackage.getExtra().get(1).getDiscountPercent());
                coin_discount_2.setText(responseGetScorePackage.getExtra().get(1).getPrice() - (int)dis+"");
                coin_discount_2.setVisibility(View.VISIBLE);
                coin_price_2.setPaintFlags( coin_price_2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            }else{
                coin_price_2.setText(responseGetScorePackage.getExtra().get(1).getPrice()+"");
                coin_discount_2.setVisibility(View.GONE);
            }

//////////////////// discount 3 ////////////////////////////////////////
            if (responseGetScorePackage.getExtra().get(2).getDiscountPercent()>0) {
                float dis=((responseGetScorePackage.getExtra().get(2).getPrice()/100.0f)*responseGetScorePackage.getExtra().get(2).getDiscountPercent());
                coin_discount_3.setText(responseGetScorePackage.getExtra().get(2).getPrice() - (int)dis+"");
                coin_discount_3.setVisibility(View.VISIBLE);
                coin_price_2.setPaintFlags( coin_price_2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            }else{
                coin_price_3.setText(responseGetScorePackage.getExtra().get(2).getPrice()+"");
                coin_discount_3.setVisibility(View.GONE);
            }


//===================== images  ========================================================
//======================================================================================
                        if (responseGetScorePackage.getExtra().get(0).getImageUrl()!=null && !responseGetScorePackage.getExtra().get(0).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(0).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_1);
            }
            if (responseGetScorePackage.getExtra().get(1).getImageUrl()!=null && !responseGetScorePackage.getExtra().get(1).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(1).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_2);
            }
            if (responseGetScorePackage.getExtra().get(2).getImageUrl()!=null && !responseGetScorePackage.getExtra().get(2).getImageUrl().isEmpty()) {
                Picasso
                        .get()
                        .load(responseGetScorePackage.getExtra().get(2).getImageUrl())
                        .resize(Value.dp(100), 0)
                        .onlyScaleDown()
                        .into(coin_image_3);
            }

 //===================== btn  ========================================================
 //=================================================================================
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




        linearLayout.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void onFailedBuyScorePackage(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }
}
