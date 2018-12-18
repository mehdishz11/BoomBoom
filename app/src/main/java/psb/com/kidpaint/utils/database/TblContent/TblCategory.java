package psb.com.kidpaint.utils.database.TblContent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.database.Sql;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Category;


public class TblCategory {

    Context mContext;

    public TblCategory(Context c) {
        this.mContext = c;
    }

    public void insert(List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList) {

        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();

        for (Category category : categoryList
                ) {

            cv.put("id", category.getId());
            cv.put("name", category.getName());
            cv.put("sort", category.getOrder());
            cv.put("parentId", category.getParentId());
            cv.put("imageUrl", category.getImageUrl());
            cv.put("price", category.getPrice());
            cv.put("songUrl", category.getSongUrl());

            int update = db.update("tbl_Category", cv, "id=?", new String[]{category.getId() + ""});
            if (update == 0) {
                db.insert("tbl_Category", null, cv);
            }

        }

        db.close();
        sql.close();
        return;
    }

    public Category getCategoryById(int id) {
        // Log.d(TAG, "getSubCategoryList: "+storeId+" "+parentId);
        Category category = new Category();
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = new String[]{"id", "name", "sort", "parentId", "imageUrl", "price", "songUrl"};
        Cursor c = db.query("tbl_Category", columns, "id=?", new String[]{id + ""}, null, null, null);
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                category.setId(c.getInt(0));
                category.setName(c.getString(1));
                category.setOrder(c.getInt(2));
                category.setParentId(c.getInt(3));
                category.setImageUrl(c.getString(4));
                category.setPrice(c.getInt(5));
                category.setSongUrl(c.getString(6));
            }
        }
        db.close();
        sql.close();
        return category;
    }

    public List<Category> getAllCategory() {
        // Log.d(TAG, "getSubCategoryList: "+storeId+" "+parentId);
        List<Category> categoryList = new ArrayList<>();
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = new String[]{"id", "name", "sort", "parentId", "imageUrl", "price", "songUrl"};
        Cursor c = db.query("tbl_Category", columns, null, null, null, null, "price ASC");
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    Category category = new Category();
                    category.setId(c.getInt(0));
                    category.setName(c.getString(1));
                    category.setOrder(c.getInt(2));
                    category.setParentId(c.getInt(3));
                    category.setImageUrl(c.getString(4));
                    category.setPrice(c.getInt(5));
                    category.setSongUrl(c.getString(6));
                    categoryList.add(category);
                    c.moveToNext();
                }
            }
        }
        db.close();
        sql.close();
        Log.d("sizeis", "getAllStickers: " + categoryList.size());
        return categoryList;
    }

}
