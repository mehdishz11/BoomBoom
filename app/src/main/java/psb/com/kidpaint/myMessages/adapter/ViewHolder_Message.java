package psb.com.kidpaint.myMessages.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import psb.com.kidpaint.R;

public class ViewHolder_Message extends RecyclerView.ViewHolder {

    public View convertView;
    public TextView main_text,title;
    public TextView time;
    public ImageView delivery,icon,content_image,image_bg;
//    public LinearLayout mainRel;
    public ScrollView mainRel;

    public ViewHolder_Message(View itemView) {
        super(itemView);
       convertView=itemView;
        main_text = itemView.findViewById(R.id.row_msg_text);
        title = itemView.findViewById(R.id.row_msg_title);
        time=itemView.findViewById(R.id.row_msg_send_mode_desc);
        mainRel= itemView.findViewById(R.id.row_msg_user_main_rel);

        icon=itemView.findViewById(R.id.row_msg_user_icon);
        content_image=itemView.findViewById(R.id.row_msg_img);

    }
}
