package psb.com.kidpaint.myMessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import psb.com.kidpaint.R;
import psb.com.kidpaint.competition.leaderBoard.FragmentLeaderBoard;
import psb.com.kidpaint.competition.myPaints.FragmentMyPaints;
import psb.com.kidpaint.myMessages.adapter.Adapter_Message;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;

public class ActivityMyMessages extends AppCompatActivity implements IVMessages {
    public static int CODE_REGISTER = 107;

    private ProgressView progressView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageView send;
    private TextView emptyView,back;

    private PMessages pMessages;
    private Adapter_Message adapter_message;

    private int mLoadMode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        pMessages=new PMessages(this);
        initView();
        pMessages.getMessageFromServer(0);
    }

    void initView(){
        progressView=findViewById(R.id.progressView);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerView=findViewById(R.id.recyclerView);
        editText=findViewById(R.id.chat_messages_txt);
        send=findViewById(R.id.btn_send);
        emptyView=findViewById(R.id.emptyView);
        back=findViewById(R.id.icon_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pMessages.getMessageFromServer(1);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pMessages.userIsRegistered()) {

                    if ("".equals(editText.getText().toString())) {
                        Toast.makeText(ActivityMyMessages.this, "متن پیغام خالی است", Toast.LENGTH_SHORT).show();
                    }else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        pMessages.sendMessage(editText.getText().toString().trim());
                        editText.setText("");
                    }
                }else{
                    showUserRegisterDialog();
                }
            }
        });


    }

    void setAdapter_message(){
        adapter_message=new Adapter_Message(pMessages);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter_message);
        emptyView.setVisibility(adapter_message.getItemCount()>0? View.GONE:View.VISIBLE);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startGetMessageFromServer(int loadMode) {
        mLoadMode=loadMode;
        emptyView.setVisibility(View.GONE);

        if (loadMode==0) {
            progressView.setVisibility(View.VISIBLE);
            progressView.showProgress();
        }
    }



    @Override
    public void onSuccessGetMessageFromServer() {
      pMessages.getMessageFromDb();
    }

    @Override
    public void onFailedGetMessageFromServer(int errorCode,String errorMessage) {

        if (mLoadMode==0) {
          /*  progressView.showError(errorMessage, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pMessages.getMessageFromServer(0);
                }
            });*/
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

        pMessages.getMessageFromDb();


    }

    @Override
    public void startGetMessageFromDb() {
        progressView.setVisibility(View.VISIBLE);
        progressView.showProgress();
    }


    @Override
    public void onSuccessGetMessageFromDb() {
      setAdapter_message();
      progressView.setVisibility(View.GONE);
      swipeRefreshLayout.setRefreshing(false);

        if (!editText.getText().toString().trim().isEmpty()) {
            send.performClick();
        }
    }

    @Override
    public void onFailedGetMessageFromDb(int errorCode,String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pMessages.getMessageFromDb();
            }
        });
    }

    @Override
    public void startSendMessage() {

    }

    @Override
    public void onSuccessSendMessage(int position) {
        recyclerView.getAdapter().notifyItemChanged(position);
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("پیغام شما با موفقیت ارسال شد.درسریعترین زمان ممکن پاسخ داده خواهد شد.");
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
        dialog.setAcceptButtonMessage("باشه");
        dialog.setTitle("ارسال پیغام");
        dialog.show();
    }

    @Override
    public void onFailedSendMessage(int errorCode, String errorMessage, int position) {
        recyclerView.getAdapter().notifyItemChanged(position);

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
        dialog.setAcceptButtonMessage("باشه");
        dialog.setTitle("ارسال پیغام");
        dialog.show();
    }

    @Override
    public void setSendMessage(String text) {
       editText.setText(text);
    }

    @Override
    public void onSuccessDeleteMessage(int position) {
        recyclerView.getAdapter().notifyItemRemoved(position);
        recyclerView.getAdapter().notifyItemRangeChanged(position, adapter_message.getItemCount());

    }




    public void showUserRegisterDialog() {
        final MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage("برای ارسال پیغام باید ثبت نام کنید");
        dialog.setOnCLickListener(new CDialog.OnCLickListener() {
            @Override
            public void onPosetiveClicked() {
                dialog.cancel();
                startActivityForResult(new Intent(ActivityMyMessages.this, ActivityRegisterUser.class),CODE_REGISTER);
            }

            @Override
            public void onNegativeClicked() {
                dialog.cancel();

            }
        });
        dialog.setAcceptButtonMessage("باشه");
        dialog.setTitle("ثبت نام");
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult message: " + requestCode);
        if (requestCode == CODE_REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                pMessages.getMessageFromServer(0);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                //finish();
            }
        }
    }

}
