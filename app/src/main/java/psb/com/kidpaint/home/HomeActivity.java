package psb.com.kidpaint.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.cview.CButton;
import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.HistoryFragment;
import psb.com.kidpaint.home.newPaint.NewPaintFragment;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.user.edit.ActivityEditProfile;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.musicHelper.MusicHelper;
import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;

public class HomeActivity extends AppCompatActivity implements IV_Home,
        HistoryFragment.OnFragmentInteractionListener,
        NewPaintFragment.OnFragmentInteractionListener
{

    public static int CODE_REGISTER = 107;
    public static int CODE_EDIT = 108;
    private CButton btnNewPainting;
    private CButton btnHistory;
    private ImageView drawerIcon;

    private final String TAG_FRAGMENT_HISTORY = "TAG_FRAGMENT_HISTORY";
    private final String TAG_FRAGMENT_NEW_PAINTING = "TAG_FRAGMENT_NEW_PAINTING";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RelativeLayout myPaint;
    private TextView registerOrLogin;
    private ImageView userImage;
    private TextView editUser, logOut;

    private UserProfile userProfile;
    private ProgressDialog progressDialog;
    private PHome pHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pHome=new PHome(this);
        userProfile=new UserProfile(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");

        btnNewPainting = findViewById(R.id.btn_new_painting);
        btnHistory = findViewById(R.id.btn_history);
        drawerIcon = findViewById(R.id.btn_more);

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
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, PaintActivity.class));
            }
        });


//        set default values
        btnNewPainting.setBackgroundResource(R.drawable.img_icon_rectangle_half_selected);
        btnHistory.setBackgroundResource(R.drawable.btn_rectangle_toolbar_half);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewPaintFragment().newInstance(), TAG_FRAGMENT_NEW_PAINTING).commit();

        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        setupDrawer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicHelper.stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

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

        }else{
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
        Intent intent=new Intent(HomeActivity.this, PaintActivity.class);
        intent.putExtra(PaintActivity.KEY_RESOURCE_OUTLINE,resId);
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
        Log.d("TAG", "onActivityResult home: "+requestCode);
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
        }
    }
}
