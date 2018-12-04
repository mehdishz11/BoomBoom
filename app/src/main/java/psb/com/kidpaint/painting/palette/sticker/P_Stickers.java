package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;
import psb.com.kidpaint.painting.palette.sticker.adapter.CategoryViewHolder;
import psb.com.kidpaint.painting.palette.sticker.adapter.StickersViewHolder;
import psb.com.kidpaint.utils.musicHelper.SingleMusicPlayer;
import psb.com.kidpaint.utils.selector.NetworkImageView;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;

public class P_Stickers implements IP_Stickers {

    private Context context;
    private IV_Stickers ivStickers;
    private M_Stickers mStickers;

    public P_Stickers(IV_Stickers ivStickers){
        this.context = ivStickers.getContext();
        this.ivStickers = ivStickers;
        mStickers = new M_Stickers(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        mStickers.getStickers();
    }

    @Override
    public void getStickersSuccess() {
        ivStickers.getStickersSuccess();
    }

    @Override
    public void getStickersFailed() {
        ivStickers.getStickersFailed();
    }

    @Override
    public void showStickers(int catPosition) {
        ivStickers.showStickers(catPosition);
    }

    @Override
    public void unSelectCat(int position) {
        ivStickers.unSelectCat(position);
    }

    @Override
    public void getStickersFromServer() {
       ivStickers.startGetStickersFromServer();
       mStickers.getStickersFromServer();
    }

    @Override
    public void getStickersSuccessFromServer() {
       ivStickers.getStickersSuccessFromServer();
    }

    @Override
    public void getStickersFailedFromServer(int errorId, String ErrorMessage) {
       ivStickers.getStickersFailedFromServer(errorId, ErrorMessage);
    }

    public int getStickersSize(){
        return mStickers.getStickersSize();
    }

    public int getCategorysSize(){
        return mStickers.getCategorysSize();
    }


    public void onBindViewHolderStickers(final StickersViewHolder holder, final int position) {
        final Sticker sticker = mStickers.getStickerAtPos(position);

        if (sticker.getImageUrl() != null && !sticker.getImageUrl().isEmpty()) {
    /*        Picasso.get().load(sticker.getImageUrl()).into(holder.imageViewStickers, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.imageViewStickers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ivStickers.onStickerSelected(
                                    ((BitmapDrawable)holder.imageViewStickers.getDrawable()).getBitmap(),
                                    sticker.getPrice(),
                                    sticker.getSongUrl()
                            );
                        }
                    });
                }

                @Override
                public void onError(Exception e) {

                    holder.progressBar.setVisibility(View.GONE);
                }
            });*/

            Log.d("TAG", "onBindViewHolderStickers: "+sticker.getPrice()+" pos:"+position);


            if (sticker.getDrawable()!=null) {
                holder.imageViewStickers.loadDrawable(sticker.getDrawable(), new NetworkImageView.Callback() {
                    @Override
                    public void onSuccess(Drawable result) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageViewStickers.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivStickers.onStickerSelected(
                                        ((BitmapDrawable)sticker.getDrawable()).getBitmap(),
                                        sticker.getPrice(),
                                        sticker.getSongUrl()
                                );
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);

                    }
                });
            }else {

                holder.imageViewStickers.load(sticker.getImageUrl(), new NetworkImageView.Callback() {
                    @Override
                    public void onSuccess(Drawable result) {
                        holder.progressBar.setVisibility(View.GONE);
                        mStickers.setStickerDrawable(position,result);
                        holder.imageViewStickers.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ivStickers.onStickerSelected(

                                        ((BitmapDrawable)sticker.getDrawable()).getBitmap(),
                                        sticker.getPrice(),
                                        sticker.getSongUrl()
                                );
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            if (sticker.getPrice()>0&& !mStickers.userIsRegistered()){
                //   holder.imageBgr.setBackgroundResource(R.drawable.circle_border_white);
                holder.imageViewStickers.setSelected(false);
                holder.locked.setVisibility(View.VISIBLE);
            } else {
                //   holder.imageBgr.setBackgroundResource(0);
                holder.imageViewStickers.setSelected(true);
                holder.locked.setVisibility(View.GONE);

            }

        }else{
            Log.d(App.TAG, "onBindViewHolderStickers: "+sticker.getImageUrl());
        }

    }

    public void onBindViewHolderCategory(final CategoryViewHolder holder, final int position) {
        final Category category = mStickers.getCategoryAtPos(position);


        if (category.getDrawable()!=null) {
            holder.imageViewCat.loadDrawable(category.getDrawable(), new NetworkImageView.Callback() {
                @Override
                public void onSuccess(Drawable result) {
                    holder.progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onError(Exception e) {
                    holder.progressBar.setVisibility(View.GONE);

                }
            });
        }else {

            holder.imageViewCat.load(category.getImageUrl(), new NetworkImageView.Callback() {
                @Override
                public void onSuccess(Drawable result) {
                    holder.progressBar.setVisibility(View.GONE);
                    mStickers.setCategoryDrawable(position,result);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }


        if (category.isSelected()){
         //   holder.imageBgr.setBackgroundResource(R.drawable.circle_border_white);


          holder.imageViewCat.setSelected(true);
        } else {
         //   holder.imageBgr.setBackgroundResource(0);
            holder.imageViewCat.setSelected(false);

        }

        holder.imageViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickers.onCatSelected(category.getId(),position);
             //   Log.d("TAG", "onClick: "+category.getSongUrl());
                new SingleMusicPlayer().playSingleMedia(category.getSongUrl());

            }
        });
    }
}
