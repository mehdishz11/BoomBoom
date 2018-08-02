package psb.com.kidpaint.myMessages.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import psb.com.kidpaint.R;

public class ViewHolder_Message extends RecyclerView.ViewHolder {

    public View convertView;
    public TextView main_text;
    public TextView time;
    public ImageView delivery,icon,content_image;
    public LinearLayout mainRel;

    public ViewHolder_Message(View itemView) {
        super(itemView);

        main_text = itemView.findViewById(R.id.row_msg_text);
        time=itemView.findViewById(R.id.row_msg_send_mode_desc);
        mainRel= itemView.findViewById(R.id.row_msg_user_main_rel);
        icon=itemView.findViewById(R.id.row_msg_user_icon);
        content_image=itemView.findViewById(R.id.row_msg_img);

    }
}
