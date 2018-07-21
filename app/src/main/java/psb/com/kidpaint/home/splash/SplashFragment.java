package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import psb.com.kidpaint.R;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

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

    private void setContent(){
        pSplash.getPirze();
    }

    public void refreshPrizeAndRank(){
        //pSplash.getRank();
        pSplash.getPirze();
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
        mListener.splashSuccess();
    }

    @Override
    public void getStickersFailed(String msg) {
        mListener.splashFailed(msg);
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
    public void getPirzeSuccess(ResponsePrize responsePrize) {
        mListener.getPrizeSuccess(responsePrize);
        pSplash.getRank();
    }

    @Override
    public void getPrizeFailed(String msg) {
        mListener.getPrizeFailed();
        pSplash.getRank();
    }

    public interface OnFragmentInteractionListener {
        void startGetStickers();
        void splashSuccess();
        void splashFailed(String msg);

        void getRankStarted();
        void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
        void getRankFailed();

        void getPrizeSuccess(ResponsePrize responsePrize);
        void getPrizeFailed();
    }
}
