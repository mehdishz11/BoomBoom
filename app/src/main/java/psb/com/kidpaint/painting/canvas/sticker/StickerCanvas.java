package psb.com.kidpaint.painting.canvas.sticker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.customView.stickerview.StickerView;
import psb.com.kidpaint.utils.musicHelper.SingleMusicPlayer;

public class StickerCanvas extends FrameLayout implements
        StickerView.onRemoved, StickerView.onVisibilityChange {

    private OnStickerListener onStickerListener;

    private ArrayList<StickerView> arrStickers = new ArrayList<>();


    public StickerCanvas(@NonNull Context context) {
        super(context);
        init();
    }

    public StickerCanvas(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickerCanvas(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideShowController(false);
            }
        });

        setClickable(true);
    }

    public void setOnStickerListener(OnStickerListener onStickerListener) {
        this.onStickerListener = onStickerListener;
    }

    public void addSticker(StickerView stickerView) {
        arrStickers.add(stickerView);
        stickerView.setOnRemoved(this);
        stickerView.setOnVisibilityChange(this);
        addView(stickerView);
    }

    public void hideShowController(boolean isShow) {
        if (isShow && arrStickers.size() > 0) {
            arrStickers.get(arrStickers.size() - 1).setOnVisibilityChange(null);
            arrStickers.get(arrStickers.size() - 1).setControlsVisibility(true);
            arrStickers.get(arrStickers.size() - 1).setOnVisibilityChange(this);
        } else if (arrStickers.size() > 0) {
            for (StickerView sticker : arrStickers) {
                sticker.setOnVisibilityChange(null);
                sticker.setControlsVisibility(false);
                sticker.setOnVisibilityChange(this);
            }
        }

    }

    public void removeAllStickers() {
        arrStickers.clear();
        removeAllViews();
    }

    public void enableDisableCanvas(boolean enable){
        this.setEnabled(enable);
        for (StickerView stickerView :
                arrStickers) {
            stickerView.setEnabled(enable);
            stickerView.setEnabled(enable);
        }
        setClickable(enable);
        //enableDisableViewGroup(this,enable);
    }

    private void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
//            view.setEnabled(enabled);
            view.setClickable(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // StickerView implements
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onControllerVisibilityChange(int id, boolean isShow) {
        if (!isShow) return;
        for (StickerView sticker : arrStickers) {
            sticker.setOnVisibilityChange(null);
            if (sticker.getId() != id) {
                sticker.setControlsVisibility(false);
            }else if(isShow){
                    new SingleMusicPlayer().playSingleMedia(sticker.getStickerSound());

            }

            sticker.setOnVisibilityChange(this);
        }


    }

    @Override
    public void onRemoved(int id) {
        for (int i = 0; i < arrStickers.size(); i++) {
            if (arrStickers.get(i).getId() == id) {
                if (onStickerListener != null) {
                    onStickerListener.onStickerRemoved(arrStickers.get(i));
                }
                arrStickers.remove(i);
                break;
            }
        }
    }


    public interface OnStickerListener{
        void onStickerRemoved(StickerView stickerView);
    }

}
