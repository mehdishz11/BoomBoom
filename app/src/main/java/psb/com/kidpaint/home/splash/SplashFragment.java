package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import psb.com.kidpaint.R;

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
        pSplash.getStickers();
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

    public interface OnFragmentInteractionListener {
        void startGetStickers();
        void splashSuccess();
        void splashFailed(String msg);
    }
}
