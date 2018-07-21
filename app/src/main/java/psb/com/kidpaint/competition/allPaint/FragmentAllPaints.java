package psb.com.kidpaint.competition.allPaint;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.allPaint.adapter.Adapter_AllPaints;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class FragmentAllPaints extends Fragment implements IVAllPaints {
    private static final int REQUEST_CODE_REGISTER = 120;

    private static final String ARG_All_PAINTS = "ARG_LL_PAINTS";
    private ResponseGetAllPaints mResponseGetAllPaints;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerViewAllPaints;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyViewAllPaints;
    private ProgressBar progressBarLoading;
    private EditText searchView;
    private UserProfile userProfile;
    private PAllPaints pPaints;
     private Adapter_AllPaints adapter_allPaints;
    private ProgressDialog progressDialog;

    int sendPosition=-1;
    public FragmentAllPaints() {
        // Required empty public constructor
    }


    public static FragmentAllPaints newInstance(ResponseGetAllPaints responseGetAllPaints) {
        FragmentAllPaints fragment = new FragmentAllPaints();
        Bundle args = new Bundle();
        args.putSerializable(ARG_All_PAINTS, responseGetAllPaints);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponseGetAllPaints = (ResponseGetAllPaints) getArguments().getSerializable(ARG_All_PAINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_paints, container, false);
        userProfile = new UserProfile(getContext());
        pPaints = new PAllPaints(this);
        pPaints.setResponseGetAllPaints(mResponseGetAllPaints);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        initView();
        return view;
    }

    void initView() {

        searchView = view.findViewById(R.id.search);
        emptyViewAllPaints = view.findViewById(R.id.emptyViewAllPaints);
        recyclerViewAllPaints = view.findViewById(R.id.recyclerViewAllPaints);
        progressBarLoading = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pPaints.onGetAllPaints(searchView.getText().toString(), 1, 20);
            }
        });

        adapter_allPaints=new Adapter_AllPaints(pPaints);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewAllPaints.setLayoutManager(linearLayoutManager);
        AnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(adapter_allPaints);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);
        recyclerViewAllPaints.setAdapter(animationAdapter);


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
                    pPaints.onGetAllPaints(searchView.getText().toString().trim(), 1, 20);
                    progressBarLoading.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int spaceCount = 0;
                char[] characters = editable.toString().toCharArray();
                for (int i = 0; i < characters.length; i++) {
                    if (characters[i] == ' ') {
                        spaceCount++;
                    }
                }
                if (spaceCount == characters.length) {
                } else if (spaceCount < characters.length && characters[(characters.length - 1)] == ' ') {
                    pPaints.onGetAllPaints(editable.toString().trim(), 1, 20);
                    progressBarLoading.setVisibility(View.VISIBLE);

                }
            }
        });


     /*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                int spaceCount = 0;
                char[] characters = newText.toCharArray();
                for (int i = 0; i < characters.length; i++) {
                    if (characters[i] == ' ') {
                        spaceCount++;
                    }
                }
                if (spaceCount == characters.length) {
                } else if (spaceCount < characters.length && characters[(characters.length - 1)] == ' ') {
                    pPaints.onGetAllPaints(newText.toString().trim(), 1, 20);
                    progressBarLoading.setVisibility(View.VISIBLE);

                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        searchView.getApplicationWindowToken(),
                        InputMethodManager.SHOW_IMPLICIT, 0);
                pPaints.onGetAllPaints(query.trim(), 1, 20);
                progressBarLoading.setVisibility(View.VISIBLE);

                return true;
            }
        });*/



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
    public void onStartGetAllPaints() {

    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {

        swipeRefreshLayout.setRefreshing(false);
        progressBarLoading.setVisibility(View.GONE);
        mResponseGetAllPaints = responseGetAllPaints;
        if (mListener != null) {
            mListener.onSetResponseAllPaints(mResponseGetAllPaints);
        }
        recyclerViewAllPaints.getAdapter().notifyDataSetChanged();
        emptyViewAllPaints.setVisibility(recyclerViewAllPaints.getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBarLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSelectPaint(PaintModel paintModel) {
        if (mListener!=null) {
            mListener.onSelectPaint(paintModel);
        }
    }



    @Override
    public void onSuccessSendScore(int position) {
        progressDialog.cancel();
        recyclerViewAllPaints.getAdapter().notifyItemChanged(position);

        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("امتیاز شما با موفقیت ثبت شد.");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
            }

            @Override
            public void onNegativeClicked() {
                sendPosition = -1;
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز دهی");
        dialog.show();
    }

    @Override
    public void onFailedSendScore(int errorCode, String errorMessage) {
        progressDialog.cancel();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserRegisterDialog(final int position) {
        sendPosition = position;
        final MessageDialog dialog = new MessageDialog(getContext());

        dialog.setMessage("برای امتیاز دهی به نقاشی باید ثبت نام کنید یا وارد شوید!");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                startActivityForResult(new Intent(getContext(), ActivityRegisterUser.class), REQUEST_CODE_REGISTER);

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
    public void onStartSendScore() {
        progressDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                if (mListener!=null) {
                    mListener.onRefreshUserData();
                }
                if (sendPosition != -1) {
                    pPaints.onSendScore(sendPosition);
                }
            }else{
                sendPosition = -1;

            }
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSetResponseAllPaints(ResponseGetAllPaints responseGetAllPaints);
        void onSelectPaint(PaintModel paintModel);
        void onRefreshUserData();
    }
}
