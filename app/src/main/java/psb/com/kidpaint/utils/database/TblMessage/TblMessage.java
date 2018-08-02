package psb.com.kidpaint.utils.database.TblMessage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.database.Sql;
import psb.com.kidpaint.webApi.chat.Get.model.Extra;
import psb.com.kidpaint.webApi.shareModel.PaintModel;



public class TblMessage {
    private interFaceDB_InsertChats interFaceDB_insertChats;
    Context mContext;

    public TblMessage(Context c) {
        this.mContext = c;
    }


    public void insertMessageList(List<Extra> chatLists,boolean isFirst,interFaceDB_InsertChats inte) {
        this.interFaceDB_insertChats = inte;

        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < chatLists.size(); i++) {
            cv.put("chatId", chatLists.get(i).getChatId());
            cv.put("messageId", chatLists.get(i).getId());
            cv.put("title", chatLists.get(i).getTitle()+"");
            cv.put("imageUrl", chatLists.get(i).getImageUrl());
            cv.put("body", chatLists.get(i).getDescription());
            cv.put("url", chatLists.get(i).getUrl()+"");
            cv.put("isRead", isFirst?1:(chatLists.get(i).getIsRead()?1:0));
            cv.put("sender", chatLists.get(i).getUsername());
            cv.put("insertTime", chatLists.get(i).getCreateDate());
            cv.put("status", "success");
            int update = db.update("tbl_Message", cv, "messageId=?", new String[]{chatLists.get(i).getId() + ""});
            if (update == 0) {
                db.insert("tbl_Message", null, cv);
            }
        }

        db.close();
        sql.close();
        if (interFaceDB_insertChats != null) {
            interFaceDB_insertChats.onSuccessInsertChats(chatLists.size());
        }

    }


    public Long insertMyMessage(int chatId,String description, String userName, String status, String time) {
        long id = -1;
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("messageId", -1);
        cv.put("chatId", chatId);
        cv.put("title", "");
        cv.put("imageUrl", "");
        cv.put("body", description);
        cv.put("url", "");
        cv.put("isRead", 1);
        cv.put("sender", userName);
        cv.put("insertTime", time);
        cv.put("status", status);

        id = db.insert("tbl_Message", null, cv);

        db.close();
        sql.close();
        return id;
    }

    public void updateMessage(long dbId, long messageId, String time, String status) {
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("messageId", messageId);
        cv.put("status", status);
        cv.put("insertTime", time);

        db.update("tbl_Message", cv, "dbid=?", new String[]{dbId + ""});

        db.close();
        sql.close();
    }


    public List<Extra> getMessageList() {
        List<Extra> chatLists = new ArrayList<>();
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();

        String[] columns = new String[]{"chatId", "messageId","title", "body", "status", "imageUrl", "sender", "isRead", "insertTime","url","dbid"};
        Cursor c = db.query("tbl_Message", columns, null, null, null, null, "insertTime ASC");
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    Extra chatList = new Extra();
                    chatList.setChatId(c.getInt(0));
                    chatList.setId(c.getInt(1));
                    chatList.setTitle(c.getString(2));
                    chatList.setDescription(c.getString(3));
                    chatList.setStatus(c.getString(5));
                    chatList.setImageUrl(c.getString(5));
                    chatList.setUsername(c.getString(6));
                    chatList.setIsRead(c.getInt(7) == 1 ? true : false);
                    chatList.setCreateDate(c.getString(8));
                    chatList.setUrl(c.getString(9));
                    chatList.setDbId(c.getInt(10));

                    chatLists.add(chatList);
                    c.moveToNext();
                }
            }
        }
        db.close();
        sql.close();
        return chatLists;
    }
    public Extra getInsertedMessage(int dbId) {
        Extra chatList = null;
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();

        String[] columns = new String[]{"chatId", "messageId","title", "body", "status", "imageUrl", "sender", "isRead", "insertTime","url","dbid"};
        Cursor c = db.query("tbl_Message", columns, "dbid=?", new String[]{""+dbId}, null, null, "insertTime ASC");
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                     chatList = new Extra();
                    chatList.setChatId(c.getInt(0));
                    chatList.setId(c.getInt(1));
                    chatList.setTitle(c.getString(2));
                    chatList.setDescription(c.getString(3));
                    chatList.setStatus(c.getString(5));
                    chatList.setImageUrl(c.getString(5));
                    chatList.setUsername(c.getString(6));
                    chatList.setIsRead(c.getInt(7) == 1 ? true : false);
                    chatList.setCreateDate(c.getString(8));
                    chatList.setUrl(c.getString(9));
                    chatList.setDbId(c.getInt(10));



            }
        }
        db.close();
        sql.close();
        return chatList;
    }

    public void removeChatMessage(long msgID) {
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        db.delete("tbl_Chat", "dbid=?", new String[]{msgID + ""});
        db.close();
        sql.close();
    }

    public int getUnreadChatMessageCount() {
        int count = 0;
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = new String[]{"dbid"};
        Cursor c = db.query("tbl_Message", columns, "isRead=?", new String[]{"0"}, null, null, null);
        count = c.getCount();
        db.close();
        sql.close();
        return count;
    }

    public void setAllWatchedChatMessage() {
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isRead", 1);
        db.update("tbl_Message", cv, "isRead=?", new String[]{"0"});
        db.close();
        sql.close();
    }


    public String getMessageLastUpdateTime() {
        String time = "0";
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = new String[]{"insertTime"};
        Cursor c = db.query("tbl_Message", columns, null,null, null, null, "insertTime ASC");
        if (c.moveToLast()) {
            time = c.getString(0);
        }

        db.close();
        sql.close();
        return time;
    }

    public interface interFaceDB_InsertChats {
        void onSuccessInsertChats(int size);
    }
}
