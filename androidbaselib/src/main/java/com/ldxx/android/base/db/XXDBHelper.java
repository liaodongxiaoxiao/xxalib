package com.ldxx.android.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WangZhuo on 2015/6/14.
 */
public class XXDBHelper extends SQLiteOpenHelper {

    private String[] createSQL;
    private String[] updateSQL;

    public XXDBHelper(Context context, String name, int version, String[] createSQL, String[] updateSQL) {
        super(context, name, null, version);
        this.createSQL = createSQL;
        this.updateSQL = updateSQL;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("");
        if (createSQL != null && createSQL.length > 0) {
            for (String cq : createSQL) {
                db.execSQL(cq);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("");
        if (updateSQL != null && updateSQL.length > 0) {
            for (String uq : updateSQL) {
                db.execSQL(uq);
            }

        }

    }
}
