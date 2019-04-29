package com.ttyp.tiantao.ttmb.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ttyp.tiantao.ttmb.entity.HomePageSave;

import java.util.HashMap;
import java.util.Map;

public class DBManger {
    private Context context;
    private static DBManger instance;
    // 操作表的对象，进行增删改查
    private SQLiteDatabase writableDatabase;

    private DBManger(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context, 1);
        writableDatabase = dbHelper.getWritableDatabase();
    }

    public static DBManger getInstance(Context context) {
        if (instance == null) {
            synchronized (DBManger.class) {
                if (instance == null) {
                    instance = new DBManger(context);
                }
            }
        }
        return instance;
    }

    public void add(HomePageSave homePageSave) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("pageid", homePageSave.getPageid());
        contentValues.put("pagename", homePageSave.getPagename());
        contentValues.put("_data", homePageSave.getData());
        contentValues.put("lastdate", homePageSave.getLastSaveDate().getTime());

        writableDatabase.insert("homepage", null, contentValues);
    }

    public void del(HomePageSave homePageSave) {
        writableDatabase.delete("homepage", "pageid = ?", new String[]{homePageSave.getPageid()+""});
    }

    public void update(HomePageSave homePageSave) {
        ContentValues contentValues = new ContentValues();
        if(homePageSave == null){
            return;
        }
        if(homePageSave.getData()!=null) {
            contentValues.put("_data", homePageSave.getData());
        }
        if(homePageSave.getPagename()!=null){
            contentValues.put("pagename", homePageSave.getPagename());
        }
        if(homePageSave.getLastSaveDate()!=null){
            contentValues.put("lastdate", homePageSave.getLastSaveDate().getTime());
        }
        writableDatabase.update("homepage", contentValues, "pageid = ?", new String[]{homePageSave.getPageid()+""});
    }

    public Map<Integer,HomePageSave> select() {

//        Cursor cursor = writableDatabase.query("homepage", null, null, null, null, null, "pageid", null);
//
//        int position = cursor.getPosition();
//        Log.e(TAG, "select: 游标默认位置：" + position);
        Map<Integer,HomePageSave> result = new HashMap<>();
//        while (cursor.moveToNext()) {
//            String pagename = cursor.getString(cursor.getColumnIndex("pagename"));
//            String data = cursor.getString(cursor.getColumnIndex("_data"));
//            int pageid = cursor.getInt(cursor.getColumnIndex("pageid"));
//            Date lastdate = new Date(cursor.getLong(cursor.getColumnIndex("lastdate")));
//            HomePageSave homePageSave = new HomePageSave();
//            homePageSave.setPageid(pageid);
//            homePageSave.setData(data);
//            homePageSave.setPagename(pagename);
//            homePageSave.setLastSaveDate(lastdate);
//            result.put(pageid,homePageSave);
//        }
        return result;

    }
}
