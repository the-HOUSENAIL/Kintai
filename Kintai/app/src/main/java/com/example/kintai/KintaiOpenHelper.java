package com.example.kintai;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class KintaiOpenHelper extends SQLiteOpenHelper {
    // DBのファイル名
    private static final String DB_NAME = "kintai.db";
    private static final String DB_TABLE = "kintai";
    // DBのバージョン
    private static final int DB_VERSION = 1;

    public KintaiOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        db.execSQL("CREATE TABLE kintai (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "startDay TEXT　NOT NULL," +
                "startTime TEXT NOT NULL," +
                "endDay TEXT ," +
                "endTime TEXT," +
                "breakTime TEXT," +
                "totalWork TEXT," +
                "memo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // テーブルを削除した新しく作成する
        db.execSQL("DROP TABLE IF EXISTS kintai");
        onCreate(db);
    }

}
