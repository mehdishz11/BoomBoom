package psb.com.kidpaint.home.history;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.home.history.adapter.HistoryAdapter;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.webApi.paint.postPaint.ResponsePostPaint;

public class HistoryFragment extends Fragment implements IVHistory {
    private static final int REQUEST_CODE_REGISTER=120;

    private View view;
    private TextView emptyView;


    private OnFragmentInteractionListener mListener;
    private PHistory pHistory;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private int sendPosition=-1;

    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("لطفا کمی صبر کنید...");
        progressDialog.setCancelable(false);
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        pHistory = new PHistory(this);
        pHistory.getMyPaintHistory();
        return view;


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else  if (getParentFragment() instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) getParentFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStartMyPaintHistory() {

    }

    @Override
    public void onGetMyPaintHistorySuccess() {
        historyAdapter = new HistoryAdapter(pHistory);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);

        AnimationAdapter animationAdapter=new SlideInBottomAnimationAdapter(historyAdapter);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);

        recyclerView.setAdapter(animationAdapter);

        emptyView.setVisibility(historyAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onGetMyPaintHistoryFailed() {

    }

    @Override
    public void onSelecteditem(String filePath) {

    }

    @Override
    public void onStartPostPaint() {

        progressDialog.show();

    }

    @Override
    public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {
       progressDialog.cancel();

        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("نقاشی شما با موفقیت ارسال شد کد پیگیری نقاشی شما "+responsePostPaint.getExtra()+" میباشد");
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
        dialog.setTitle(getString(R.string.dangger));
        dialog.show();

    }

    @Override
    public void showUserRegisterDialog(final int position) {
        sendPosition=position;
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("برای ارسال نقاشی باید ثبت نام کنید. آیامیخواهید ثبت نام کنید؟");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                startActivityForResult(new Intent(getContext(), ActivityRegisterUser.class),REQUEST_CODE_REGISTER);

                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                sendPosition=-1;
                dialog.cancel();

            }
        });
        dialog.setSoundId(R.raw.are_you_sure_exit);
        dialog.setAcceptButtonMessage(getContext().getString(R.string.register));
        dialog.setTitle(getString(R.string.register));
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult requestCode: "+requestCode);
        Log.d("TAG", "onActivityResult resultCode: "+resultCode);
        Log.d("TAG", "onActivityResult sendPosition: "+sendPosition);
        if (requestCode==REQUEST_CODE_REGISTER && resultCode== Activity.RESULT_OK) {
            mListener.setupDrawer();
            if (sendPosition!=-1) {
                pHistory.postPaint(sendPosition);
            }
            sendPosition=-1;
        }else{
            sendPosition=-1;
        }
    }

    public interface OnFragmentInteractionListener {
          void setupDrawer();
    }
}
