package psb.com.kidpaint.home.newPaint.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import psb.com.kidpaint.R;
import psb.com.kidpaint.home.newPaint.PaintingOutlinEnum;
import psb.com.kidpaint.utils.Value;

public class OutlineAdapter extends RecyclerView.Adapter<OutlineViewHolder> {

    private OnOutlineSelected onOutlineSelected;
    private Context context;

    public OutlineAdapter(Context context, OnOutlineSelected onOutlineSelected) {
        this.context = context;
        this.onOutlineSelected = onOutlineSelected;
    }

    @NonNull
    @Override
    public OutlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_outline_template, parent, false);
        OutlineViewHolder outlineViewHolder = new OutlineViewHolder(view);
        return outlineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OutlineViewHolder holder, final int position) {
        Picasso
                .get()
                .load(PaintingOutlinEnum.values()[position].getResIdSmall())
                .resize(Value.dp(200), Value.dp(200))
                .onlyScaleDown()
                .into(holder.imgOutline);

        if (position % 2 == 0) {
            holder.relParent.setBackgroundResource(R.drawable.bgr_white_1);
            holder.imgMask.setBackgroundResource(R.drawable.mask_bgr_white_1);
        } else {
            holder.relParent.setBackgroundResource(R.drawable.bgr_white_2);
            holder.imgMask.setBackgroundResource(R.drawable.mask_bgr_white_2);
        }


        holder.relParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOutlineSelected != null) {
                    onOutlineSelected.onSelected(PaintingOutlinEnum.values()[position].getResIdBig());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return PaintingOutlinEnum.values().length;
    }


    public interface OnOutlineSelected {
        void onSelected(int resId);
    }

}
