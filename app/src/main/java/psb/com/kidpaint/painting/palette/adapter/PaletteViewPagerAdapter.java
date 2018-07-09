package psb.com.kidpaint.painting.palette.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import psb.com.kidpaint.painting.palette.color.PaletteFragment;
import psb.com.kidpaint.painting.palette.sticker.StickerFragment;


public class PaletteViewPagerAdapter extends FragmentStatePagerAdapter {
    public PaletteViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new PaletteFragment().newInstance();
        }else{
            return new StickerFragment().newInstance();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
