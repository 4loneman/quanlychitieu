package com.app.quanlychitieu.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.app.quanlychitieu.R;
import com.app.quanlychitieu.model.DanhMucThuChi;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DanhMucThuChiDAO extends SQLiteOpenHelper {
    public static final String DB_NAME = "qlthuchi";
    public static final String TB_NAME = "danhmucthuchi";
    public static final String ID = "id";
    public static final String TEN = "ten";
    public static final String HINH = "hinh";
    public static final String LOAI = "loai";

    public static final String CREATE_TB = "CREATE TABLE " + TB_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TEN + " TEXT, " +
            HINH + " BLOD, " +
            LOAI + " INTEGER )";

    public static final Integer VERSION = 1;
    private Context context;

    public DanhMucThuChiDAO(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB);
        db.execSQL(ViDao.CREATE_TB);
        db.execSQL(KhoanThuChiDao.CREATE_TB);

        // khoi tao khoan thu
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.mucchi), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sell), 1), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.chi_an_uong), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_noodle), 1), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.chi_dien_thoai), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_phone), 1), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.chi_quan_ao), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cothes), 1), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.chi_mua_sam), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_shopping), 1), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.chi_hoc_tap), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_book), 1), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.chi_the_duc), BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_fitness), 1), db);

        // them khoan chi
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.mucthu), BitmapFactory.decodeResource(context.getResources(), R.drawable.thupng), 0), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.thu_tien_luon), BitmapFactory.decodeResource(context.getResources(), R.drawable.thu_tien_luong), 0), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.thu_ban_do), BitmapFactory.decodeResource(context.getResources(), R.drawable.thu_ban), 0), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.thu_lai_xuat), BitmapFactory.decodeResource(context.getResources(), R.drawable.thu_dautu), 0), db);
        themDanhMuc(new DanhMucThuChi(context.getResources().getString(R.string.thu_vay_no), BitmapFactory.decodeResource(context.getResources(), R.drawable.thu_divay), 0), db);

    }

    public void themDanhMuc(DanhMucThuChi danhMucThuChi) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Bitmap hinh = danhMucThuChi.getHinhanh();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        hinh.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] hinhanh = outputStream.toByteArray();
        contentValues.put(TEN, danhMucThuChi.getTen());
        contentValues.put(HINH, hinhanh);
        contentValues.put(LOAI, danhMucThuChi.getLoai());
        database.insert(TB_NAME, null, contentValues);
    }

    public void themDanhMuc(DanhMucThuChi danhMucThuChi, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        Bitmap hinh = danhMucThuChi.getHinhanh();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        hinh.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] hinhanh = outputStream.toByteArray();
        contentValues.put(TEN, danhMucThuChi.getTen());
        contentValues.put(HINH, hinhanh);
        contentValues.put(LOAI, danhMucThuChi.getLoai());
        database.insert(TB_NAME, null, contentValues);
    }

    public List<DanhMucThuChi> loadAllChi() {
        List<DanhMucThuChi> resul = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, LOAI + " = ?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] hinh = cursor.getBlob(2);
            Bitmap hinhanh = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
            DanhMucThuChi danhMucThuChi = new DanhMucThuChi(ma, ten, hinhanh, 1);
            resul.add(danhMucThuChi);
        }
        return resul;
    }

    public List<DanhMucThuChi> loadAllthu() {
        List<DanhMucThuChi> resul = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, LOAI + " = ?", new String[]{"0"}, null, null, null);
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] hinh = cursor.getBlob(2);
            Bitmap hinhanh = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
            DanhMucThuChi danhMucThuChi = new DanhMucThuChi(ma, ten, hinhanh, 1);
            resul.add(danhMucThuChi);
        }
        return resul;
    }

    public List<DanhMucThuChi> loadAll() {
        List<DanhMucThuChi> resul = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] hinh = cursor.getBlob(2);
            Bitmap hinhanh = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
            DanhMucThuChi danhMucThuChi = new DanhMucThuChi(ma, ten, hinhanh, 1);
            resul.add(danhMucThuChi);
        }
        return resul;
    }

    public DanhMucThuChi getDmById(Integer id) {
        DanhMucThuChi danhMucThuChi = new DanhMucThuChi();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, ID + " = ?", new String[]{id.toString()}, null, null, null);
        if (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            int loai = cursor.getInt(3);
            byte[] hinh = cursor.getBlob(2);
            Bitmap hinhanh = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
            danhMucThuChi = new DanhMucThuChi(id, ten, hinhanh, loai);
        }
        return danhMucThuChi;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
