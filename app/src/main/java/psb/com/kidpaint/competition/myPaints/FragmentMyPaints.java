package psb.com.kidpaint.competition.myPaints;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.myPaints.adapter.Adapter_MyPaints;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class FragmentMyPaints extends Fragment implements IVMyPaints {

    private static final String ARG_MY_PAINTS = "ARG_MY_PAINTS";
    private ResponseGetMyPaints mResponseGetMyPaints;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerViewMyPaints;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyViewMyPaints;
    private ProgressBar progressBarLoading;
    private UserProfile userProfile;
    private PMyPaints pPaints;
     private Adapter_MyPaints adapter_myPaints;
     private int loadMode=0;
     private ProgressView progressView;
     private ProgressDialog progressDialog;
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
        progressView = view.findViewById(R.id.progressView);
        progressView.setVisibility(View.GONE);
        recyclerViewMyPaints = view.findViewById(R.id.recyclerViewMyPaints);
        progressBarLoading = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pPaints.onGetMyPaints(1);
            }
        });



        adapter_myPaints=new Adapter_MyPaints(pPaints);
        LinearLayoutManager linearLayoutManager2 = new GridLayoutManager(getContext(), 1);
        recyclerViewMyPaints.setLayoutManager(linearLayoutManager2);
        AnimationAdapter animationAdapter2 = new SlideInBottomAnimationAdapter(adapter_myPaints);
        animationAdapter2.setDuration(100);
        animationAdapter2.setFirstOnly(false);
        recyclerViewMyPaints.setAdapter(animationAdapter2);
        emptyViewMyPaints.setVisibility(adapter_myPaints.getItemCount()>0?View.GONE:View.VISIBLE);


    }
    public void getMyPaints(){
        pPaints.onGetMyPaints(0);

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
            mListener.onSelectPaint(paintModel);
        }
    }

    @Override
    public void onStartGetMyPaints(int loadMode) {
        this.loadMode=loadMode;

        if (loadMode==0) {
            progressView.setVisibility(View.VISIBLE);
            progressView.showProgress();
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
        swipeRefreshLayout.setRefreshing(false);
        progressView.setVisibility(View.GONE);




    }

    @Override
    public void onFailedGetMyPaints(int errorCode, String errorMessage) {
        if (loadMode==0) {
               progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pPaints.onGetMyPaints(0);
            }
        });
        }else{
           swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }


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
        void onSelectPaint(PaintModel paintModel);
        void setResponseMyPaint(ResponseGetMyPaints responseGetMyPaints);

    }



}
