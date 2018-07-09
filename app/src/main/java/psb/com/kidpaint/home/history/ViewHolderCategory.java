package psb.com.kidpaint.home.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import psb.com.kidpaint.R;


/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

public class ViewHolderCategory extends RecyclerView.ViewHolder {

    public TextView textViewCatName;
    public ImageView imageViewCatImg;
    public View parent;
    public ViewHolderCategory(View itemView) {
        super(itemView);
        parent=itemView;
        textViewCatName = itemView.findViewById(R.id.text_category);
        imageViewCatImg = itemView.findViewById(R.id.img_category);
    }
}