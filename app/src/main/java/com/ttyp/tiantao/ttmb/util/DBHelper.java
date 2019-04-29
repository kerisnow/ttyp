package com.ttyp.tiantao.ttmb.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // 数据库默认名字
    public static final String db_name = "ttyp.db";

    public DBHelper(Context context, int version) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE `homepage` (" +
//                "`pageid`  INTEGER(18) NOT NULL PRIMARY KEY AUTOINCREMENT ," +
//                "`pagename`  varchar(255)," +
//                "`_data`  varchar," +
//                "`lastdate`  date" +
//                ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
