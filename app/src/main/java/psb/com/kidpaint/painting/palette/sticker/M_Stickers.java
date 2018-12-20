package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.UserProfile;
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
//    private List<Sticker> stickerList = new ArrayList<>();
    private List<Sticker> stickerListCategory = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private UserProfile userProfile;

    public M_Stickers(IP_Stickers ipStickers) {
        this.context = ipStickers.getContext();
        this.ipStickers = ipStickers;
        this.userProfile=new UserProfile(getContext());

        tblCategory = new TblCategory(getContext());
        tblStickers = new TblStickers(getContext());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {

        categoryList.clear();
        categoryList = tblCategory.getAllCategory();
        if (categoryList.size() > 0) {
            categoryList.get(0).setSelected(true);
        }

        addObjectTofirstCatList();

        stickerListCategory.clear();
        stickerListCategory=new ArrayList<>(tblStickers.getStickersByCatId(categoryList.get(1).getId()));
        ipStickers.showStickers(1);
    }


    private void addObjectTofirstCatList(){
        Category category = new Category();
        category.setId(-1);
        category.setName("");
        category.setOrder(0);
        category.setParentId(-1);
        category.setImageUrl("");
        category.setPrice(0);
        category.setSongUrl("");
        categoryList.add(0,category);
    }

    @Override
    public void onCatSelected(int id,int catPosition) {

        int lastSelectPos=-1;
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).isSelected()) {
                categoryList.get(i).setSelected(false);
                lastSelectPos=i;
            }
        }

        if (lastSelectPos!=-1) {
            ipStickers.unSelectCat(lastSelectPos);
        }



        categoryList.get(catPosition).setSelected(true);
        stickerListCategory.clear();
        stickerListCategory=new ArrayList<>(tblStickers.getStickersByCatId(id));

        ipStickers.showStickers(catPosition);
    }

    @Override
    public void getStickersFromServer() {

        categoryList.clear();
        String fromDate = tblStickers.getStickerLastUpdateTime();
        new psb.com.kidpaint.webApi.Category.Category().getCategory(new iGetCategory.iResult() {
            @Override
            public void onSuccessGetCategory(ResponseStickers responseStickers) {
                categoryList = responseStickers.getExtra();
                addCategoryToDataBase(categoryList);
                addStickersToDataBase(categoryList);
                ipStickers.getStickersSuccessFromServer();

            }

            @Override
            public void onFailedGetCategory(int errorId, String ErrorMessage) {
                ipStickers.getStickersFailedFromServer(errorId, ErrorMessage);
            }
        }).doGetCategory(fromDate);
    }

    private void addStickersToDataBase(List<Category> responseStickers) {

        ArrayList<Sticker> stickerList=new ArrayList<>();

        for (int i = 0; i < responseStickers.size(); i++) {
            for (int j = 0; j < responseStickers.get(i).getStickers().size(); j++) {
                stickerList.add(responseStickers.get(i).getStickers().get(j));
            }
        }

        tblStickers.insert(stickerList);

    }

    private void addCategoryToDataBase(List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList) {

        tblCategory.insert(categoryList);


    }


    public int getCategorysSize() {
        return categoryList.size();
    }

    public int getStickersSize() {
        return stickerListCategory.size();
    }

    public Sticker getStickerAtPos(int pos) {
        return stickerListCategory.get(pos);
    }


    public Category getCategoryAtPos(int pos) {
        return categoryList.get(pos);
    }

    @Override
    public boolean userIsRegistered() {
        return userProfile.get_KEY_PHONE_NUMBER("").isEmpty() ? false : true;
    }
}
