package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;

public class M_Stickers implements IM_Stickers {

    private Context context;
    private IP_Stickers ipStickers;
    private TblCategory tblCategory;
    private TblStickers tblStickers;
    private List<Sticker> stickerList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();

    public M_Stickers(IP_Stickers ipStickers){
        this.context = ipStickers.getContext();
        this.ipStickers = ipStickers;
        tblCategory = new TblCategory(getContext());
        tblStickers = new TblStickers(getContext());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        stickerList.clear();
        stickerList = tblStickers.getAllStickers();
        categoryList.clear();
        categoryList = tblCategory.getAllCategory();
    }

    public int getCategorysSize(){
        return categoryList.size();
    }

    public int getStickersSize(){
        return stickerList.size();
    }

    public Sticker getStickerAtPos(int pos){
        return stickerList.get(pos);
    }

    public Category getCategoryAtPos(int pos){
        return categoryList.get(pos);
    }
}
