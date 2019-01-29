package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.helper.PaymentHelper;
import com.psb.versioncontrol.CheckVersion;

import androidx.fragment.app.Fragment;
import ir.dorsa.totalpayment.payment.Payment;
import psb.com.kidpaint.App;
import psb.com.kidpaint.BuildConfig;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;

public class SplashFragment extends Fragment implements IV_Splash {

    private OnFragmentInteractionListener mListener;

    private View view;

    private ProgressBar progressBar;


    private P_Splash pSplash;

    private CheckVersion checkVersion;

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
        pSplash.setFirstUserScore(2500);// give user 2500 score
        checkVersion();
        //setContent();
        return view;
    }

    private void checkVersion() {
        String BASE_URL = "http://getboomboom.ir/api/Version/Get";
        checkVersion = new CheckVersion(getActivity(), BASE_URL, "BoomBoom", BuildConfig.VERSION_CODE);
        checkVersion.setDebug(true);
        checkVersion.setVas(PaymentHelper.isAgrigator());
        checkVersion.getVersion(new CheckVersion.onTaskFinished() {
            @Override
            public void onFinished(boolean exit) {
                if (exit) {
                    if (mListener != null) {
                        mListener.exit();
                    }
                } else {
                    setContent();
                }
            }
        });
    }

    private void setContent() {
        if (pSplash.userIsRegistered()) {
            if (PaymentHelper.isAgrigator()) {
                Payment payment = new Payment(getContext());
                final boolean isRegisteredBefor=payment.isUserPremium();
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
                                    if(isRegisteredBefor){
                                        SharePrefrenceHelper.setIntroIsShowBefore(false);
                                    }
                                    UserProfile userProfile = new UserProfile(getContext());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkVersion.checkActivityResult(requestCode, resultCode);
    }

    @Override
    public void startGetStickers() {
        if (mListener != null) {
            mListener.startGetStickers();
        }
    }

    @Override
    public void getStickersSuccess() {
        if (pSplash != null) {
        pSplash.getMessage();
        }
    }

    @Override
    public void getStickersFailed(String msg) {
        if (pSplash != null) {
        pSplash.getMessage();
        }
    }

    @Override
    public void startGetRank() {
        if (mListener != null) {
            mListener.getRankStarted();
        }
    }

    @Override
    public void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip) {
        if (mListener != null) {
            mListener.getRankSuccess(responseGetLeaderShip);
        }
        if (pSplash != null) {
            pSplash.getStickers();
        }

    }

    @Override
    public void getRankFailed(String msg) {
        if (mListener != null) {
            mListener.getRankFailed();
        }
        if (pSplash != null) {
            pSplash.getStickers();
        }

    }

    @Override
    public void onSuccessUpdateFcmToken() {
        if (pSplash != null) {
            pSplash.getOfferPackage();
        }

    }

    @Override
    public void onFailedUpdateFcmToken(int errorCode, String errorMessage) {
        if (pSplash != null) {
            pSplash.getOfferPackage();
        }

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
            if (pSplash != null) {
                pSplash.getRank();
            }
        } else {
            if (pSplash != null) {
                pSplash.getDailyPrize();
            }
        }
    }

    @Override
    public void onFailedGetOfferPackage(int errorCode, String errorMessage) {
        if (mListener != null) {
            mListener.setResponseOfferPackage(null);
        }
        if (pSplash != null) {
            pSplash.getDailyPrize();
        }
    }

    @Override
    public void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize) {
        if (mListener != null) {
            mListener.setResponseDailyPrize(responseGetDailyPrize);
        }
        if (pSplash != null) {
            pSplash.getRank();
        }
    }

    @Override
    public void onFailedGetDailyPrize(int errorCode, String errorMessage) {
        if (mListener != null) {
            mListener.setResponseDailyPrize(null);
        }
        if (pSplash != null) {
            pSplash.getRank();
        }
    }

    @Override
    public void onSuccessGetMessage() {
        if (pSplash != null && pSplash.userIsRegistered()) {
            pSplash.getProfile();
        } else {
            if (mListener != null) {
                mListener.splashSuccess();

            }
        }
    }

    @Override
    public void onFailedGetMessage(int errorCode, String errorMessage) {
        if (pSplash != null && pSplash.userIsRegistered()) {
            pSplash.getProfile();
        } else {
            if (mListener != null) {
                mListener.splashSuccess();

            }
        }
    }

    @Override
    public void onSuccessGetProfile() {
        if (pSplash != null) {
            if (pSplash.getLocalPaintsCount() > 0) {
                pSplash.onSavePaintsInServer();
            } else {
                pSplash.getMyPaints();

            }
        }

    }

    @Override
    public void onFailedGetProfile(int errorCode, String errorMessage) {
        if (pSplash != null) {
            if (pSplash.getLocalPaintsCount() > 0) {
                pSplash.onSavePaintsInServer();
            } else {
                pSplash.getMyPaints();

            }
        }
    }

    @Override
    public void onSuccessSavePaintsInServer() {
        if (pSplash != null) {
        pSplash.getMyPaints();
        }
    }

    @Override
    public void onFailedSavePaintsInServer(int errorCode, String errorMessage) {
        if (pSplash != null) {
        pSplash.getMyPaints();
        }
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

        void exit();
    }
}
