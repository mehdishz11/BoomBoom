package psb.com.kidpaint.utils.database;

import android.content.Context;

import java.util.List;

import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;


public interface iDatabase {

    TblStickers getStickers(Context context);
    void deleteSticker(Context context, String[] tables);

    TblCategory getCategory(Context context);
    void deleteCategory(Context context, String[] tables);
}
