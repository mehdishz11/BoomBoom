package psb.com.kidpaint.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.utils.database.TblMessage.TblMessage;

public class Database implements iDatabase {

    @Override
    public TblStickers getStickers(Context context) {
        return new TblStickers(context);
    }



    @Override
    public TblCategory getCategory(Context context) {
        return new TblCategory(context);
    }

    @Override
    public void deleteTables(Context context, String[] tables) {
        Sql sql = new Sql(context);
        SQLiteDatabase db = sql.getWritableDatabase();
        for (int i = 0; i < tables.length; i++) {
            db.delete(tables[i], null, null);
            db.execSQL("delete from sqlite_sequence where name=" + "'" + tables[i] + "'");
        }
        db.close();
        sql.close();
    }

    @Override
    public TblMessage tblMessage(Context context) {
        return new TblMessage(context);
    }
}
