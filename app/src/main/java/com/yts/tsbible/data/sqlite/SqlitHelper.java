package com.yts.tsbible.data.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.yts.tsbible.data.model.Bible;

import java.util.ArrayList;

public class SqlitHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "bible.sqlite";
    private static final int DATABASE_VERSION = 1;

    public SqlitHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getEmployees() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"idx", "cate", "book", "chapter", "paragraph", "sentence", "testament", "long_label", "short_label"};
        String sqlTables = "bible2";
        qb.setTables(sqlTables);

        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        c.moveToFirst();
        return c;
    }

    public ArrayList<Bible> getBibleList() {
        ArrayList<Bible> bibleList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"idx", "cate", "book", "chapter", "paragraph", "sentence", "testament", "long_label", "short_label"};
        String sqlTables = "bible2";
        qb.setTables(sqlTables);

        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Bible bible = new Bible(c.getString(0), c.getString(1), c.getString(7), c.getInt(2), c.getString(6), c.getString(3), c.getString(4), c.getString(5));
                    bibleList.add(bible);
                } while (c.moveToNext());
            }
        }
        return bibleList;
    }

}
