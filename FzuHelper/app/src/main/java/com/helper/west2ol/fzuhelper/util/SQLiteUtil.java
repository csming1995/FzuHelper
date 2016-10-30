package com.helper.west2ol.fzuhelper.util;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/10/30.
 */

public class SQLiteUtil {
    public static SQLiteDatabase db;
    public static void createDataBases(){
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.helper.west2ol/databases/courseTable.db" , null);
    }

    public static void createCourseTable(){
    }
}
