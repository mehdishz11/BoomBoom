package psb.com.kidpaint.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.ActivityCompetition;
import psb.com.kidpaint.home.history.HistoryFragment;
import psb.com.kidpaint.home.history.adapter.HistoryAdapter;
import psb.com.kidpaint.home.newPaint.NewPaintFragment;
import psb.com.kidpaint.home.splash.SplashFragment;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.score.DialogScorePackage;
import psb.com.kidpaint.user.edit.ActivityEditProfile;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.DialogSettings;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;

public class HomeActivity_2 extends BaseActivity implements IV_Home,
        SplashFragment.OnFragmentInteractionListener {

    public static int CODE_REGISTER_First = 106;
    public static int CODE_REGISTER = 107;
    public static int CODE_Competition = 108;
    public static int CODE_EDIT = 108;
    public static int CODE_PAINT_ACTIVITY = 109;

    private ImageView drawerIcon, btn_settings;
    private int sendPosition = -1;

    private HistoryAdapter historyAdapter;

    private final String TAG_FRAGMENT_SPLASH = "TAG_FRAGMENT_SPLASH";


    private TextView registerOrLogin,text_user_rate,text_user_Coin;
    private ImageView userImage;
    private TextView editUser;
    private boolean isFirstRegister = false;

    private ImageView img_winner_1, img_winner_2, img_winner_3,img_podium;


    private UserProfile userProfile;
    private ProgressDialog progressDialog;
    private PHome pHome;

    private FrameLayout frameLayoutSplash;


    private ResponseGetLeaderShip responseGetLeaderShip;
    private ResponsePrize responsePrize;


    private SplashFragment splashFragment;
    private RecyclerView recyclerView;

    private int lastOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_2);


        pHome = new PHome(this);
        userProfile = new UserProfile(this);

        Log.d("TAG", "onCreate token: " + userProfile.get_KEY_FCM("-"));

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeActivity_2.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
                userProfile.set_KEY_FCM(newToken);

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");

        img_winner_1 = findViewById(R.id.img_winner_1);
        img_winner_2 = findViewById(R.id.img_winner_2);
        img_winner_3 = findViewById(R.id.img_winner_3);
        btn_settings = findViewById(R.id.btn_settings);
        drawerIcon = findViewById(R.id.btn_more);

        recyclerView = findViewById(R.id.recyclerView);

        splashFragment = new SplashFragment().newInstance();



        ToolbarHandler.makeTansluteToolbar(this, getWindow(), getWindow().getDecorView());



        frameLayoutSplash=findViewById(R.id.frameLayoutSplash);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // drawer.openDrawer(GravityCompat.END);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);

                DialogSettings cDialog = new DialogSettings(HomeActivity_2.this);
                cDialog.show();


            }
        });

        setupUserInfo();

        initAnimation();

        createHelperWnd();

        pHome.getMyPaintHistory();

    }

    void showDialogPackage(){
        DialogScorePackage cDialog = new DialogScorePackage(HomeActivity_2.this);
        cDialog.setShowBtnDiscardBuy(false);
        cDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicHelper.stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToolbarHandler.makeFullScreen(getWindow());
        MusicHelper.stopMusic();
        MusicHelper.playMusic(R.raw.bgr_happy_sunshine);
    }



    public void setupUserInfo() {

        userImage = findViewById(R.id.act_user_image);
        editUser = findViewById(R.id.text_edit);
        registerOrLogin = findViewById(R.id.reg_or_login);
        text_user_Coin = findViewById(R.id.text_userCoin);
        text_user_rate = findViewById(R.id.text_userRank);
        img_podium = findViewById(R.id.img_podium);

        img_podium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity_2.this, ActivityCompetition.class), CODE_Competition);
            }
        });
       text_user_Coin.setText(userProfile.get_KEY_SCORE(0)+" سکه");


        setUnreadMessageCount();


        if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            editUser.setVisibility(View.GONE);
            registerOrLogin.setText("ثبت نام | ورود");

        } else {
            editUser.setVisibility(View.VISIBLE);
            registerOrLogin.setText(userProfile.get_KEY_FIRST_NAME("") + " " + userProfile.get_KEY_LAST_NAME(""));

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
        } else {
            Picasso.get().load(R.drawable.user_profile).placeholder(R.drawable.user_empty_gray).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    userImage.setImageResource(R.drawable.user_empty_gray);
                }
            });
        }


        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity_2.this, ActivityEditProfile.class);
                startActivityForResult(intent, CODE_EDIT);
            }
        });

   /*     relMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity_2.this, ActivityMyMessages.class);
                startActivityForResult(intent, CODE_REGISTER);
                drawer.closeDrawer(GravityCompat.END);
            }
        });


        relMyPrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity_2.this, ActivityMyPrize.class);
                startActivityForResult(intent, CODE_REGISTER);
                drawer.closeDrawer(GravityCompat.END);
            }
        });*/


        registerOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    Intent intent = new Intent(HomeActivity_2.this, ActivityEditProfile.class);
                    startActivityForResult(intent, CODE_EDIT);
                } else {
                    Intent intent = new Intent(HomeActivity_2.this, ActivityRegisterUser.class);
                    startActivityForResult(intent, CODE_REGISTER_First);
                }
            }
        });

       /* logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitProfileDialog();
            }
        });*/
    }

    void setUnreadMessageCount() {
     /*   if (pHome != null && unreadMessageCount != null) {
            int unread = pHome.getUnreadMessageCount();
            unreadMessageCount.setText(unread + "");
            unreadMessageCount.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
        }*/
    }
    @Override
    public void onOutlineSelected(int resId) {
        Intent intent = new Intent(HomeActivity_2.this, PaintActivity.class);
        intent.putExtra(PaintActivity.KEY_RESOURCE_OUTLINE, resId);
        startActivityForResult(intent, CODE_PAINT_ACTIVITY);
    }



    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onStartLogout() {
        progressDialog.show();
    }//

    @Override
    public void onLogoutSuccess() {
        progressDialog.cancel();
        // finish();
        setupUserInfo();

        text_user_rate.setText("ثبت نام کنید");
        text_user_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity_2.this, ActivityRegisterUser.class), CODE_REGISTER_First);
            }
        });


    }

    @Override
    public void onLogoutFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void startGetPrizeRequest() {

    }

    @Override
    public void prizeRequestSuccess(int score) {
        responseGetLeaderShip.getExtra().setMyScore(score);
       // setPrizeLayout();
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage(getString(R.string.prizedeliveryContact));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                //dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                //dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.prizeRegistered));

        dialog.show();
    }

    @Override
    public void prizeRequestFailed(String msg) {

    }

    public void exitProfileDialog() {

        ////////////////
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("آیا میخواهید از حساب کاربری خود خارج شوید؟");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                pHome.onStartLogout();
            }

            @Override
            public void onNegativeClicked() {
                //dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.yes));
        dialog.setTitle("خروج");

        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      refreshUserRank();
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult home: " + requestCode);
        if (requestCode == CODE_REGISTER_First) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                if (data.hasExtra("First")) {
                    isFirstRegister = data.getIntExtra("First", 1) == 0 ? true : false;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();


            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        }
        if (requestCode == CODE_REGISTER) {
            setUnreadMessageCount();
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        } else if (requestCode == CODE_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == CODE_Competition) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, splashFragment, TAG_FRAGMENT_SPLASH).commit();

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == CODE_PAINT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                setupUserInfo();

                // TODO: 8/9/2018 adapter refresh

                pHome.getMyPaintHistory();
            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // splashFragment
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGetStickers() {

    }

    @Override
    public void splashSuccess() {
        frameLayoutSplash.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SPLASH)).commit();
        //showIntro();
        if (isFirstRegister) {
          //  showIntroNewUser();
        }
    }

    @Override
    public void splashFailed(String msg) {
        frameLayoutSplash.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SPLASH)).commit();
        //showIntro();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getRankStarted() {

    }

    @Override
    public void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip) {
        this.responseGetLeaderShip = responseGetLeaderShip;
        setWinners();
    }

    @Override
    public void getRankFailed() {

    }

    @Override
    public void getPrizeSuccess(ResponsePrize responsePrize) {
        this.responsePrize = responsePrize;
    }

    @Override
    public void getPrizeFailed(ResponsePrize responsePrize) {
        this.responsePrize = responsePrize;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Activity methods
    ///////////////////////////////////////////////////////////////////////////
    private void showIntro() {
        final View view = findViewById(R.id.intro_view_1);
        final View view2 = findViewById(R.id.intro_view_2);
        FancyShowCaseView fancyShowCaseView = Intro.addIntroTo(this, view, IntroEnum.getLayoutId(1), IntroPosition.TOP, IntroEnum.getSoundId(1), IntroEnum.getShareId(1), null, null);
        FancyShowCaseView fancyShowCaseView_2 = Intro.addIntroTo(this, view2, IntroEnum.getLayoutId(2), IntroPosition.TOP, IntroEnum.getSoundId(2), IntroEnum.getShareId(2), null, null);


        FancyShowCaseQueue fancyShowCaseQueue = new FancyShowCaseQueue();
        fancyShowCaseQueue.add(fancyShowCaseView);
        fancyShowCaseQueue.add(fancyShowCaseView_2);
        fancyShowCaseQueue.setCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete: ");
            }
        });
        fancyShowCaseQueue.show();
    }

    private void showIntroNewUser() {

        final View view = findViewById(R.id.prizeHint);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FancyShowCaseView fancyShowCaseView = Intro.addIntroTo(HomeActivity_2.this, view, IntroEnum.getLayoutId(14), IntroPosition.TOP, IntroEnum.getSoundId(14), IntroEnum.getShareId(14), null, null);


                FancyShowCaseQueue fancyShowCaseQueue = new FancyShowCaseQueue();
                fancyShowCaseQueue.add(fancyShowCaseView);
                fancyShowCaseQueue.setCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        Log.d("TAG", "onComplete: ");
                    }
                });
                fancyShowCaseQueue.show();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });
    }

    void setWinners() {
        if (responseGetLeaderShip != null) {
            if (responseGetLeaderShip.getExtra().getLeaderModel().size() > 0) {

                if (responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl() != null && !responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_1);
                }

                if (responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl() != null && !responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_2);
                }
                if (responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl() != null && !responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl().isEmpty()) {
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl())
                            .resize(Value.dp(200), Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_3);
                }
            }

            if (responseGetLeaderShip.getExtra().getMyRank() != null) {
                text_user_rate.setText("بهترین رتبه شما " + responseGetLeaderShip.getExtra().getMyRank().getRank());
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
            }
        }


        if (responsePrize != null && responsePrize.getExtra() != null && responsePrize.getExtra().size() > 0) {
            if (!responsePrize.getExtra().get(0).getImageUrl().isEmpty()) {
              //  setPrizeLayout();

            } else {
              //  relPrize.setVisibility(View.GONE);
            }
        } else {
           // relPrize.setVisibility(View.GONE);
        }
    }

    private boolean isRegistered() {
        UserProfile userProfile = new UserProfile(this);
        Log.d("isRegistered", "isRegistered: " + userProfile.get_KEY_PHONE_NUMBER("-1"));
        if (!userProfile.get_KEY_PHONE_NUMBER("").equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void setPrizeDialogLowScore(int lowScore) {
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("شما باید برای دریافت این جایزه " + lowScore + " امتیاز کم دارید!");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                //dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                //dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز کافی نیست");

        dialog.show();
    }

    private void dialogGivePrize(final int prizeId) {
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage(getString(R.string.prizeRequestMsg));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                ParamsPrizeRequest paramsPrizeRequest = new ParamsPrizeRequest();
                paramsPrizeRequest.setMobile(userProfile.get_KEY_PHONE_NUMBER(""));
                paramsPrizeRequest.setPrizeId(prizeId);
                pHome.prizeRequest(paramsPrizeRequest);
                dialog.dismiss();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.prizeGiven));

        dialog.show();
    }

    private void dialogRegister() {
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage(getString(R.string.registerBefore));
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
                Intent intent = new Intent(HomeActivity_2.this, ActivityRegisterUser.class);
                startActivityForResult(intent, CODE_REGISTER);
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();
            }
        });
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.registerBeforeTitle));
        dialog.show();
    }

    public void initAnimation() {
        if (imageLion == null) {
            imageLion = findViewById(R.id.lion);
            imageLion.setPivotX(imageLion.getWidth() / 2);
            imageLion.setPivotY(imageLion.getHeight());
        }

        if (imageRooster == null) {
            imageRooster = findViewById(R.id.rooster);
            imageRooster.setPivotX(imageRooster.getWidth() / 2);
            imageRooster.setPivotY(imageRooster.getHeight());
        }

        imageRooster.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hideRooster(0);
                imageRooster.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    private ImageView imageLion;
    private boolean isLionShow = true;
    private boolean isAnimationLion = false;

    private ImageView imageRooster;
    private boolean isRossterShow = true;
    private boolean isAnimationRooster = false;


    private void hideLion() {
        if (!isLionShow || isAnimationLion) return;

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(imageLion, "rotation", 0.0f, -20, 0);
        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageLion, "translationX", 0.0f, -(imageLion.getWidth() - imageLion.getLeft()));

        animRotate.setDuration(500); // miliseconds
        animMove.setDuration(500); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animRotate, animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isLionShow = false;
                isAnimationLion = false;
            }
        });
        isAnimationLion = true;
        animatorSet.start();
    }

    private void showLion() {
        if (isLionShow || isAnimationLion) return;

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(imageLion, "rotation", -45, 0.0f);
        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageLion, "translationX", -(imageLion.getWidth() - imageLion.getLeft()), 0.0f);

        animRotate.setDuration(500); // miliseconds
        animMove.setDuration(500); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animRotate, animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageLion.setRotation(0);
                imageLion.setTranslationX(5);
                isLionShow = true;
                isAnimationLion = false;
            }
        });
        isAnimationLion = true;
        animatorSet.start();
    }

    private void hideRooster() {
        hideRooster(300);
    }

    private void hideRooster(int duration) {
        if (!isRossterShow || isAnimationRooster) return;

        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageRooster, "translationY", 0.0f, imageRooster.getHeight());


        animMove.setDuration(duration); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRossterShow = false;
                isAnimationRooster = false;
            }
        });
        isAnimationRooster = true;
        animatorSet.start();
    }

    private void showRooster() {
        if (isRossterShow || isAnimationRooster) return;

        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageRooster, "translationY", imageRooster.getHeight(), 0.0f);


        animMove.setDuration(300); // miliseconds

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRossterShow = true;
                isAnimationRooster = false;
            }
        });
        isAnimationRooster = true;
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    public void refreshUserRank() {
        if (userProfile != null) {
            text_user_rate.setText("بهترین رتبه شما " + userProfile.get_KEY_RANK(0));
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
        }
    }


    //================================================== ===========
    //===================================================== =        =

    @Override
    public void onStartMyPaintHistory() {

    }

    @Override
    public void onGetMyPaintHistorySuccess() {
        historyAdapter = new HistoryAdapter(pHome);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        AnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(historyAdapter);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);

        recyclerView.setAdapter(animationAdapter);

      //  emptyView.setVisibility(historyAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);

       // showIntro();
    }

    @Override
    public void onGetMyPaintHistoryFailed() {

    }

    @Override
    public void onSelecteditem(String filePath) {

        startActivityForResult(new Intent(getContext(), PaintActivity.class).putExtra("Path",filePath),CODE_PAINT_ACTIVITY);

    }

    @Override
    public void onStartPostPaint() {

        progressDialog.show();

    }

    @Override
    public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {
        progressDialog.cancel();

        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("نقاشی شما با موفقیت ارسال شد کد پیگیری نقاشی شما " + responsePostPaint.getExtra() + " میباشد");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
              //  showIntroCompetition();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });
        dialog.setAcceptButtonMessage("باشه");
        dialog.setTitle("مسابقه");
        dialog.show();
    }

    @Override
    public void onFailedPostPaint(int errorCode, String errorMessage) {
        progressDialog.cancel();

        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage(errorMessage);
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });
        dialog.setSoundId(R.raw.are_you_sure_exit);
        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle(getString(R.string.danger));
        dialog.show();

    }

    @Override
    public void showUserRegisterDialog(final int position) {
        sendPosition = position;
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("برای ارسال نقاشی باید ثبت نام کنید یا وارد شوید!");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                startActivityForResult(new Intent(getContext(), ActivityRegisterUser.class), CODE_REGISTER);

                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                sendPosition = -1;
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.enter));
        dialog.setTitle(getString(R.string.register_login));
        dialog.show();
    }

    @Override
    public void showDeleteDialog(final int position) {
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("آیا از حذف نقاشی مطمئن هستید؟");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                pHome.deletePaint(position);
                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.yes));
        dialog.setTitle("حذف");
        dialog.show();
    }


}