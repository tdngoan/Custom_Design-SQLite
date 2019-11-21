package com.itnnsoft.custom_design_sqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.itnnsoft.custom_design_sqlite.MainActivity;
import com.itnnsoft.custom_design_sqlite.model.LienHe;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private final String TAG = "DBManager";
    private static final String DATABASE_NAME = "lienhes_manager";
    private static final String TABLE_NAME = "lienhe";
    private static final String SDT = "sdt";
    private static final String NAME = "name";
    private static final String GT = "gt";
    private static int VERSION = 1;

    private Context context;
    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            SDT + " TEXT primary key, " +
            NAME + " TEXT, " +
            GT + " TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "DBManager: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
    }

    public void addLienhe(LienHe lienhe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SDT, lienhe.getNumber());
        values.put(NAME, lienhe.getTen());
        values.put(GT, lienhe.getGioi_tinh());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<LienHe> getAllLienhe() {
        List<LienHe> listLienhe = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                LienHe lienhe = new LienHe();
                lienhe.setNumber(cursor.getString(0));
                lienhe.setTen(cursor.getString(1));
                lienhe.setGioi_tinh(cursor.getString(2));

                listLienhe.add(lienhe);

            } while (cursor.moveToNext());
        }
        db.close();
        return listLienhe;
    }

    public int updateLienhe(LienHe lienhe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,lienhe.getTen());
        contentValues.put(GT,lienhe.getGioi_tinh());
        return db.update(TABLE_NAME,contentValues,SDT+"=?",new String[]{String.valueOf(lienhe.getNumber())});
    }

    public int deleteLienhe(String sdt){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,SDT+"=?",new String[] {sdt});
    }
}
