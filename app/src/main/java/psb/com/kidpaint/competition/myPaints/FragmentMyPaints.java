package psb.com.kidpaint.competition.myPaints;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.myPaints.adapter.Adapter_MyPaints;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class FragmentMyPaints extends Fragment implements IVMyPaints {

    private static final String ARG_MY_PAINTS = "ARG_MY_PAINTS";
    private ResponseGetMyPaints mResponseGetMyPaints;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerViewMyPaints;
    private TextView emptyViewMyPaints;
    private ProgressBar progressBarLoading;
    private UserProfile userProfile;
    private PMyPaints pPaints;
     private Adapter_MyPaints adapter_myPaints;
     private int loadMode=0;
     private ProgressDialog progressDialog;

    private TextView text_user_name;
    private ImageView  userImage;
    private Button back;

    public FragmentMyPaints() {
        // Required empty public constructor
    }


    public static FragmentMyPaints newInstance(ResponseGetMyPaints responseGetMyPaints) {
        FragmentMyPaints fragment = new FragmentMyPaints();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MY_PAINTS, responseGetMyPaints);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetMyPaints = (ResponseGetMyPaints) getArguments().getSerializable(ARG_MY_PAINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_paints, container, false);
        userProfile = new UserProfile(getContext());
        pPaints = new PMyPaints(this);
        pPaints.setResponseGetMyPaints(mResponseGetMyPaints);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        progressDialog.setCancelable(false);

        initView();

        if (mResponseGetMyPaints==null) {
            pPaints.onGetMyPaints(0);
        }/*else{
            if (adapter_myPaints!=null) {
                if (adapter_myPaints.getItemCount()>0) {
                    showIntro();
                }
            }
        }*/

        return view;
    }

    void initView() {


        emptyViewMyPaints = view.findViewById(R.id.emptyViewAllPaints);

        recyclerViewMyPaints = view.findViewById(R.id.recyclerViewMyPaints);
        progressBarLoading = view.findViewById(R.id.progressBar);

        adapter_myPaints=new Adapter_MyPaints(pPaints);
        LinearLayoutManager linearLayoutManager2 = new GridLayoutManager(getContext(), 1,GridLayoutManager.HORIZONTAL, true);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewMyPaints.setLayoutManager(linearLayoutManager2);
        AnimationAdapter animationAdapter2 = new SlideInBottomAnimationAdapter(adapter_myPaints);
        animationAdapter2.setDuration(100);
        animationAdapter2.setFirstOnly(false);
        recyclerViewMyPaints.setAdapter(animationAdapter2);
        emptyViewMyPaints.setVisibility(adapter_myPaints.getItemCount()>0?View.GONE:View.VISIBLE);

        userImage = view.findViewById(R.id.userImage);
        text_user_name = view.findViewById(R.id.text_user_name);
        back = view.findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_1);
                if (mListener!=null) {
                    mListener.onBackPressed();
                }
            }
        });

        setUserInfo();


    }
    public void getMyPaints(){
        pPaints.onGetMyPaints(0);

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
                /*    Intent intent = new Intent(ActivityCompetition.this, ActivityRegisterUser.class);
                    startActivityForResult(intent, CODE_REGISTER);*/
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
    public void onSuccessDeleteMyPaints(int position) {
        recyclerViewMyPaints.getAdapter().notifyDataSetChanged();
        recyclerViewMyPaints.getAdapter().notifyItemRemoved(position);
        recyclerViewMyPaints.getAdapter().notifyItemRangeChanged(position, adapter_myPaints.getItemCount());


        progressDialog.cancel();

        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("نقاشی شما با موفقیت حذف شد.");
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

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("حذف نقاشی");
        dialog.show();
    }

    @Override
    public void onStartDeleteMyPaints() {
      progressDialog.show();
    }

    @Override
    public void showDialogDelete(final int position) {
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("آیا از حذف این نقاشی مطمئن هستید؟");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
                pPaints.deleteMyPaints(position);
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.yes));
        dialog.setTitle("حذف نقاشی");
        dialog.show();
    }

    @Override
    public void onSelectPaint(PaintModel paintModel) {
        if (mListener!=null) {
          //  mListener.onSelectPaint(paintModel);
        }
    }

    @Override
    public void onStartGetMyPaints(int loadMode) {
        this.loadMode=loadMode;

        if (loadMode==0) {
           progressBarLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {


        mResponseGetMyPaints = responseGetMyPaints;

        if (mListener!=null) {
            mListener.setResponseMyPaint(mResponseGetMyPaints);
        }
        recyclerViewMyPaints.getAdapter().notifyDataSetChanged();
        emptyViewMyPaints.setVisibility(adapter_myPaints.getItemCount()>0?View.GONE:View.VISIBLE);
        progressBarLoading.setVisibility(View.GONE);




    }

    @Override
    public void onFailedGetMyPaints(int errorCode, String errorMessage) {
/*        if (loadMode==0) {
               progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pPaints.onGetMyPaints(0);
            }
        });
        }else{*/
          // swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            progressBarLoading.setVisibility(View.GONE);

        //  }


    }

    @Override
    public void onFailedDeleteMyPaints(int errorCode, String errorMessage) {
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

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("حذف نقاشی");
        dialog.show();
    }

    public interface OnFragmentInteractionListener {
       // void onSelectPaint(PaintModel paintModel);
        void onBackPressed();
        void setResponseMyPaint(ResponseGetMyPaints responseGetMyPaints);

    }



}
