package psb.com.kidpaint.offerPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;


public class Fragment_OfferPackage extends Fragment implements IVOfferPackage,OnPaymentResult.OnPaymentFinished {
    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener mListener;
    private View view;


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
    private ProgressDialog pDialog;
    public Fragment_OfferPackage() {
        // Required empty public constructor
    }

    public static Fragment_OfferPackage newInstance(ResponseGetOfferPackage mResponseGetOfferPackagel) {
        Fragment_OfferPackage fragment = new Fragment_OfferPackage();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, mResponseGetOfferPackagel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetOfferPackagel = (ResponseGetOfferPackage) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_offer_package, container, false);
        init();
        setInfo(mResponseGetOfferPackagel);

        return view;
    }
    private void init() {


        view.findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                if (mListener!=null) {
                    mListener.onBackPressed();
                }

            }
        });
        progressBar = view.findViewById(R.id.progressBar);
        message = view.findViewById(R.id.message);
        message.setVisibility(View.GONE);

        relContent = view.findViewById(R.id.rel_content);


        coin_coin_1 = view.findViewById(R.id.coin_coin_1);


        coin_discount_1 = view.findViewById(R.id.coin_discount_1);
        relDiscount = view.findViewById(R.id.rel_discount_1);


        coin_image_1 = view.findViewById(R.id.coin_image_1);


        coin_btn_1 = view.findViewById(R.id.btn_buy_1);


        relContent.setVisibility(View.GONE);

        pOfferPackage = new POfferPackage(this);


        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("در حال بررسی اطلاعات ...");
        pDialog.setCancelable(false);

        paymentHelper = new PaymentHelper();
        paymentHelper.setOnPaymentFinished(this);
        paymentHelper.setOnSetupFinished(new OnPaymentResult.OnSetupFinished() {
            @Override
            public void onSuccess() {
                Log.d("TAG", "onSuccess setOnSetupFinished frg: ");
                pDialog.cancel();
            }

            @Override
            public void onFailed(String message) {
                Log.d("TAG", "onFailed setOnSetupFinished frg: ");
                pDialog.cancel();

                showMessageDialog(getString(R.string.problem), getString(R.string.msg_error_payment), new CDialog.OnCLickListener() {
                    @Override
                    public void onPosetiveClicked() {
                        if (mListener!=null) {
                            mListener.onBackPressed();
                        }

                    }

                    @Override
                    public void onNegativeClicked() {
                        if (mListener!=null) {
                            mListener.onBackPressed();
                        }
                    }
                }).show();

            }
        });

        pDialog.show();
        paymentHelper.init(getContext());

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
                    paymentHelper.buyProduct(getActivity(),321,responseGetScorePackage.getExtra().get(0).getSku());
                }
            }
        });


        relContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
    public void setDialogMessage(String dialogMessage) {


        if ("".equals(dialogMessage)) {
            message.setVisibility(View.INVISIBLE);
        } else {
            message.setText(dialogMessage);
            message.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void startBuyOfferPackage() {
      //  progressBar.setVisibility(View.VISIBLE);
        pDialog.show();
    }

    @Override
    public void onSuccessBuyOfferPackage(final ResponseBuyOfferPackage responseBuyOfferPackage) {
        pDialog.cancel();

        showMessageDialog("موفق", "با سپاس از شما\n بسته مورد نظر با موفق خریداری شد و مجموع سکه های شما به  " + responseBuyOfferPackage.getExtra() + "  سکه ارتقا یافت.", new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                if (mListener != null) {
                    mListener.onSuccessBuyOfferPackage(responseBuyOfferPackage.getExtra());
                    mListener.onBackPressed();
                }
            }

            @Override
            public void onNegativeClicked() {
                if (mListener != null) {
                    mListener.onSuccessBuyOfferPackage(responseBuyOfferPackage.getExtra());
                    mListener.onBackPressed();
                }
            }
        }).setSoundId(R.raw.cash).show();



    }

    @Override
    public void onFailedBuyOfferPackage(int errorCode, String errorMessage) {
        pDialog.cancel();
        showMessageDialog(getString(R.string.problem),errorMessage,null).show();


    }
  @Override
    public void onSuccessPayment(String sku) {
      Log.d("TAG", "onSuccessPayment frag: ");
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

    @Override

    public void onFailedPayment(String message) {
        Log.d("TAG", "onSuccessPayment frag: ");
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (paymentHelper != null && paymentHelper.checkActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    public interface OnFragmentInteractionListener {

        void onSuccessBuyOfferPackage(int totalCoin);
        void onBackPressed();
    }

    private MessageDialog showMessageDialog(
            String title,
            String message,
            @Nullable CDialog.OnCLickListener listener
    ){

        MessageDialog dialog=new MessageDialog(getContext());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setAcceptButtonMessage(getString(R.string.test_ok));
        if(listener!=null)dialog.setOnCLickListener(listener);

        return dialog;

    }
}
