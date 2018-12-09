package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.helper.PaymentHelper;

import ir.dorsa.totalpayment.payment.Payment;
import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;

public class SplashFragment extends Fragment implements IV_Splash {


    private OnFragmentInteractionListener mListener;

    private View view;

    private ProgressBar progressBar;


    private P_Splash pSplash;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        pSplash = new P_Splash(this);
        setContent();
        return view;
    }

    private void setContent() {
        if (pSplash.userIsRegistered()) {
            if (PaymentHelper.isAgrigator()) {
                Payment payment = new Payment(getContext());
                payment.checkStatus(
                        App.appCode,
                        App.productCode,
                        App.irancellSku,
                        new Payment.onCheckFinished() {
                            @Override
                            public void result(boolean status, int errorCode, String errorMessage) {
                                if (status) {//check successfully and user is active
                                    pSplash.updateFcmToken();
                                } else if (!status && errorCode == Payment.ERROR_CODE_INTERNET_CONNECTION) {//internet connection problem
                                    pSplash.getRank();
                                }
                                   /* else if(!status && errorCode==Payment.ERROR_CODE_USER_HAS_NO_CHARGE){//user has no charge

                                    }*/
                                else {//user not enable anymore
                                    UserProfile userProfile=new UserProfile(getContext());
                                    userProfile.REMOVE_KEY_USER_INFO();
                                    pSplash.getRank();
                                }
                            }
                        }
                );


            } else {
                pSplash.updateFcmToken();
            }

        } else {
            pSplash.getRank();
        }
    }

    public void refreshPrizeAndRank() {
        if (pSplash.userIsRegistered()) {
            pSplash.updateFcmToken();
        } else {
            pSplash.getRank();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void startGetStickers() {
        mListener.startGetStickers();
    }

    @Override
    public void getStickersSuccess() {
        pSplash.getMessage();
    }

    @Override
    public void getStickersFailed(String msg) {
        pSplash.getMessage();
    }

    @Override
    public void startGetRank() {
        mListener.getRankStarted();
    }

    @Override
    public void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip) {
        mListener.getRankSuccess(responseGetLeaderShip);
        pSplash.getStickers();

    }

    @Override
    public void getRankFailed(String msg) {
        mListener.getRankFailed();
        pSplash.getStickers();

    }

    @Override
    public void onSuccessUpdateFcmToken() {

        pSplash.getOfferPackage();

    }

    @Override
    public void onFailedUpdateFcmToken(int errorCode, String errorMessage) {
        pSplash.getOfferPackage();

    }

    @Override
    public void onSuccessGetOfferPackage(ResponseGetOfferPackage responseGetOfferPackage) {
        if (mListener != null) {
            mListener.setResponseOfferPackage(responseGetOfferPackage);
        }

        if (responseGetOfferPackage.getExtra().size() > 0) {
            if (mListener != null) {
                mListener.setResponseDailyPrize(null);
            }
            pSplash.getRank();
        } else {

            pSplash.getDailyPrize();
        }
    }

    @Override
    public void onFailedGetOfferPackage(int errorCode, String errorMessage) {
        if (mListener != null) {
            mListener.setResponseOfferPackage(null);
        }
        pSplash.getDailyPrize();
    }

    @Override
    public void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize) {
        if (mListener != null) {
            mListener.setResponseDailyPrize(responseGetDailyPrize);
        }
        pSplash.getRank();
    }

    @Override
    public void onFailedGetDailyPrize(int errorCode, String errorMessage) {
        if (mListener != null) {
            mListener.setResponseDailyPrize(null);
        }
        pSplash.getRank();
    }

    @Override
    public void onSuccessGetMessage() {
        if (pSplash.userIsRegistered()) {
            pSplash.getProfile();
        } else {
            if (mListener != null) {
                mListener.splashSuccess();

            }
        }
    }

    @Override
    public void onFailedGetMessage(int errorCode, String errorMessage) {
        if (pSplash.userIsRegistered()) {
            pSplash.getProfile();
        } else {
            if (mListener != null) {
                mListener.splashSuccess();

            }
        }
    }

    @Override
    public void onSuccessGetProfile() {

        if (pSplash.getLocalPaintsCount() > 0) {
            pSplash.onSavePaintsInServer();
        } else {
            pSplash.getMyPaints();

        }

    }

    @Override
    public void onFailedGetProfile(int errorCode, String errorMessage) {

        if (pSplash.getLocalPaintsCount() > 0) {
            pSplash.onSavePaintsInServer();
        } else {
            pSplash.getMyPaints();

        }


    }

    @Override
    public void onSuccessSavePaintsInServer() {
        pSplash.getMyPaints();
    }

    @Override
    public void onFailedSavePaintsInServer(int errorCode, String errorMessage) {
        pSplash.getMyPaints();
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
        if (mListener != null) {
            mListener.setResponseMyPaints(responseGetMyPaints);
            mListener.splashSuccess();

        }


    }

    @Override
    public void onFailedGetGetMyPaints(int errorCode, String errorMessage) {

        if (mListener != null) {
            mListener.splashSuccess();
        }

    }


    public interface OnFragmentInteractionListener {
        void startGetStickers();

        void splashSuccess();

        void splashFailed(String msg);

        void getRankStarted();

        void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);

        void getRankFailed();

        void setResponseOfferPackage(ResponseGetOfferPackage responseOfferPackage);

        void setResponseDailyPrize(ResponseGetDailyPrize responseGetDailyPrize);

        void setResponseMyPaints(ResponseGetMyPaints responseMyPaints);
    }
}
