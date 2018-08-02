package psb.com.kidpaint.myMessages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.myMessages.adapter.ViewHolder_Message;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.webApi.chat.Get.model.Extra;

public class PMessages implements IPMessages  {
    private Context context;
    private IVMessages ivMessages;
    private MMessages mMessage;

    public PMessages(IVMessages ivMessages) {
        this.ivMessages = ivMessages;
        this.context=ivMessages.getContext();
        this.mMessage=new MMessages(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getMessageFromServer(int loadMode) {
       ivMessages.startGetMessageFromServer(loadMode);
       mMessage.getMessageFromServer();
    }

    @Override
    public void onSuccessGetMessageFromServer() {
        ivMessages.onSuccessGetMessageFromServer();

    }

    @Override
    public void onFailedGetMessageFromServer(int errorCode,String errorMessage) {
        ivMessages.onFailedGetMessageFromServer(errorCode, errorMessage);

    }

    @Override
    public void getMessageFromDb() {
        ivMessages.startGetMessageFromDb();
        mMessage.getMessageFromDb();

    }

    @Override
    public void onSuccessGetMessageFromDb() {
        ivMessages.onSuccessGetMessageFromDb();

    }

    @Override
    public void onFailedGetMessageFromDb(int errorCode,String errorMessage) {
        ivMessages.onFailedGetMessageFromDb(errorCode, errorMessage);

    }

    @Override
    public void sendMessage(String text) {
     ivMessages.startSendMessage();
     mMessage.sendMessage(text);
    }

    @Override
    public void onSuccessSendMessage(int position) {
       ivMessages.onSuccessSendMessage(position);
    }

    @Override
    public void onFailedSendMessage(int errorCode,String errorMessage,int position) {
    ivMessages.onFailedSendMessage(errorCode, errorMessage,position);
    }

    @Override
    public void onBindView_Message(final ViewHolder_Message holder, final int position) {

        final Extra chat=mMessage.getPositionAtMessage(position);


            holder.main_text.setText(chat.getDescription());
            holder.time.setText(Utils.getIraninTimeCommentSlash(chat.getCreateDate()));

            //  set profile image
            if ("".equals(chat.getUsername()) || mMessage.getMobileNumber().equals(chat.getUsername())) {// user
                Picasso.get().load("".equals(mMessage.getUserImage()) ? "avatar" : mMessage.getUserImage()).placeholder(R.drawable.user_profile).error(R.drawable.user_profile).into(holder.icon, new Callback() {
                    @Override
                    public void onSuccess() {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getContext().getResources(),
                                Bitmap.createScaledBitmap(((BitmapDrawable) holder.icon.getDrawable()).getBitmap(), 100, 100, false));
                        drawable.setCircular(true);
                        holder.icon.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(Exception e) {
                          holder.icon.setImageResource(R.drawable.user_profile);
                    }
                });
            } else {// admin
                Picasso.get().load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.icon);


            }

            //  set content image
            if (holder.content_image != null) {
                if ("".equals(chat.getImageUrl()) || null == chat.getImageUrl()) {
                    holder.content_image.setVisibility(View.GONE);
                } else {
                    holder.content_image.setVisibility(View.VISIBLE);
                    Picasso.get().load(chat.getImageUrl()).placeholder(R.drawable.user_empty_gray).into(holder.content_image);

                }
            }

            //
            switch (chat.getStatus()) {
                case "success":
                    if (holder.delivery != null) {
                        if ("".equals(chat.getUsername()) || mMessage.getUserName().equals(chat.getUsername())) {// user
                            holder.mainRel.setBackgroundResource(R.drawable.bgr_chat_user);
                            holder.delivery.setImageResource(R.drawable.icon_tick);

                        }else {// admin
                            holder.mainRel.setBackgroundResource(R.drawable.bgr_chat_admin);
                            holder.delivery.setImageResource(R.drawable.icon_tick);

                            }
                    }
                    break;
                case "pending":
                    holder.mainRel.setBackgroundResource(R.drawable.bgr_chat_user);
                    holder.delivery.setImageResource(R.drawable.icon_clock);
                    break;
                case "failed":
                    holder.mainRel.setBackgroundResource(R.drawable.bgr_chat_user_error);
                    holder.delivery.setImageResource(R.drawable.icon_warning);
                    holder.time.setTextColor(Color.parseColor("#FF495264"));
                    holder.time.setText("پیغام ارسال نشد،برای ارسال مجدد روی پیغام کلیک کنید.");
                    holder.convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mMessage.deleteMessage(chat.getDbId(), position);
                            ivMessages.setSendMessage(chat.getDescription());
                        }
                    });
                    break;
            }


        }

    @Override
    public boolean userIsRegistered() {
        return mMessage.userIsRegistered();
    }

    @Override
    public int getArrSize() {
        return mMessage.getArrSize();
    }

    @Override
    public int getRowType(int position) {
        return mMessage.getRowType(position);
    }

    @Override
    public void onSuccessDeleteMessage(int position) {
        ivMessages.onSuccessDeleteMessage(position);
    }
}
