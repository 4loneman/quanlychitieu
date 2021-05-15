package com.app.quanlychitieu.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;


import com.app.quanlychitieu.model.ViTien;

import java.util.ArrayList;
import java.util.List;

public class ViDao extends SQLiteOpenHelper {
    Context context;
    public static final String DB_NAME = "qlthuchi";
    public static final String TB_NAME = "vi";
    public static final String ID = "id";
    public static final String SO_DU = "so_du";
    public static final String TEN = "ten";
    public static final String LOAI = "loai";
    public static final String TRANG_THAI = "trangthai";

    public static final String CREATE_TB = "CREATE TABLE " + TB_NAME + "( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            TEN + " TEXT, " +
            SO_DU + " REAL ," +
            LOAI + " INTEGER, " +
            TRANG_THAI + " INTEGER )";


    public static final Integer VERSION = 1;

    public ViDao(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    SQLiteDatabase database = getWritableDatabase();
    List<ViTien> listvi = new ArrayList<>();
    Cursor cursor = database.query(TB_NAME, null, null, null, null, null, null);

    public void themVi(ViTien viTien) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEN, viTien.getTen());
        contentValues.put(SO_DU, viTien.getSodu());
        contentValues.put(LOAI, viTien.getLoai());
        contentValues.put(TRANG_THAI, 1);
        database.insert(TB_NAME, null, contentValues);
    }

    public List<ViTien> getAllVi() {
        SQLiteDatabase database = getWritableDatabase();
        List<ViTien> listvi = new ArrayList<>();
        Cursor cursor = database.query(TB_NAME, null, TRANG_THAI + " = ? ", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            float sodu = cursor.getFloat(2);
            int loai = cursor.getInt(3);
            listvi.add(new ViTien(id, ten, sodu, loai));
        }
        return listvi;
    }

    public ViTien getViById(Integer id) {
        ViTien vi = new ViTien();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, ID + " = ?", new String[]{id.toString()}, null, null, null);
        if (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            float sodu = cursor.getFloat(2);
            int loai = cursor.getInt(3);
            vi = new ViTien(id, ten, sodu, loai);
        }
        return vi;
    }

    public void xoaVi(Integer id) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANG_THAI, 0);
        database.update(TB_NAME, contentValues, ID + " = ? ", new String[]{id.toString()});
    }

    public void upDateSodu(Float sodu, Integer id) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SO_DU, sodu);
        database.update(TB_NAME, contentValues, ID + " =? ", new String[]{id.toString()});
    }


}
