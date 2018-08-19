package psb.com.kidpaint.competition.allPaint;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.ActivityCompetition;
import psb.com.kidpaint.competition.allPaint.adapter.Adapter_AllPaints;
import psb.com.kidpaint.painting.PaintActivity;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.EndlessRecyclerViewScrollListener;
import psb.com.kidpaint.utils.GridLayoutManager_EndlessRecyclerOnScrollListener;
import psb.com.kidpaint.utils.IntroEnum;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;
import psb.com.kidpaint.utils.customView.intro.Intro;
import psb.com.kidpaint.utils.customView.intro.IntroPosition;
import psb.com.kidpaint.utils.customView.intro.showCase.DismissListener;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseQueue;
import psb.com.kidpaint.utils.customView.intro.showCase.FancyShowCaseView;
import psb.com.kidpaint.utils.customView.intro.showCase.OnCompleteListener;
import psb.com.kidpaint.utils.customView.intro.showCase.OnShowListener;
import psb.com.kidpaint.utils.customView.intro.showCase.OnViewInflateListener;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.shareModel.PaintModel;


public class FragmentAllPaints extends Fragment implements IVAllPaints {
    private static final int REQUEST_CODE_REGISTER = 120;

    private static final String ARG_All_PAINTS = "ARG_LL_PAINTS";
    private ResponseGetAllPaints mResponseGetAllPaints;
    private static final String ARG_LEVEL = "ARG_LEVEL";
    private static final String ARG_MATCHID = "ARG_MATCHID";
    int matchId = 0;
    int level = 1;
    private String searchText = "";

    private OnFragmentInteractionListener mListener;

    private View view;
    private RecyclerView recyclerViewAllPaints;
    private TextView emptyViewAllPaints;
    private ProgressBar progressBarLoading;
    private EditText searchView;
    private UserProfile userProfile;
    private PAllPaints pPaints;
    private Adapter_AllPaints adapter_allPaints;
    private ProgressDialog progressDialog;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ImageView userImage, back;
    private TextView text_user_name;


    int sendPosition = -1;

    public FragmentAllPaints() {
        // Required empty public constructor
    }


    public static FragmentAllPaints newInstance(String searchText, int matchId, int level) {
        FragmentAllPaints fragment = new FragmentAllPaints();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putString(ARG_All_PAINTS, searchText);
        args.putInt(ARG_LEVEL, level);
        args.putInt(ARG_MATCHID, matchId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchText = getArguments().getString(ARG_All_PAINTS);
            level = getArguments().getInt(ARG_LEVEL);
            matchId = getArguments().getInt(ARG_MATCHID);
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_paints, container, false);
        userProfile = new UserProfile(getContext());
        pPaints = new PAllPaints(this);
        pPaints.setResponseGetAllPaints(mResponseGetAllPaints);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا کمی صبر کنید ...");
        initView();
        //  setUserInfo();

        searchView.setText(searchText);
        pPaints.onGetAllPaints(searchView.getText().toString(), 1, 20, matchId, level);


        if (pPaints != null) {
            showIntro();

        }
        return view;
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
                    Intent intent = new Intent(getContext(), ActivityRegisterUser.class);
                    startActivityForResult(intent, REQUEST_CODE_REGISTER);
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


    void initView() {

        searchView = view.findViewById(R.id.search);
        emptyViewAllPaints = view.findViewById(R.id.emptyViewAllPaints);
        recyclerViewAllPaints = view.findViewById(R.id.recyclerViewAllPaints);
        progressBarLoading = view.findViewById(R.id.progressBar);
        back = view.findViewById(R.id.img_back_1);
/*        userImage = view.findViewById(R.id.userImage);
        text_user_name = view.findViewById(R.id.text_user_name);*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null) {
                    mListener.onBackPressed();
                }
            }
        });


        adapter_allPaints = new Adapter_AllPaints(pPaints);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerViewAllPaints.setLayoutManager(linearLayoutManager);
        AnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(adapter_allPaints);
        animationAdapter.setDuration(100);
        animationAdapter.setFirstOnly(false);
        recyclerViewAllPaints.setAdapter(animationAdapter);

      /*  recyclerViewAllPaints.setOnScrollListener(new GridLayoutManager_EndlessRecyclerOnScrollListener((GridLayoutManager) linearLayoutManager,pPaints.getServerAllPaintsSize()) {
            @Override
            public void onLoadMore(int load_count,int page) {
                if (progressBarLoading.getVisibility()==View.GONE) {
                    progressBarLoading.setVisibility(View.VISIBLE);
                    pPaints.onGetAllPaints(searchView.getText().toString().trim(), page, 20,matchId,level);
                }
            }
        });*/

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                if (pPaints.getServerAllPaintsSize() > adapter_allPaints.getItemCount()) {
                    int loadcount = 20;
                    int current_page = (int) Math.ceil((adapter_allPaints.getItemCount() + loadcount) / 20.0);

                    Log.d("TAG", "onLoadMore: " + current_page);
                    progressBarLoading.setVisibility(View.VISIBLE);
                    pPaints.onGetAllPaints(searchView.getText().toString().trim(), current_page, 20, matchId, level);

                }
                // Log.d("TAG", "onLoadMore: "+page);
            }
        };
        // Adds the scroll listener to RecyclerView
        // recyclerViewAllPaints.addOnScrollListener(scrollListener);


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
                    pPaints.onGetAllPaints(searchView.getText().toString().trim(), 1, 20, matchId, level);
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
                    pPaints.onGetAllPaints(editable.toString().trim(), 1, 20, matchId, level);
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

    public void getAllPaints(int matchId, int level) {
        pPaints.onGetAllPaints(searchView.getText().toString(), 1, 20, matchId, level);

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
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {

        progressBarLoading.setVisibility(View.GONE);
        mResponseGetAllPaints = responseGetAllPaints;
        if (mListener != null) {
            mListener.onSetResponseAllPaints(mResponseGetAllPaints);
        }
        recyclerViewAllPaints.getAdapter().notifyDataSetChanged();
        emptyViewAllPaints.setVisibility(recyclerViewAllPaints.getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);

        //  showIntro();

        scrollListener.resetState();

        progressBarLoading.setVisibility(View.GONE);

    }

    @Override
    public void onFailedGetAllPaints(int errorCode, String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        progressBarLoading.setVisibility(View.GONE);
        emptyViewAllPaints.setVisibility(recyclerViewAllPaints.getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);

    }

    @Override
    public void onSelectPaint(LeaderModel paintModel) {
        if (mListener != null) {
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
            }

            @Override
            public void onNegativeClicked() {
                sendPosition = -1;

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز دهی");
        dialog.show();
    }

    @Override
    public void onFailedSendScore(int errorCode, String errorMessage) {
        progressDialog.cancel();
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage(errorMessage);
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });

        dialog.setAcceptButtonMessage(getContext().getString(R.string.confirm));
        dialog.setTitle("امتیاز دهی");
        dialog.show();
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
                setUserInfo();
                if (mListener != null) {
                    mListener.onRefreshUserData();
                }
                if (sendPosition != -1) {
                    pPaints.onSendScore(sendPosition);
                }
            } else {
                sendPosition = -1;

            }
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSetResponseAllPaints(ResponseGetAllPaints responseGetAllPaints);

        void onSelectPaint(LeaderModel paintModel);

        void onRefreshUserData();
        void onBackPressed();
    }

    private void showIntro() {
      /*  final View v = view.findViewById(R.id.viewStep13_1);
        final View v_2 = view.findViewById(R.id.viewStep13);


        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /////////////////////
                FancyShowCaseView fancyShowCaseView= Intro.addIntroTo(getActivity(), v, IntroEnum.getLayoutId(12), IntroPosition.BOTTOM, IntroEnum.getSoundId(12), IntroEnum.getShareId(12),null,null);
                FancyShowCaseView fancyShowCaseView_2= Intro.addIntroTo(getActivity(),
                        v_2, IntroEnum.getLayoutId(13),
                        IntroPosition.TOP,
                        IntroEnum.getSoundId(13),
                        IntroEnum.getShareId(13),
                        null, null, new OnViewInflateListener() {
                            @Override
                            public void onViewInflated(@NonNull View viewIn) {
                                ImageView imageView=viewIn.findViewById(R.id.img_outline_template);
                                ImageView userImage=viewIn.findViewById(R.id.img_user);
                                TextView userName=viewIn.findViewById(R.id.text_user_name);
                                TextView code=viewIn.findViewById(R.id.text_image_code);

                                if (pPaints.getFirstPaintModel()!=null) {

                                    PaintModel paintModel=pPaints.getFirstPaintModel();


                                    if (paintModel.getUser().getImageUrl()!=null && !paintModel.getUser().getImageUrl().isEmpty()) {
                                        Picasso.get().load(paintModel.getUser().getImageUrl()).into(userImage);
                                    }

                                    userName.setText(paintModel.getUser().getFirstName()+" "+paintModel.getUser().getLastName());
                                    code.setText(getContext().getString(R.string.image_code)+" "+paintModel.getCode());

                                    //Picasso.get().invalidate(paintModel.getUrl());
                                    Picasso
                                            .get()
                                            .load(paintModel.getUrl())
                                            .resize(Value.dp(200),0)
                                            .onlyScaleDown()
                                            .into(imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {

                                                    Log.d("TAG", "onError fancyShowCaseView2: ");
                                                    e.printStackTrace();
                                                }
                                            });
                                }
                            }
                        });

                FancyShowCaseQueue fancyShowCaseQueue=new FancyShowCaseQueue();
                fancyShowCaseQueue.add(fancyShowCaseView);

                if (pPaints.getArrSizeAllPaints()>0) {
                    fancyShowCaseQueue.add(fancyShowCaseView_2);

                }
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
*/

    }
}
