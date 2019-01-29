package psb.com.kidpaint.user.register.verifyCode;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import psb.com.kidpaint.R;
import psb.com.kidpaint.webApi.register.RegisterRequest.RegisterRequest;
import psb.com.kidpaint.webApi.register.RegisterRequest.iRegisterRequest;

public class FragmentVerifyCode extends Fragment implements iVVerifyCode {

    private OnFragmentInteractionListener mListener;

    private View view;

    private PVerifyCode pVerifyCode;

    private static final String ARG_ID = "ARG_ID";
    private String phoneNumber;
    private EditText textMessageCode;
    private ProgressBar progress;
    private TextView textError;
    private Button send;

    private TextView textPhoneNumber;
    private Button iconBack;

    private TextView textResendCode;

    private Handler handler;
    private Timer timer;
    private timerResend mTimerResend;

    private final int MAX_TIMER_VALUE = 30;
    private int timerCounter = MAX_TIMER_VALUE;
    private boolean onStartVerify=false;

    public FragmentVerifyCode() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString(ARG_ID);
        }
    }

    public static FragmentVerifyCode newInstance(String verifyCode) {
        FragmentVerifyCode fragmentVerifyCode = new FragmentVerifyCode();
        Bundle args = new Bundle();
        args.putString(ARG_ID, verifyCode);
        fragmentVerifyCode.setArguments(args);
        return fragmentVerifyCode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        handler = new Handler();

        pVerifyCode = new PVerifyCode(this);
        view = inflater.inflate(R.layout.fragment_send_verify_code, container, false);
        setView();
        return view;
    }

    private void setView(){
        progress = view.findViewById(R.id.progressBar7);
        textError = view.findViewById(R.id.text_error);
        send = view.findViewById(R.id.frg_sendPhoneNumber_btn);
        textMessageCode = view.findViewById(R.id.text_message_code);
        textPhoneNumber = view.findViewById(R.id.text_phone_number);
        iconBack = view.findViewById(R.id.icon_back);
        textResendCode = view.findViewById(R.id.text_resend_code);

        textPhoneNumber.setText(phoneNumber);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                mListener.onBackPressed();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textMessageCode.getText().toString().length() > 3 ){
                    pVerifyCode.VerifyCode(phoneNumber, textMessageCode.getText().toString());

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    textMessageCode.setError("کد صحیح وارد کنید");
                }
            }
        });

        textMessageCode.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE);
        textMessageCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (textMessageCode.getText().toString().length() > 3 ){
                        pVerifyCode.VerifyCode(phoneNumber, textMessageCode.getText().toString());
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    } else {
                        textMessageCode.setError("کد صحیح وارد کنید");
                    }
                    return true;
                }
                return false;
            }
        });


        timer = new Timer();
        mTimerResend = new timerResend();
        timer.schedule(mTimerResend, 0);


        textResendCode.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                RegisterRequest registerRequest = new RegisterRequest(new iRegisterRequest.iResult() {
                    @Override
                    public void onSuccessRegisterRequest() {
                        timerCounter=MAX_TIMER_VALUE;
                        mTimerResend = new timerResend();
                        timer.schedule(mTimerResend, 0);
                    }

                    @Override
                    public void onFailedRegisterRequest(int ErrorId, String ErrorMessage) {
                    }
                });
                registerRequest.startSendPhoneNumber(phoneNumber);

            }
        });

    }

    public void setCodeToEditTextCode(String code){
        if (!onStartVerify) {
            textMessageCode.setText(code);
            pVerifyCode.VerifyCode(phoneNumber, textMessageCode.getText().toString());
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    public void onStartVerifyCode() {
        onStartVerify=true;
        progress.setVisibility(View.VISIBLE);
        textError.setVisibility(View.GONE);
        textMessageCode.setEnabled(false);
        send.setEnabled(false);
        if (mListener != null) {
        mListener.onStartVerifyCode(textMessageCode.getText().toString());
        }
    }

    @Override
    public void VerifyCodeSuccess() {
        onStartVerify=false;

        progress.setVisibility(View.GONE);
        textMessageCode.setEnabled(true);
        send.setEnabled(true);
        if (mListener != null) {
        mListener.VerifyCodeSuccess();
        }
    }

    @Override
    public void VerifyCodeFailed(String msg) {
        onStartVerify=false;

        progress.setVisibility(View.GONE);
        textMessageCode.setEnabled(true);
        send.setEnabled(true);
        textError.setText(msg);
        textError.setVisibility(View.VISIBLE);
    }


    class timerResend extends TimerTask {
        @Override
        public void run() {
            if (timerCounter < 2) {
                setResendEnable();
                timerCounter = MAX_TIMER_VALUE;
            } else {
                timerCounter -= 1;
                setTimerCount();
                mTimerResend = new timerResend();
                timer.schedule(mTimerResend, 1000);
            }
        }
    }

    private void setResendEnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    textResendCode.setText(R.string.resend_key);
                    textResendCode.setEnabled(true);
                    textResendCode.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }catch (Exception ex){

                }

            }
        });
    }

    private void setTimerCount() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    textResendCode.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    textResendCode.setEnabled(false);
                    textResendCode.setText(getString(R.string.resend_key) + " " + timerCounter);
                }catch (Exception ex){

                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onStartVerifyCode(String verifyCode);
        void VerifyCodeSuccess();
        void onBackPressed();
    }
}
