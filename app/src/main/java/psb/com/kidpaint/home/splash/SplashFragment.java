package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import psb.com.kidpaint.R;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
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
            pSplash.updateFcmToken();
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

        if (responseGetOfferPackage.getExtra().size()>0) {
            if (mListener != null) {
                mListener.setResponseDailyPrize(null);
            }
            pSplash.getRank();
        }else{

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
        pSplash.getProfile();
    }

    @Override
    public void onFailedGetMessage(int errorCode, String errorMessage) {
        pSplash.getProfile();
    }

    @Override
    public void onSuccessGetProfile() {
        mListener.splashSuccess();

    }

    @Override
    public void onFailedGetProfile(int errorCode, String errorMessage) {
        mListener.splashSuccess();

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
    }
}
