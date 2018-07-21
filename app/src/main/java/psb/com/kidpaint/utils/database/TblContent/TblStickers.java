package psb.com.kidpaint.utils.database.TblContent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.database.Sql;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;


public class TblStickers {

    Context mContext;

    public TblStickers(Context c) {
        this.mContext = c;
    }


    public void insert(Sticker sticker) {

        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id", sticker.getId());
        cv.put("imageUrl", sticker.getImageUrl());
        cv.put("price", sticker.getPrice());
        cv.put("songUrl", sticker.getSongUrl());
        cv.put("categoryId", sticker.getCategoryId());

        int update = db.update("tbl_Stickers", cv, "id=?", new String[]{sticker.getId()+""});
        if (update == 0) {
            db.insert("tbl_Stickers", null, cv);
        }
        db.close();
        sql.close();
    }

    public Sticker getStickersById(int id) {
        Sticker content = new Sticker();
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = new String[]{"id", "imageUrl", "price", "songUrl", "categoryId"};
        Cursor c = db.query("tbl_Stickers", columns, "id=?", new String[]{id+""}, null, null,null);
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                content.setId(c.getInt(0));
                content.setImageUrl(c.getString(1));
                content.setPrice(c.getInt(2));
                content.setSongUrl(c.getString(3));
                content.setCategoryId(c.getInt(4));
            }
        }
        db.close();
        sql.close();
        return content;
    }

    public List<Sticker> getAllStickers() {
        List<Sticker> stickerList = new ArrayList<>();
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = new String[]{"id", "imageUrl", "price", "songUrl", "categoryId"};
        Cursor c = db.query("tbl_Stickers", columns, null, null, null, null,null);
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    Sticker content = new Sticker();
                    content.setId(c.getInt(0));
                    content.setImageUrl(c.getString(1));
                    content.setPrice(c.getInt(2));
                    content.setSongUrl(c.getString(3));
                    content.setCategoryId(c.getInt(4));
                    stickerList.add(content);
                    c.moveToNext();
                }
            }
        }
        db.close();
        sql.close();

        return stickerList;
    }

    public void delete(int id) {
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        db.delete("tbl_Stickers", "id=?", new String[]{id + ""});
        db.close();
        sql.close();
    }
}
