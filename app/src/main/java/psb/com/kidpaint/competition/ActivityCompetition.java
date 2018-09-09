package psb.com.kidpaint.competition;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.FragmentAllPaints;
import psb.com.kidpaint.competition.leaderBoard.FragmentLeaderBoard;
import psb.com.kidpaint.competition.myPaints.FragmentMyPaints;
import psb.com.kidpaint.competition.score.FragmentScore;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.match.Get.model.ResponseGetMatch;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;


public class ActivityCompetition extends BaseActivity implements IVCompetition,
        FragmentMyPaints.OnFragmentInteractionListener,
        FragmentScore.OnFragmentInteractionListener,
        FragmentAllPaints.OnFragmentInteractionListener,
        FragmentLeaderBoard.OnFragmentInteractionListener {
    private static final String TAG_FRAGMENT_PAINTS = "TAG_FRAGMENT_PAINTS";
    private static final String TAG_FRAGMENT_SEARCH = "TAG_FRAGMENT_SEARCH";
    private static final String TAG_FRAGMENT_LEADER_BOARD = "TAG_FRAGMENT_LEADER_BOARD";
    private static final String TAG_FRAGMENT_SCORE = "TAG_FRAGMENT_SCORE";


    public static int CODE_REGISTER = 107;


    private ImageView  userImage;
    private PCompetition pCompetition;
    private ProgressView progressView;

    private ResponseGetMatch mResponseGetMatch;
    private ResponseGetMyPaints mResponseGetMyPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;
    private ResponseGetLeaderShip mResponseGetLeaderShip;


    private FrameLayout frameLayoutScore;
    private FrameLayout frameLayoutSearch;


    private TextView text_user_name;
    private UserProfile userProfile;

    private ImageView sheep;
    private ImageView cow;
    private ImageView rooster;
    private EditText searchView;

    private Button back;
    private ImageView bronzeMedal, silverMedal, goldMedal;
    private ImageView img_winner_1, img_winner_2, img_winner_3;
    int matchId = 0;
    int levelId = 1;
    private Spinner spinnerMatch;

    private RelativeLayout rel_my_paints;
    private ProgressBar progressBarMatch;

    private int loadModeMatch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_competition);

        userProfile = new UserProfile(this);
        levelId = userProfile.get_KEY_LEVEL(1);
        pCompetition = new PCompetition(this);
        setViewContent();
        //pCompetition.onGetMatch(0,levelId);
        if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            pCompetition.onGetMyPaints();
        } else {
            pCompetition.onGetLeaderBoard();
        }
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
        //  setTabBgr(position);
        if (position == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutScore, new FragmentMyPaints().newInstance(mResponseGetMyPaints), TAG_FRAGMENT_SCORE).commitNowAllowingStateLoss();
        } else if (position == 1) {
            SoundHelper.playSound(R.raw.audience);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentLeaderBoard().newInstance(mResponseGetLeaderShip, matchId, levelId), TAG_FRAGMENT_LEADER_BOARD).commitNowAllowingStateLoss();
        } else if (position == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSearch, new FragmentAllPaints().newInstance(searchView.getText().toString().trim(), matchId, levelId), TAG_FRAGMENT_SEARCH).commitNowAllowingStateLoss();
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


    private void setViewContent() {
        searchView = findViewById(R.id.search);

        progressView = findViewById(R.id.progressView);
        frameLayoutScore = findViewById(R.id.frameLayoutScore);
        frameLayoutSearch = findViewById(R.id.frameLayoutSearch);

        progressBarMatch = findViewById(R.id.progressBar);
        // spinnerMatch = findViewById(R.id.spinner_match);

        sheep = findViewById(R.id.img_animal_1);
        cow = findViewById(R.id.img_animal_3);
        rooster = findViewById(R.id.img_animal_5);

        back = findViewById(R.id.btn_back);
        rel_my_paints = findViewById(R.id.rel_my_paints);

        /*     bronzeMedal = findViewById(R.id.bronzeMedal);
        silverMedal = findViewById(R.id.silverMedal);
        goldMedal = findViewById(R.id.goldMedal);*/


        img_winner_1 = findViewById(R.id.img_winner_1);
        img_winner_2 = findViewById(R.id.img_winner_2);
        img_winner_3 = findViewById(R.id.img_winner_3);
        userImage = findViewById(R.id.userImage);
        text_user_name = findViewById(R.id.text_user_name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        frameLayoutScore.setVisibility(View.GONE);
        frameLayoutSearch.setVisibility(View.GONE);

        rel_my_paints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);
                if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    frameLayoutScore.setVisibility(View.VISIBLE);
                    setFragment(0);
                } else {
                    showUserRegisterDialog();
                }


            }
        });

/*
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
        });*/

        searchView.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE);
        searchView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(
                            searchView.getApplicationWindowToken(),
                            InputMethodManager.SHOW_IMPLICIT, 0);

                    if (searchView.getText().toString().trim().length()>0) {
                        frameLayoutSearch.setVisibility(View.VISIBLE);
                        setFragment(2);

                        searchView.setText("");
                    }

                    return true;
                }
                return false;
            }
        });



    }

    void setSpinnerAdapterMatch() {


        List<String> title = new ArrayList<>();
        for (int i = 0; i < mResponseGetMatch.getExtra().size(); i++) {
            title.add(mResponseGetMatch.getExtra().get(i).getTitle());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.row_spinner,
                        title);
        /*ArrayAdapter<CharSequence> adp3 = ArrayAdapter.createFromResource(getContext(),
                title, R.layout.row_spinner);*/

        spinnerArrayAdapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinnerMatch.setAdapter(spinnerArrayAdapter);
        spinnerMatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                matchId = mResponseGetMatch.getExtra().get(position).getId();

                Log.d(App.TAG, "onItemSelected: "+matchId);

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }
              /*  if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS) != null) {
                    ((FragmentAllPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_All_PAINTS)).getAllPaints(matchId, levelId);
                }*/
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
        loadModeMatch = mode;
        if (mode == 0) {
            progressView.setVisibility(View.VISIBLE);
        } else {
            progressBarMatch.setVisibility(View.VISIBLE);
            spinnerMatch.setVisibility(View.GONE);

        }

    }

    @Override
    public void onSuccessGetGetMatch(ResponseGetMatch responseGetMatch) {
        this.mResponseGetMatch = responseGetMatch;
        if (loadModeMatch == 0) {

            if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                pCompetition.onGetMyPaints();
            } else {
                pCompetition.onGetAllPaints();
            }
        }

        //   setSpinnerAdapterMatch();

    }

    @Override
    public void onFailedGetGetMatch(int errorCode, String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pCompetition.onGetMatch(0, levelId);
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
        pCompetition.onGetLeaderBoard();

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
        setWinners();
        //showIntro();

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
        setWinners();

    }

    @Override
    public void onSelectPaint(LeaderModel paintModel) {
        frameLayoutScore.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutScore, new FragmentScore().newInstance(paintModel), TAG_FRAGMENT_SCORE).commit();
        SoundHelper.playSound(R.raw.click_1);

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
        Log.d("TAG", "onBackPressed: "+(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE) != null));

        if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE) != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SCORE)).commit();
            frameLayoutScore.setVisibility(View.GONE);

        } else if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SEARCH) != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SEARCH)).commit();
            frameLayoutSearch.setVisibility(View.GONE);

        } else {
            SoundHelper.playSound(R.raw.click_1);
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

                /*if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_PAINTS) != null) {
                    ((FragmentMyPaints) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_PAINTS)).getMyPaints();
                }

                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD) != null) {
                    ((FragmentLeaderBoard) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LEADER_BOARD)).onGetLeaderShip(matchId, levelId);
                }*/

                pCompetition.onGetMyPaints();
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

    void setWinners() {
        if (mResponseGetLeaderShip != null) {
            if (mResponseGetLeaderShip.getExtra().getLeaderModel().size() > 0) {

                if (mResponseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl() != null && !mResponseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(mResponseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_1);
                }
                if (mResponseGetLeaderShip.getExtra().getLeaderModel().size()>=2) {


                    if (mResponseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl() != null && !mResponseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl().isEmpty()) {
                        Picasso
                                .get()
                                .load(mResponseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl())
                                .resize(Value.dp(200), Value.dp(200))
                                .onlyScaleDown()
                                .into(img_winner_2);
                    }
                }
                if (mResponseGetLeaderShip.getExtra().getLeaderModel().size()>=3) {

                    if (mResponseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl() != null && !mResponseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl().isEmpty()) {
                        Picasso
                                .get()
                                .load(mResponseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl())
                                .resize(Value.dp(200), Value.dp(200))
                                .onlyScaleDown()
                                .into(img_winner_3);
                    }
                }
            }
/*
            if (mResponseGetLeaderShip.getExtra().getMyRank() != null) {
                text_user_rate.setText("بهترین رتبه شما " + mResponseGetLeaderShip.getExtra().getMyRank().getRank());
                text_user_rate.setOnClickListener(null);

            } else {
                if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    text_user_rate.setText("ثبت نام کنید");
                    text_user_rate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(HomeActivity_2.this, ActivityRegisterUser.class), CODE_REGISTER_First);
                        }
                    });

                } else {
                    text_user_rate.setText("شرکت نکرده اید");
                    text_user_rate.setOnClickListener(null);
                }
            }*/
        }

    }

    public void showUserRegisterDialog() {
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("برای نمایش لیست نقاشی باید ثبت نام کنید یا وارد شوید!");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                startActivityForResult(new Intent(getContext(), ActivityRegisterUser.class), CODE_REGISTER);
                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.enter));
        dialog.setTitle(getString(R.string.register_login));
        dialog.show();
    }


}
