package psb.com.kidpaint.competition;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.FragmentAllPaints;
import psb.com.kidpaint.competition.leaderBoard.FragmentLeaderBoard;
import psb.com.kidpaint.competition.myPaints.FragmentMyPaints;
import psb.com.kidpaint.competition.score.FragmentScore;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.match.Get.model.ResponseGetMatch;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class ActivityCompetition extends BaseActivity implements IVCompetition,
        FragmentMyPaints.OnFragmentInteractionListener,
        FragmentScore.OnFragmentInteractionListener,
        FragmentAllPaints.OnFragmentInteractionListener,
        FragmentLeaderBoard.OnFragmentInteractionListener {
    private static final String TAG_FRAGMENT_PAINTS = "TAG_FRAGMENT_PAINTS";
    private static final String TAG_FRAGMENT_All_PAINTS = "TAG_FRAGMENT_All_PAINTS";
    private static final String TAG_FRAGMENT_LEADER_BOARD = "TAG_FRAGMENT_LEADER_BOARD";
    private static final String TAG_FRAGMENT_SCORE = "TAG_FRAGMENT_SCORE";

    public static int CODE_REGISTER = 107;


    private ImageView back, userImage;
    private PCompetition pCompetition;
    private ProgressView progressView;

    private ResponseGetMatch mResponseGetMatch;
    private ResponseGetMyPaints mResponseGetMyPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;
    private ResponseGetLeaderShip mResponseGetLeaderShip;


    private FrameLayout frameLayoutScore;


    private TextView tabMe;
    private TextView tabAll;
    private TextView tabCompetition;
    private TextView text_user_name;
    private UserProfile userProfile;

    private ImageView sheep;
    private ImageView cow;
    private ImageView rooster;

    private ImageView imgBack, bronzeMedal, silverMedal, goldMedal;

    int matchId = 0;
    int levelId = 1;

    private Spinner spinnerMatch;
    private ProgressBar progressBarMatch;

    private int loadModeMatch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_competition);

        userProfile = new UserProfile(this);
        levelId = userProfile.get_KEY_LEVEL(1);
        pCompetition = new PCompetition(this);
        setViewContent();
        pCompetition.onGetMatch(0,levelId);

        setUserInfo();

        sheep.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideAnimal(sheep);
                        hideAnimal(rooster);
                    }
                }, 1000);
                sheep.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        createHelperWnd();
    }

    void setFragment(int position) {
        animalAnimation(position);
        setTabBgr(position);
        if (position == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentMyPaints().newInstance(mResponseGetMyPaints), TAG_FRAGMENT_PAINTS).commit();
        } else if (position == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentLeaderBoard().newInstance(mResponseGetLeaderShip, matchId, levelId), TAG_FRAGMENT_LEADER_BOARD).commit();
        } else if (position == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentAllPaints().newInstance(mResponseGetAllPaints, matchId, levelId), TAG_FRAGMENT_All_PAINTS).commit();
        }
    }

    void setUserInfo() {
        if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            text_user_name.setText(userProfile.get_KEY_FIRST_NAME("") + " " + userProfile.get_KEY_LAST_NAME(""));
            text_user_name.setOnClickListener(null);
        } else {
            text_user_name.setText("ثبت نام | ورود");
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

    private void animalAnimation(int position) {

        if (position == 0) {
            hideAnimal(sheep);
            hideAnimal(cow);
            showAnimal(rooster);
        } else if (position == 1) {
            hideAnimal(rooster);
            hideAnimal(sheep);
            showAnimal(cow);
        } else {
            hideAnimal(rooster);
            hideAnimal(cow);
            showAnimal(sheep);
        }

    }

    private void hideAnimal(final View animal) {
        if (animal.getTag() != null && animal.getTag().equals("hide")) return;
        int[] delay = {0, 300, 500};
        ObjectAnimator animMove = ObjectAnimator.ofFloat(animal, "translationY", 0.0f, -20.0f, animal.getHeight());
        animMove.setDuration(500); // miliseconds
        animMove.setStartDelay(delay[new Random().nextInt(3)]);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);
        animal.setTag("hide");
        animatorSet.start();
    }

    private void showAnimal(View animal) {
        if (animal.getTag() != null && animal.getTag().equals("show")) return;
        ObjectAnimator animMove = ObjectAnimator.ofFloat(animal, "translationY", animal.getHeight(), -20.0f, 0);
        animMove.setDuration(500); // miliseconds
        int[] delay = {0, 300, 500};
        animMove.setStartDelay(delay[new Random().nextInt(3)]);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);
        animal.setTag("show");
        animatorSet.start();
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

        progressBarMatch = findViewById(R.id.progressBar);
        spinnerMatch = findViewById(R.id.spinner_match);

        sheep = findViewById(R.id.img_animal_1);
        cow = findViewById(R.id.img_animal_3);
        rooster = findViewById(R.id.img_animal_5);

        imgBack = findViewById(R.id.img_back_1);
        bronzeMedal = findViewById(R.id.bronzeMedal);
        silverMedal = findViewById(R.id.silverMedal);
        goldMedal = findViewById(R.id.goldMedal);


        tabAll = findViewById(R.id.text_All);
        tabMe = findViewById(R.id.text_me);
        tabCompetition = findViewById(R.id.text_competition);
        userImage = findViewById(R.id.userImage);
        text_user_name = findViewById(R.id.text_user_name);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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


        bronzeMedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelId = 1;

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }
                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS) != null) {
                    ((FragmentAllPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS)).getAllPaints(matchId, levelId);
                }
                pCompetition.onGetMatch(1,levelId);

            }
        });

        silverMedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelId = 2;

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS) != null) {
                    ((FragmentAllPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS)).getAllPaints(matchId, levelId);
                }

                pCompetition.onGetMatch(1,levelId);
            }
        });

        goldMedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelId = 3;

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS) != null) {
                    ((FragmentAllPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS)).getAllPaints(matchId, levelId);
                }
                pCompetition.onGetMatch(1,levelId);

            }
        });

    }

    void setSpinnerAdapterMatch(){


        List<String> title=new ArrayList<>();
        for (int i = 0; i <mResponseGetMatch.getExtra().size() ; i++) {
            title.add(mResponseGetMatch.getExtra().get(i).getTitle());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.row_spinner,
                        title);
        /*ArrayAdapter<CharSequence> adp3 = ArrayAdapter.createFromResource(getContext(),
                title, R.layout.row_spinner);*/

        spinnerArrayAdapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinnerMatch.setAdapter(spinnerArrayAdapter);
        spinnerMatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                matchId=mResponseGetMatch.getExtra().get(position).getId();

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }
                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS) != null) {
                    ((FragmentAllPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS)).getAllPaints(matchId, levelId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        progressBarMatch.setVisibility(View.GONE);
        spinnerMatch.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onStartGetMatch(int mode) {
        loadModeMatch=mode;
        if (mode==0) {
            progressView.setVisibility(View.VISIBLE);
        }else {
            progressBarMatch.setVisibility(View.VISIBLE);
            spinnerMatch.setVisibility(View.GONE);

        }

    }

    @Override
    public void onSuccessGetGetMatch(ResponseGetMatch responseGetMatch) {
        this.mResponseGetMatch=responseGetMatch;
        if (loadModeMatch==0) {

            if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                pCompetition.onGetMyPaints();
            } else {
                pCompetition.onGetAllPaints();
            }
        }

            setSpinnerAdapterMatch();

    }

    @Override
    public void onFailedGetGetMatch(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetMatch(0,levelId);
            }
        });
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

        showIntro();
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
    public void onRefreshUserData() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
    }

    @Override
    public void setResponseMyPaint(ResponseGetMyPaints responseGetMyPaints) {
        mResponseGetMyPaints = responseGetMyPaints;
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
                    ((FragmentMyPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_PAINTS)).getMyPaints();
                }

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        }
    }

    private void showIntro() {
        final View v = findViewById(R.id.text_competition);
        final View v_2 = findViewById(R.id.text_me);
        final View v_3 = findViewById(R.id.text_All);
        FancyShowCaseView fancyShowCaseView = Intro.addIntroTo(this, v, IntroEnum.getLayoutId(9), IntroPosition.BOTTOM, IntroEnum.getSoundId(9), IntroEnum.getShareId(9), null, null);
        FancyShowCaseView fancyShowCaseView_me = Intro.addIntroTo(this, v_2, IntroEnum.getLayoutId(10), IntroPosition.BOTTOM, IntroEnum.getSoundId(10), IntroEnum.getShareId(10), null, null);
        FancyShowCaseView fancyShowCaseView_all = Intro.addIntroTo(this, v_3, IntroEnum.getLayoutId(11), IntroPosition.BOTTOM, IntroEnum.getSoundId(11), IntroEnum.getShareId(11), null, null);


        FancyShowCaseQueue fancyShowCaseQueue = new FancyShowCaseQueue();
        fancyShowCaseQueue.add(fancyShowCaseView);
        fancyShowCaseQueue.add(fancyShowCaseView_me);
        fancyShowCaseQueue.add(fancyShowCaseView_all);

        fancyShowCaseQueue.setCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete: ");
            }
        });
        fancyShowCaseQueue.show();
    }


}
