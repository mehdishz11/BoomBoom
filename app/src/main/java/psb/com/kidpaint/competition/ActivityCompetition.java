package psb.com.kidpaint.competition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.FragmentAllPaints;
import psb.com.kidpaint.competition.leaderBoard.FragmentLeaderBoard;
import psb.com.kidpaint.competition.myPaints.FragmentMyPaints;
import psb.com.kidpaint.competition.score.FragmentScore;
import psb.com.kidpaint.home.HomeActivity;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class ActivityCompetition extends AppCompatActivity implements IVCompetition,
        FragmentMyPaints.OnFragmentInteractionListener,
        FragmentScore.OnFragmentInteractionListener,
        FragmentAllPaints.OnFragmentInteractionListener,
        FragmentLeaderBoard.OnFragmentInteractionListener {
    private static final String TAG_FRAGMENT_PAINTS = "TAG_FRAGMENT_PAINTS";
    private static final String TAG_FRAGMENT_All_PAINTS = "TAG_FRAGMENT_All_PAINTS";
    private static final String TAG_FRAGMENT_LEADER_BOARD = "TAG_FRAGMENT_LEADER_BOARD";
    private static final String TAG_FRAGMENT_SCORE = "TAG_FRAGMENT_SCORE";

    public static int CODE_REGISTER = 107;


    private ImageView back,userImage;
    private PCompetition pCompetition;
    private ProgressView progressView;

    private ResponseGetMyPaints mResponseGetMyPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;
    private ResponseGetLeaderShip mResponseGetLeaderShip;

    private FrameLayout frameLayoutScore;


    private TextView tabMe;
    private TextView tabAll;
    private TextView tabCompetition;
    private TextView text_user_name;
    private UserProfile userProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        ToolbarHandler.setToolbarColor(this, getWindow(), getWindow().getDecorView(), R.color.blue_4, false);
        userProfile=new UserProfile(this);
        pCompetition = new PCompetition(this);
        setViewContent();
        if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            pCompetition.onGetMyPaints();
        }else{
            pCompetition.onGetAllPaints();
        }

        setUserInfo();

    }


    void setFragment(int position) {
        setTabBgr(position);
        if (position == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMyPaints().newInstance(mResponseGetMyPaints), TAG_FRAGMENT_PAINTS).commit();
        } else if (position == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentLeaderBoard().newInstance(mResponseGetLeaderShip), TAG_FRAGMENT_LEADER_BOARD).commit();
        } else if (position == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentAllPaints().newInstance(mResponseGetAllPaints), TAG_FRAGMENT_All_PAINTS).commit();
        }
    }

    void setUserInfo(){
        if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            text_user_name.setText(userProfile.get_KEY_FIRST_NAME("") + " " + userProfile.get_KEY_LAST_NAME(""));
            text_user_name.setOnClickListener(null);
        }else{
            text_user_name.setText("ثبت نام کنید");
            text_user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityCompetition.this, ActivityRegisterUser.class);
                    startActivityForResult(intent, CODE_REGISTER);
                }
            });
        }


        if (!userProfile.get_KEY_IMG_URL("").isEmpty()) {
        Picasso.get().load(userProfile.get_KEY_IMG_URL("avatar")).placeholder(R.drawable.user_empty_gray).into(userImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                userImage.setImageResource(R.drawable.user_empty_gray);
            }
        });
    }
    }


    private void setTabBgr(int position) {
        tabAll.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        tabMe.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        tabCompetition.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        tabAll.setTextColor(ContextCompat.getColor(getContext(), R.color.brown_2));
        tabMe.setTextColor(ContextCompat.getColor(getContext(), R.color.brown_2));
        tabCompetition.setTextColor(ContextCompat.getColor(getContext(), R.color.brown_2));

        if (position == 0) {
            tabMe.setBackgroundResource(R.drawable.pallet_drop);
            tabMe.setTextColor(ContextCompat.getColor(getContext(), R.color.brown_3));
        } else if (position == 1) {
            tabCompetition.setBackgroundResource(R.drawable.pallet_drop);
            tabCompetition.setTextColor(ContextCompat.getColor(getContext(), R.color.brown_3));
        } else {
            tabAll.setBackgroundResource(R.drawable.pallet_drop);
            tabAll.setTextColor(ContextCompat.getColor(getContext(), R.color.brown_3));
        }

    }

    private void setViewContent() {

        progressView = findViewById(R.id.progressView);
        frameLayoutScore = findViewById(R.id.frameLayoutScore);

        tabAll = findViewById(R.id.text_All);
        tabMe = findViewById(R.id.text_me);
        tabCompetition = findViewById(R.id.text_competition);
        userImage = findViewById(R.id.userImage);
        text_user_name = findViewById(R.id.text_user_name);

        tabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(2);
            }
        });

        tabMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(0);
            }
        });

        tabCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(1);
            }
        });


        frameLayoutScore.setVisibility(View.GONE);

    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToolbarHandler.makeFullScreen(getWindow());
    }

    @Override
    public void onStartGetMyPaints() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
        mResponseGetMyPaints = responseGetMyPaints;
        pCompetition.onGetAllPaints();

    }

    @Override
    public void onFailedGetMyPaints(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetMyPaints();
            }
        });
    }

    @Override
    public void onStartGetAllPaints() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints = responseGetAllPaints;
        pCompetition.onGetLeaderBoard();


    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetAllPaints();
            }
        });
    }

    @Override
    public void onStartGetLeaderBoard() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessGetLeaderBoard(ResponseGetLeaderShip responseGetLeaderShip) {
        mResponseGetLeaderShip = responseGetLeaderShip;
        progressView.setVisibility(View.GONE);
        setFragment(1);
    }

    @Override
    public void onFailedGetLeaderBoard(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetLeaderBoard();
            }
        });
    }

    @Override
    public void onSetResponseAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints = responseGetAllPaints;
    }

    @Override
    public void onSelectPaint(PaintModel paintModel) {
        frameLayoutScore.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutScore, new FragmentScore().newInstance(paintModel), TAG_FRAGMENT_SCORE).commit();

    }

    @Override
    public void setResponseMyPaint(ResponseGetMyPaints responseGetMyPaints) {
        mResponseGetMyPaints=responseGetMyPaints;
    }

    @Override
    public void setResponseLeaderShip(ResponseGetLeaderShip responseLeaderShip) {
        mResponseGetLeaderShip = responseLeaderShip;
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE) != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE)).commit();
            frameLayoutScore.setVisibility(View.GONE);

        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult competition: " + requestCode);
        if (requestCode == CODE_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                setUserInfo();

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_PAINTS) != null) {
                    ((FragmentMyPaints)getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_PAINTS)).getMyPaints();
                }

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard)getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        }
    }


}
