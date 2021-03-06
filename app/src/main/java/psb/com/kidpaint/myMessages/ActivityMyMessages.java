package psb.com.kidpaint.myMessages;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.leolin.shortcutbadger.ShortcutBadger;
import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.home.HomeActivity_2;
import psb.com.kidpaint.myMessages.adapter.Adapter_Message;
import psb.com.kidpaint.user.register.ActivityRegisterUser;
import psb.com.kidpaint.utils.NotificationCreator;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.customView.BaseActivity;
import psb.com.kidpaint.utils.customView.ProgressView;
import psb.com.kidpaint.utils.customView.dialog.CDialog;
import psb.com.kidpaint.utils.customView.dialog.MessageDialog;

public class ActivityMyMessages extends BaseActivity implements IVMessages {
    public static int CODE_REGISTER = 107;

    private ProgressView progressView;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button send;
    private TextView emptyView;

    private PMessages pMessages;
    private Adapter_Message adapter_message;

    private int mLoadMode=0;
    private int mLoadModeDb=0;
    private Button back;

    @Override
    public void onBackPressed() {
        if(!App.isHomeActivityStarted){
            startActivity(new Intent(this,HomeActivity_2.class));
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

//        clear notification and badges
        NotificationCreator.clearNotifications(this);
        ShortcutBadger.removeCount(this);


        pMessages=new PMessages(this);
        initView();
        pMessages.getMessageFromServer(0);

        createHelperWnd();
    }

    void initView(){
        progressView=findViewById(R.id.progressView);
        //swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerView=findViewById(R.id.recyclerView);
        editText=findViewById(R.id.chat_messages_txt);
        send=findViewById(R.id.btn_send);
        emptyView=findViewById(R.id.emptyView);

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setReverseLayout(true);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter_message);
        emptyView.setVisibility(adapter_message.getItemCount()>0? View.GONE:View.VISIBLE);
//
        IntentFilter iff = new IntentFilter(Utils.FCM_BROADCAST_CHAT);
        LocalBroadcastManager.getInstance(ActivityMyMessages.this).registerReceiver(chatBroadcastReceiver, iff);
        Utils.activitymyMessageIsRunning=true;

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
      pMessages.getMessageFromDb(mLoadModeDb);
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
        }

        pMessages.getMessageFromDb(mLoadModeDb);


    }

    @Override
    public void startGetMessageFromDb(int loadMode) {
        if (loadMode==0) {
            progressView.setVisibility(View.VISIBLE);
            progressView.showProgress();
        }
    }


    @Override
    public void onSuccessGetMessageFromDb() {
        mLoadModeDb=0;
        if (adapter_message==null) {
            setAdapter_message();
        }else{
            recyclerView.getAdapter().notifyDataSetChanged();
            emptyView.setVisibility(adapter_message.getItemCount()>0? View.GONE:View.VISIBLE);

        }

      progressView.setVisibility(View.GONE);

      if(pMessages.getFirstUnreadMessagePosition()<=(adapter_message.getItemCount()-1)){
          recyclerView.scrollToPosition(pMessages.getFirstUnreadMessagePosition());

      }else {
          recyclerView.scrollToPosition((adapter_message.getItemCount()-1));

      }
         pMessages.setAllMessageToRead();
        if (!editText.getText().toString().trim().isEmpty()) {
            send.performClick();
        }
    }

    @Override
    public void onFailedGetMessageFromDb(int errorCode,String errorMessage) {
        progressView.showError(errorMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pMessages.getMessageFromDb(mLoadModeDb);
            }
        });
    }

    @Override
    public void startSendMessage() {

    }

    @Override
    public void onSuccessSendMessage(int position,boolean sendToServer) {
        emptyView.setVisibility(adapter_message.getItemCount()>0? View.GONE:View.VISIBLE);

        if (position!=-1) {
            recyclerView.getAdapter().notifyItemChanged(position);
        }
        if (sendToServer) {
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
        }else{
            if (position!=-1) {
                recyclerView.scrollToPosition(position);
            }
        }
    }

    @Override
    public void onFailedSendMessage(int errorCode, String errorMessage, int position) {
        if (position!=-1) {
            recyclerView.getAdapter().notifyItemChanged(position);
        }
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

    @Override
    public void startActionView(String url) {
       Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        //intent.setData(Uri.parse(url));
        startActivity(intent);
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

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(ActivityMyMessages.this).unregisterReceiver(chatBroadcastReceiver);
        Utils.activitymyMessageIsRunning=false;

        super.onDestroy();
    }

    private BroadcastReceiver chatBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("Chat")) {
                mLoadModeDb=2;
                pMessages.getMessageFromServer(2);

            }
        }
    };


}
