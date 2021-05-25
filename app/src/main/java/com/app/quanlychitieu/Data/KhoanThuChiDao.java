package com.app.quanlychitieu.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.app.quanlychitieu.model.DanhMucThuChi;
import com.app.quanlychitieu.model.KhoanThuChi;
import com.app.quanlychitieu.model.ViTien;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class KhoanThuChiDao extends SQLiteOpenHelper {
    public static String DB_NAME = "qlthuchi";
    public static String TB_NAME = "khoan_thu_chi";
    public static String ID = "id";
    public static String IDVI = "id_vi";
    public static String IDDM = "id_dm";
    public static String TEN = "ten";
    public static String TIEN = "tien";
    public static String LOAI = "loai";
    public static String THOIGIAN = "thoigian";
    public static String GHICHU = "ghichu";

    public static String CREATE_TB = "CREATE TABLE " + TB_NAME + " ( " +
            ID + " INTEGER PRIMARY KEY, " +
            IDVI + " INTEGER, " +
            IDDM + " INTEGER, " +
            TEN + " TEXT, " +
            TIEN + " REAL, " +
            LOAI + " BIT, " +
            THOIGIAN + " INTEGER, " +
            GHICHU + " TEXT )";


    public static int VERSION = 1;

    ViDao viDao;

    Context context;


    public KhoanThuChiDao(Context context) {
        super(context, DB_NAME, null, VERSION);
        viDao = new ViDao(context);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB);

    }

    public boolean themKhoanThuChi(KhoanThuChi khoanThuChi) {
        SQLiteDatabase database = getWritableDatabase();
        if (khoanThuChi.getLoai()) {
            if (khoanThuChi.getViTien().getSodu().compareTo(khoanThuChi.getTien()) == -1) {
                Toast.makeText(context, context.getResources().getString(R.string.sotienkhongdu), Toast.LENGTH_SHORT).show();
                return false;
            } else {
                MainActivity.viDao.upDateSodu(khoanThuChi.getViTien().getSodu() - khoanThuChi.getTien(), khoanThuChi.getViTien().getId());
            }

        } else {
            MainActivity.viDao.upDateSodu(khoanThuChi.getTien() + khoanThuChi.getViTien().getSodu(), khoanThuChi.getViTien().getId());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDVI, khoanThuChi.getViTien().getId());
        contentValues.put(IDDM, khoanThuChi.getDanhMucThuChi().getId());
        contentValues.put(TEN, khoanThuChi.getTen());
        contentValues.put(LOAI, khoanThuChi.getLoai());
        contentValues.put(THOIGIAN, khoanThuChi.getThoigian().getTimeInMillis());
        contentValues.put(GHICHU, khoanThuChi.getGhiChu());
        contentValues.put(TIEN, khoanThuChi.getTien());
        database.insert(TB_NAME, null, contentValues);
        return true;
    }

    public List<KhoanThuChi> getAll() {
        List<KhoanThuChi> khoanThuChis = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, null, null, null, null, THOIGIAN);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int idVi = cursor.getInt(1);
            int idDm = cursor.getInt(2);
            String ten = cursor.getString(3);
            boolean loai = false;
            if (cursor.getInt(5) == 1) loai = true;
            long date = cursor.getLong(6);
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(new Date(date));
            } catch (Exception e) {
            }
            Float tien = cursor.getFloat(4);
            String ghichu = cursor.getString(7);
            ViTien viTien = viDao.getViById(idVi);
            DanhMucThuChi mucThuChi = new DanhMucThuChi();
            for (DanhMucThuChi danhMucThuChi : MainActivity.danhMucThuChis) {
                if (danhMucThuChi.getId() == idDm) {
                    mucThuChi = danhMucThuChi;
                    break;
                }
            }
            KhoanThuChi khoanThuChi = new KhoanThuChi(id, viTien, mucThuChi, ten, tien, calendar, loai, ghichu);
            khoanThuChis.add(khoanThuChi);
        }
        return khoanThuChis;
    }

    public List<KhoanThuChi> getbyDate(Long startDate, Long endDate) {
        List<KhoanThuChi> khoanThuChis = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, null, THOIGIAN + " >= ? AND " + THOIGIAN + " <= ?", new String[]{startDate.toString(), endDate.toString()}, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int idVi = cursor.getInt(1);
            int idDm = cursor.getInt(2);
            String ten = cursor.getString(3);
            boolean loai = false;
            if (cursor.getInt(5) == 1) loai = true;
            Long date = cursor.getLong(6);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(date));
            Float tien = cursor.getFloat(4);
            String ghichu = cursor.getString(7);
            ViTien viTien = viDao.getViById(idVi);
            DanhMucThuChi mucThuChi = new DanhMucThuChi();
            for (DanhMucThuChi danhMucThuChi : MainActivity.danhMucThuChis) {
                if (danhMucThuChi.getId() == idDm) {
                    mucThuChi = danhMucThuChi;
                    break;
                }
            }
            KhoanThuChi khoanThuChi = new KhoanThuChi(id, viTien, mucThuChi, ten, tien, calendar, loai, ghichu);
            khoanThuChis.add(khoanThuChi);
        }
        return khoanThuChis;
    }

    public List<BarEntry> getDataSetTienTrongNgay(int loai, XAxis xAxis) {
        List<BarEntry> barEntries = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.DAY_OF_MONTH);
        int b = calendar.get(Calendar.MONTH) + 1;
        int c = calendar.get(Calendar.YEAR);
        String date = a + "/" + b + "/" + c;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Long d = simpleDateFormat.parse(date).getTime();
            simpleDateFormat = new SimpleDateFormat("dd/MM");
            for (int i = 0; i < 7; i++) {
                strings.add(simpleDateFormat.format(new Date(d)));
                barEntries.add(new BarEntry(i, tienTrongNgay(d, loai)));
                d = d - 86400000;
            }
            xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));
        } catch (Exception ex) {
        }
        return barEntries;
    }


    public Float tienTrongNgay(Long begin, Integer loai) {
        Float tong = 0f;
        Long end = begin + 86400000;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TB_NAME, new String[]{"SUM(" + TIEN + ")"},
                THOIGIAN + " >= ? AND " + THOIGIAN + " <= ? AND " + LOAI + " =?", new String[]{begin.toString(), end.toString(), loai.toString()},
                null, null, null);
        if (cursor.moveToNext()) tong = cursor.getFloat(0);
        return tong;
    }

    public List<KhoanThuChi> getbyDate(Long startDate, Long endDate, Integer loaitc) {
        List<KhoanThuChi> khoanThuChis = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor;
        if (loaitc == 2) {
            cursor = database.query(TB_NAME, null, THOIGIAN + " >= ? AND " + THOIGIAN + " <= ?", new String[]{startDate.toString(), endDate.toString()}, null, null, null);
        } else {
            cursor = database.query(TB_NAME, null, THOIGIAN + " >= ? AND " + THOIGIAN + " <= ? AND " + LOAI + " =?", new String[]{startDate.toString(), endDate.toString(), loaitc.toString()}, null, null, null);
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int idVi = cursor.getInt(1);
            int idDm = cursor.getInt(2);
            String ten = cursor.getString(3);
            boolean loai = false;
            if (cursor.getInt(5) == 1) loai = true;
            Long date = cursor.getLong(6);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(date));
            Float tien = cursor.getFloat(4);
            String ghichu = cursor.getString(7);
            ViTien viTien = viDao.getViById(idVi);
            DanhMucThuChi mucThuChi = new DanhMucThuChi();
            for (DanhMucThuChi danhMucThuChi : MainActivity.danhMucThuChis) {
                if (danhMucThuChi.getId() == idDm) {
                    mucThuChi = danhMucThuChi;
                    break;
                }
            }
            KhoanThuChi khoanThuChi = new KhoanThuChi(id, viTien, mucThuChi, ten, tien, calendar, loai, ghichu);
            khoanThuChis.add(khoanThuChi);
        }
        return khoanThuChis;
    }


    // lay du lieu de ve bieu do danh muc
    public List<PieEntry> getDataset(Long startDate, Long endDate, Integer loai) {
        List<PieEntry> pieEntries = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TB_NAME, new String[]{IDDM, "SUM(" + TIEN + ")"},
                THOIGIAN + " >= ? AND " + THOIGIAN + " <= ? AND " + LOAI + " =?",
                new String[]{startDate.toString(), endDate.toString(), loai.toString()},
                IDDM, null, null);
        while (cursor.moveToNext()) {
            Integer iddm = cursor.getInt(0);
            String ten = "";
            for (DanhMucThuChi danhMucThuChi : MainActivity.danhMucThuChis) {
                if (iddm == danhMucThuChi.getId()) ten = danhMucThuChi.getTen();
            }
            Float tien = cursor.getFloat(1);
            PieEntry pieEntry = new PieEntry(tien, ten);
            pieEntries.add(pieEntry);
        }
        return pieEntries;
    }

    public List<BarEntry> getDatasetBarChart(int loai) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            barEntries.add(new BarEntry(i, getTongtien(i, loai)));
        }
        return barEntries;
    }

    public Float getTongtien(Integer thang, Integer loai) {
        Float tong = 0f;
        String begin = "1/" + thang.toString() + "/2021";
        thang++;
        String end = "1/" + thang.toString() + "/2021";
        Long b = new Long(0);
        Long e = new Long(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            b = simpleDateFormat.parse(begin).getTime();
            e = simpleDateFormat.parse(end).getTime();
        } catch (Exception ex) {

        }
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TB_NAME, new String[]{"SUM(" + TIEN + ")"},
                THOIGIAN + " >= ? AND " + THOIGIAN + " <= ? AND " + LOAI + " =?", new String[]{b.toString(), e.toString(), loai.toString()},
                null, null, null);
        if (cursor.moveToNext()) tong = cursor.getFloat(0);
        return tong;
    }

    public void xoaKhoanThuChi(Integer ma) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TB_NAME, ID + " =?", new String[]{ma.toString()});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
