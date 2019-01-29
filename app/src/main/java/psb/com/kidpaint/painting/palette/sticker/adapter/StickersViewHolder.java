package psb.com.kidpaint.painting.palette.sticker.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import psb.com.kidpaint.R;

public class StickersViewHolder extends RecyclerView.ViewHolder {

    public ImageView locked;
    public ImageView imageViewStickers;
    public ProgressBar progressBar;

    public StickersViewHolder(View itemView) {
        super(itemView);
        imageViewStickers=itemView.findViewById(R.id.img_stickers);
        locked=itemView.findViewById(R.id.locked);
        progressBar=itemView.findViewById(R.id.prg);

    }
}
