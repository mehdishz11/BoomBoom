package psb.com.kidpaint.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import psb.com.kidpaint.utils.Utils;


public class Sql extends SQLiteOpenHelper {
    //------------------------------------------
    static final String dbName = "Paint";
    //------------------------------------------

    public Sql(Context context) {
        super(context, dbName, null,9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE tbl_Stickers (" +
                "dbid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER ," +
                "imageUrl TEXT ," +
                "price INTEGER ," +
                "songUrl TEXT ," +
                "categoryId INTEGER,"  +
                "deleted INTEGER,"  +
                "updateTime CURRENT_TIMESTAMP," +
                "lastEditDate CURRENT_TIMESTAMP" +
                ")"
        );

        db.execSQL("CREATE TABLE tbl_Category (" +
                "dbid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER ," +
                "name TEXT ," +
                "sort INTEGER ," +
                "parentId INTEGER ," +
                "imageUrl TEXT ," +
                "price INTEGER ," +
                "songUrl TEXT,"   +
                "deleted INTEGER,"  +
                "updateTime CURRENT_TIMESTAMP)"
        );

        db.execSQL("CREATE TABLE tbl_Message (" +
                "dbid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "messageId INTEGER ," +
                "chatId INTEGER ," +
                "title TEXT ," +
                "imageUrl INTEGER ," +
                "body TEXT ," +
                "type TEXT ," +
                "url TEXT,"   +
                "isRead INTEGER,"  +
                "status TEXT ," +
                "sender TEXT ," +
                "insertTime CURRENT_TIMESTAMP)"
        );

        ContentValues cv = new ContentValues();
        cv.put("chatId", 0);
        cv.put("messageId", 0);
        cv.put("title", "پشتیبانی");
        cv.put("imageUrl", "");
        cv.put("body", "هر سوال ، نظر یا پیشنهادی که داشتی اینجا میتونی به ما بگی\nیا تو اینستاگرام به ما پیغام بدی");
        cv.put("type", "Chat");
        cv.put("url", "");
        cv.put("isRead", 0);
        cv.put("sender", "admin");
        cv.put("insertTime", Utils.getCurrentTime());
        cv.put("status", "success");

            db.insert("tbl_Message", null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_Stickers");
        db.execSQL("DROP TABLE IF EXISTS tbl_Category");
        db.execSQL("DROP TABLE IF EXISTS tbl_Message");
        onCreate(db);
    }


}




