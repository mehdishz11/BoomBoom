package psb.com.kidpaint.competition.score;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;


public class FragmentScore extends Fragment  implements IVScore {
    private static final String ARG_PARAM1 = "param1";
    private LeaderModel mPaintModel;
    private OnFragmentInteractionListener mListener;
    private View view;
    private ImageView paintImage,userImage;
    private TextView userName,imageScore;
    private Button send;
    private ProgressBar progressBar;
    private Button back;

    private ProgressDialog progressDialog;
    private PScore pScore;

    public FragmentScore() {
        // Required empty public constructor
    }

    public static FragmentScore newInstance(LeaderModel paintModel) {
        FragmentScore fragment = new FragmentScore();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, paintModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPaintModel = (LeaderModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_score, container, false);



        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        pScore=new PScore(this);
        initView();
        return view;
    }


    void initView(){
        userImage=view.findViewById(R.id.userImage);
        back=view.findViewById(R.id.btn_back);
        userName=view.findViewById(R.id.text_user_name);


        paintImage=view.findViewById(R.id.img_outline_bgr);
        send=view.findViewById(R.id.submit);
        progressBar=view.findViewById(R.id.progressBar);

        userName.setText(mPaintModel.getUser().getFirstName()+" "+mPaintModel.getUser().getLastName());
       // imageScore.setText(mPaintModel.getScore()+"");

        progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(mPaintModel.getUrl())
                .into(paintImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);

                        Log.d("TAG", "onError: ");
                        e.printStackTrace();
                    }
                });

        if (mPaintModel.getUser()!=null &&!mPaintModel.getUser().getImageUrl().isEmpty()) {
            Picasso.get().load(mPaintModel.getUser().getImageUrl()).placeholder(R.drawable.user_empty_gray).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    userImage.setImageResource(R.drawable.user_empty_gray);
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);
                mListener.onBackPressed();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mListener.onBackPressed();
                SoundHelper.playSound(R.raw.click_1);
                pScore.onSendScore(mPaintModel.getId());
            }
        });
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
    public void onStartSendScore() {
        progressDialog.show();
    }

    @Override
    public void onSuccessSendScore(ResponseScore responseScore) {
        progressDialog.cancel();
        Toast.makeText(getContext(), "امتیاز شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
        mListener.onBackPressed();
    }

    @Override
    public void onFailedSendScore(int errorCode, String errorMessage) {
        progressDialog.cancel();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    public interface OnFragmentInteractionListener {
        void onBackPressed();
    }
}
