package psb.com.kidpaint.user.register.sendPhoneNumber;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.Utils;


public class FragmentSendPhoneNumber extends Fragment implements iVSendPhoneNumber {

    private OnFragmentInteractionListener mListener;
    private int REQUEST_SMS_PERMMISION = 105;
    private View view;
    private EditText editTextPhoneNumber;
    private Button buttonSendPhoneNumber;
    private PSendPhoneNumber pSendPhoneNumber;
    private TextView back,textError;
    private ProgressBar progressBar;

    public FragmentSendPhoneNumber() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pSendPhoneNumber = new PSendPhoneNumber(this);
        view = inflater.inflate(R.layout.fragment_send_phone_number, container, false);
        setView();
        return view;
    }

    private void setView(){

        textError = view.findViewById(R.id.text_error);
        back = view.findViewById(R.id.icon_back);

        progressBar = view.findViewById(R.id.progressBar7);
        editTextPhoneNumber = view.findViewById(R.id.text_phone_number);
        buttonSendPhoneNumber = view.findViewById(R.id.frg_sendPhoneNumber_btn);



        buttonSendPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePhoneNumber()) {
                    return;
                }
                Log.d("ffff", "onClick: " + Utils.getSmsPermission(getActivity()));
                if (Utils.getSmsPermission(getActivity())) {
                    pSendPhoneNumber.sendPhoneNumber(editTextPhoneNumber.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextPhoneNumber.getWindowToken(), 0);
                } else {
                    int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECEIVE_SMS);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        requestPermission();
                    }
                }
            }
        });

      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mListener.finish();
          }
      });





    }

    private boolean validatePhoneNumber(){
        if (editTextPhoneNumber.toString().isEmpty()) {
            editTextPhoneNumber.setError("لطفا شماره موبایل را وارد کنید");
            return false;
        }if (editTextPhoneNumber.getText().length()<11) {
            editTextPhoneNumber.setError("لطفا شماره موبایل را کامل وارد کنید");
            return false;
        }
        return true;
    }


    public void getSmsPermissionFromUser(){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextPhoneNumber.getWindowToken(), 0);
        pSendPhoneNumber.sendPhoneNumber(editTextPhoneNumber.getText().toString());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_PERMMISION);
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
    public void onStartSendPhoneNumber() {
        progressBar.setVisibility(View.VISIBLE);
        editTextPhoneNumber.setEnabled(false);
        mListener.onStartSendPhoneNumber(editTextPhoneNumber.getText().toString());
    }

    public void sendPhoneNumberAgain(){
        if (editTextPhoneNumber.getText().toString().length() == 11){
            pSendPhoneNumber.sendPhoneNumber(editTextPhoneNumber.getText().toString());
        } else {
            Toast.makeText(getContext(), "شماره صحیح وارد کنید", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void sendPhoneNumberSuccess() {
        progressBar.setVisibility(View.GONE);
        editTextPhoneNumber.setEnabled(true);
        mListener.sendPhoneNumberSuccess();
    }

    @Override
    public void sendPhoneNumberFailed(String msg) {
        Log.d("TAG", "sendPhoneNumberFailed: "+msg);

        progressBar.setVisibility(View.GONE);
        textError.setText(msg);
        textError.setVisibility(View.VISIBLE);
    }

    public interface OnFragmentInteractionListener {
        void onStartSendPhoneNumber(String phoneNumber);
        void sendPhoneNumberSuccess();
        void finish();

        void sendPhoneNumberFailed(String msg);
    }
}
