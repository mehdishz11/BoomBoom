package psb.com.kidpaint.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.cview.CButton;
import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.ActivityCompetition;
import psb.com.kidpaint.home.history.HistoryFragment;
import psb.com.kidpaint.home.newPaint.NewPaintFragment;
import psb.com.kidpaint.home.splash.SplashFragment;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.user.edit.ActivityEditProfile;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public class HomeActivity extends AppCompatActivity implements IV_Home,
        HistoryFragment.OnFragmentInteractionListener,
        NewPaintFragment.OnFragmentInteractionListener,
        SplashFragment.OnFragmentInteractionListener {

    public static int CODE_REGISTER = 107;
    public static int CODE_Competition = 108;
    public static int CODE_EDIT = 108;
    private CButton btnNewPainting;
    private CButton btnHistory;
    private ImageView drawerIcon;

    private final String TAG_FRAGMENT_HISTORY = "TAG_FRAGMENT_HISTORY";
    private final String TAG_FRAGMENT_NEW_PAINTING = "TAG_FRAGMENT_NEW_PAINTING";
    private final String TAG_FRAGMENT_SPLASH = "TAG_FRAGMENT_SPLASH";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RelativeLayout myPaint;
    private TextView registerOrLogin;
    private ImageView userImage;
    private TextView editUser, logOut;

    private ImageView img_winner_1,img_winner_2,img_winner_3;


    private UserProfile userProfile;
    private ProgressDialog progressDialog;
    private PHome pHome;

    private FrameLayout frameLayoutSplash;

    private AppBarLayout appBar;

    private ResponseGetLeaderShip responseGetLeaderShip;

    private int lastOffset=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pHome = new PHome(this);
        userProfile = new UserProfile(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");

        img_winner_1 = findViewById(R.id.img_winner_1);
        img_winner_2 = findViewById(R.id.img_winner_2);
        img_winner_3 = findViewById(R.id.img_winner_3);

        btnNewPainting = findViewById(R.id.btn_new_painting);
        frameLayoutSplash = findViewById(R.id.frameLayoutSplash);
        btnHistory = findViewById(R.id.btn_history);
        drawerIcon = findViewById(R.id.btn_more);
        appBar=findViewById(R.id.app_bar);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(lastOffset>verticalOffset){
                    hideLion();
                    showRooster();
                    Log.d(App.TAG, "onOffsetChanged: scrollUp");
                }else{
                    showLion();
                    hideRooster();
                    Log.d(App.TAG, "onOffsetChanged: scrollDown");
                }
                lastOffset=verticalOffset;
            }
        });

        btnNewPainting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHistory.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
                btnNewPainting.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HistoryFragment(), TAG_FRAGMENT_HISTORY).commit();
            }
        });


        ToolbarHandler.makeTansluteToolbar(this, getWindow(), getWindow().getDecorView());

        Button btnCompetition = findViewById(R.id.competition);
        btnCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this, ActivityCompetition.class),CODE_Competition);
            }
        });


//        set default values
        btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
        btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutSplash, new SplashFragment().newInstance(), TAG_FRAGMENT_SPLASH).commit();

        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        setupDrawer();

        initAnimation();
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

    public void setupDrawer() {
        Log.d("TAG", "setupDrawer: ");
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        userImage = navigationView.findViewById(R.id.act_user_image);
        editUser = navigationView.findViewById(R.id.text_edit);
        logOut = navigationView.findViewById(R.id.act_sign_out);
        myPaint = navigationView.findViewById(R.id.ManageAddress);
        registerOrLogin = navigationView.findViewById(R.id.reg_or_login);

        if (userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
            logOut.setVisibility(View.GONE);
            myPaint.setVisibility(View.GONE);
            editUser.setVisibility(View.GONE);
            registerOrLogin.setText("ثبت نام | ورود");

        } else {
            editUser.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.VISIBLE);
            myPaint.setVisibility(View.VISIBLE);
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
        }


        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick editUser: ");
                Intent intent = new Intent(HomeActivity.this, ActivityEditProfile.class);
                startActivityForResult(intent, CODE_EDIT);
                drawer.closeDrawer(GravityCompat.END);
            }
        });


        myPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick manageAddress: ");

                /*  startActivityForResult(new Intent(MainActivity.this, ActivityShowUserAddresses.class), REQUEST_MANAGE_USER_ADDRESS);*/
                drawer.closeDrawer(GravityCompat.END);
            }
        });


        registerOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userProfile.get_KEY_PHONE_NUMBER("").isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, ActivityEditProfile.class);
                    startActivityForResult(intent, CODE_EDIT);
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    Intent intent = new Intent(HomeActivity.this, ActivityRegisterUser.class);
                    startActivityForResult(intent, CODE_REGISTER);
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.END);
                exitProfileDialog();
            }
        });
    }


    @Override
    public void onOutlineSelected(int resId) {
        Intent intent = new Intent(HomeActivity.this, PaintActivity.class);
        intent.putExtra(PaintActivity.KEY_RESOURCE_OUTLINE, resId);
        HomeActivity.this.startActivity(intent);
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
        setupDrawer();


    }

    @Override
    public void onLogoutFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    public void exitProfileDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_logout, null);
        final AlertDialog alertCancel = new AlertDialog.Builder(getContext()).create();
        alertCancel.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button cancel = promptView.findViewById(R.id.buttonCancel);
        Button taiid = promptView.findViewById(R.id.buttonTaiid);
        taiid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pHome.onStartLogout();
                alertCancel.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertCancel.dismiss();
            }
        });
        alertCancel.setView(promptView);
        alertCancel.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }*/
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult home: " + requestCode);
        if (requestCode == CODE_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                setupDrawer();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        } else if (requestCode == CODE_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                setupDrawer();
            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == CODE_Competition) {
            if (resultCode == Activity.RESULT_OK) {
                setupDrawer();
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
    }

    @Override
    public void splashFailed(String msg) {
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

    void setWinners(){
        if (responseGetLeaderShip!=null) {
            if (responseGetLeaderShip.getExtra().getLeaderModel().size()>0) {

                if(responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl()!=null && !responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl().isEmpty()){
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(0).getUser().getImageUrl())
                            .resize(Value.dp(200),Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_1);
                }

                if(responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl()!=null && !responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl().isEmpty()){
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(1).getUser().getImageUrl())
                            .resize(Value.dp(200),Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_2);
                }
                if(responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl()!=null && !responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl().isEmpty()){
                    Picasso
                            .get()
                            .load(responseGetLeaderShip.getExtra().getLeaderModel().get(2).getUser().getImageUrl())
                            .resize(Value.dp(200),Value.dp(200))
                            .onlyScaleDown()
                            .into(img_winner_3);
                }
            }
        }
    }




    public void initAnimation(){
        if(imageLion==null){
            imageLion=findViewById(R.id.lion);
            imageLion.setPivotX(imageLion.getWidth()/2);
            imageLion.setPivotY(imageLion.getHeight());
        }

        if(imageRooster==null){
            imageRooster=findViewById(R.id.rooster);
            imageRooster.setPivotX(imageRooster.getWidth()/2);
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
    private boolean isLionShow=true;
    private boolean isAnimationLion =false;

    private ImageView imageRooster;
    private boolean isRossterShow=true;
    private boolean isAnimationRooster =false;


    private void hideLion(){
        if(!isLionShow || isAnimationLion)return;

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(imageLion ,"rotation", 0.0f,-20,0);
        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageLion ,"translationX", 0.0f,-(imageLion.getWidth()-imageLion.getLeft()));

        animRotate.setDuration(500); // miliseconds
        animMove.setDuration(500); // miliseconds

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animRotate,animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isLionShow=false;
                isAnimationLion =false;
            }
        });
        isAnimationLion =true;
        animatorSet.start();
    }

    private void showLion(){
        if(isLionShow || isAnimationLion)return;

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(imageLion ,"rotation", -45,0.0f);
        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageLion ,"translationX", -(imageLion.getWidth()-imageLion.getLeft()),0.0f);

        animRotate.setDuration(500); // miliseconds
        animMove.setDuration(500); // miliseconds

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animRotate,animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageLion.setRotation(0);
                imageLion.setTranslationX(5);
                isLionShow=true;
                isAnimationLion =false;
            }
        });
        isAnimationLion =true;
        animatorSet.start();
    }

    private void hideRooster(){
        hideRooster(300);
    }
    private void hideRooster(int duration){
        if(!isRossterShow || isAnimationRooster)return;

        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageRooster ,"translationY", 0.0f,imageRooster.getHeight());


        animMove.setDuration(duration); // miliseconds

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRossterShow=false;
                isAnimationRooster =false;
            }
        });
        isAnimationRooster =true;
        animatorSet.start();
    }

    private void showRooster(){
        if(isRossterShow || isAnimationRooster)return;

        ObjectAnimator animMove = ObjectAnimator.ofFloat(imageRooster ,"translationY", imageRooster.getHeight(),0.0f);


        animMove.setDuration(300); // miliseconds

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animMove);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRossterShow=true;
                isAnimationRooster =false;
            }
        });
        isAnimationRooster =true;
        animatorSet.start();
    }
}
