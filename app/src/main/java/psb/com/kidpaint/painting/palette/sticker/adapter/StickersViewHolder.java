package psb.com.kidpaint.painting.palette.sticker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import psb.com.kidpaint.R;

public class StickersViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewStickers;
    public ProgressBar progressBar;

    public StickersViewHolder(View itemView) {
        super(itemView);
        imageViewStickers=itemView.findViewById(R.id.img_stickers);
        progressBar=itemView.findViewById(R.id.prg);

    }
}
