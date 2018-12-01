package psb.com.kidpaint.painting.palette.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import psb.com.kidpaint.painting.palette.color.PaletteFragment;
import psb.com.kidpaint.painting.palette.sticker.StickerFragment;


public class PaletteViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> arrFragment = new ArrayList<>();
    PaletteFragment paletteFragment;
    StickerFragment stickerFragment;

    public PaletteViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (paletteFragment == null) {
                paletteFragment = new PaletteFragment().newInstance();
            }
            return paletteFragment;
        } else if (position == 1) {
            if (stickerFragment == null) {
                stickerFragment = new StickerFragment().newInstance();
            }
            return stickerFragment;
        }

        return null;
    }
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
