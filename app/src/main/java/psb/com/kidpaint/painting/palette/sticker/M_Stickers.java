package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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
    private List<Sticker> stickerListCategory = new ArrayList<>();
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

        categoryList.get(0).setSelected(true);

        stickerListCategory.clear();

        for (int i = 0; i < stickerList.size(); i++) {
            if (categoryList.get(0).getId() == stickerList.get(i).getCategoryId()){
                stickerListCategory.add(stickerList.get(i));
            }
        }
        ipStickers.showStickers();
    }

    @Override
    public void onCatSelected(int id) {

        for (int i = 0; i < categoryList.size(); i++) {
            categoryList.get(i).setSelected(false);
        }

        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == id){
                categoryList.get(i).setSelected(true);
            }
        }

        stickerListCategory.clear();
        for (int i = 0; i < stickerList.size(); i++) {
            if (stickerList.get(i).getCategoryId() == id){
                stickerListCategory.add(stickerList.get(i));
            }
        }
        ipStickers.showStickers();
    }

    public int getCategorysSize(){
        return categoryList.size();
    }

    public int getStickersSize(){
        return stickerListCategory.size();
    }

    public Sticker getStickerAtPos(int pos){
        return stickerListCategory.get(pos);
    }

    public Category getCategoryAtPos(int pos){
        return categoryList.get(pos);
    }
}
