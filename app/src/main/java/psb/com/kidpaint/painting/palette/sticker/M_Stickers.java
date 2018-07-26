package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;
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
        if (categoryList.size()>0) {
            categoryList.get(0).setSelected(true);
        }

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

    @Override
    public void getStickersFromServer() {
        stickerList.clear();
        categoryList.clear();
        String fromDate=tblStickers.getStickerLastUpdateTime();
        new psb.com.kidpaint.webApi.Category.Category().getCategory(new iGetCategory.iResult() {
            @Override
            public void onSuccessGetCategory(ResponseStickers responseStickers) {
                categoryList = responseStickers.getExtra();
                addCategoryToDataBase(categoryList);

            }

            @Override
            public void onFailedGetCategory(int errorId, String ErrorMessage) {
                ipStickers.getStickersFailedFromServer(errorId, ErrorMessage);
            }
        }).doGetCategory(fromDate);
    }

    private void addStickersToDataBase(List<Category> responseStickers){

        for (int i = 0; i < responseStickers.size(); i++) {
            for (int j = 0; j < responseStickers.get(i).getStickers().size(); j++) {
                stickerList.add(responseStickers.get(i).getStickers().get(j));
            }
        }


        for (int i = 0; i < stickerList.size(); i++) {
            tblStickers.insert(stickerList.get(i));
        }

        ipStickers.getStickersSuccessFromServer();

    }

    private void addCategoryToDataBase(List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList){
        for (int i = 0; i < categoryList.size(); i++) {
            tblCategory.insert(categoryList.get(i));
        }

        addStickersToDataBase(categoryList);

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
