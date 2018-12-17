package psb.com.kidpaint.utils.database;

import android.content.Context;

import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.utils.database.TblMessage.TblMessage;


public interface iDatabase {

    TblStickers getStickers(Context context);

    TblCategory getCategory(Context context);

    TblMessage tblMessage(Context context);

    void deleteTables(Context context, String[] tables);

}
