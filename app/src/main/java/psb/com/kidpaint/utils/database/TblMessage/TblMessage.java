package psb.com.kidpaint.utils.database.TblMessage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.database.Sql;
import psb.com.kidpaint.webApi.shareModel.PaintModel;



public class TblMessage {
    private interFaceDB_InsertChats interFaceDB_insertChats;
    Context mContext;

    public TblMessage(Context c, interFaceDB_InsertChats inte) {
        this.mContext = c;
        this.interFaceDB_insertChats = inte;
    }


    public void insertMessageList(List<PaintModel> chatLists) {
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < chatLists.size(); i++) {
            cv.put("messageId", chatLists.get(i).getId());
     /*       cv.put("title", chatLists.get(i).getDescription());
            cv.put("imageUrl", chatLists.get(i).getDescription());
            cv.put("body", chatLists.get(i).getDescription());
            cv.put("url", chatLists.get(i).getDescription());
            cv.put("isRead", chatLists.get(i).getDescription());
            cv.put("replyId", chatLists.get(i).getDescription());
            cv.put("replyMessage", chatLists.get(i).getDescription());
            cv.put("sender", chatLists.get(i).getDescription());
            cv.put("insertTime", chatLists.get(i).getDescription());*/
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



    public Long insertMyMessage(String description, String userName, String status, String time) {
        long id = -1;
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("messageId", -1);
        cv.put("title", "");
        cv.put("imageUrl", "");
        cv.put("body", description);
        cv.put("url", "");
        cv.put("isRead", 1);
        cv.put("replyId", -1);
        cv.put("replyMessage", -1);
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



    public List<PaintModel> getMessageList() {
        List<PaintModel> chatLists = new ArrayList<>();
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();

        String[] columns = new String[]{"chatId", "messageId", "description", "status", "imageUrl", "username", "isRead", "createDate"};
        Cursor c = db.query("tbl_Chat", columns, null, null, null, null, "createDate ASC");
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
               /*     ChatList chatList = new ChatList();
                    chatList.setChatId(c.getLong(0));
                    chatList.setId(c.getLong(1));
                    chatList.setDescription(c.getString(2));
                    chatList.setStatus(c.getString(3));
                    chatList.setImageUrl(c.getString(4));
                    chatList.setUsername(c.getString(5));
                    chatList.setIsRead(c.getInt(6) == 1 ? true : false);
                    chatList.setCreateDate(c.getString(7));

                    chatLists.add(chatList);*/
                    c.moveToNext();
                }
            }
        }
        db.close();
        sql.close();
        return chatLists;
    }

    public void removeChatMessage(long msgID) {
        Sql sql = new Sql(mContext);
        SQLiteDatabase db = sql.getReadableDatabase();
        db.delete("tbl_Chat", "messageId=?", new String[]{msgID + ""});
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




    public interface interFaceDB_InsertChats {
        void onSuccessInsertChats(int size);
    }
}
