package com.app.quanlychitieu.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quanlychitieu.Data.DanhMucThuChiDAO;
import com.app.quanlychitieu.model.KhoanThuChi;
import com.app.quanlychitieu.Data.KhoanThuChiDao;
import com.app.quanlychitieu.Data.ViDao;
import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.app.quanlychitieu.adapter.DanhMucAdapter;
import com.app.quanlychitieu.adapter.ViAdapter;
import com.app.quanlychitieu.model.DanhMucThuChi;
import com.app.quanlychitieu.model.KhoanThuChi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAdd extends Fragment {
    KhoanThuChi khoanThuChi;
    EditText edtDate, edtTen, edtSoTien, edtGhiChu;
    Spinner spnthuchi;
    Button btndate, btnluu;
    View chondanhmuc, chonVi;
    ImageView imgdanhmuc, imgVi;
    TextView txttendanhmuc, txttenvi;
    View view;
    Calendar calendar;
    List<DanhMucThuChi> danhMucThuChiList, danhMucChi, danhMucThu; // danh sach hang muc hien thi
    ViDao viDao;
    private String current="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.fragment_add, container, false);
        danhMucThu = MainActivity.danhMucThu;
        danhMucChi = MainActivity.danhMucChi;
        MainActivity.khoanThuChiDao = new KhoanThuChiDao(getContext());
        viDao = new ViDao(getContext());
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {
        khoanThuChi = new KhoanThuChi();
        edtTen = view.findViewById(R.id.tenkhoanthuchi);
        edtSoTien = view.findViewById(R.id.sotien);
        edtSoTien.setRawInputType(Configuration.KEYBOARD_12KEY);
        edtSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                DecimalFormat dec = new DecimalFormat("0.00");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtGhiChu = view.findViewById(R.id.edtghichu);
        //khoi tao ngay mac dinh
        calendar = Calendar.getInstance();
        khoanThuChi.setThoigian(calendar);
        edtDate = view.findViewById(R.id.date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edtDate.setText(simpleDateFormat.format(calendar.getTime()));

        btndate = view.findViewById(R.id.btndate);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        spnthuchi = view.findViewById(R.id.spinner_thu_chi);
        imgdanhmuc = view.findViewById(R.id.imgdanhmucadd);
        imgVi = view.findViewById(R.id.imgloaivi);
        txttenvi = view.findViewById(R.id.txttenvi);
        txttendanhmuc = view.findViewById(R.id.txttendanhmuc);

        // tao su kien thu chi;
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.drop_simple, new String[]{getString(R.string.thu), getString(R.string.chi)});
        spnthuchi.setAdapter(arrayAdapter);
        spnthuchi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    danhMucThuChiList = danhMucThu;
                    khoanThuChi.setLoai(false);
                } else {
                    danhMucThuChiList = danhMucChi;
                    khoanThuChi.setLoai(true);
                }
                imgdanhmuc.setImageBitmap(danhMucThuChiList.get(0).getHinhanh());
                txttendanhmuc.setText(danhMucThuChiList.get(0).getTen());
                khoanThuChi.setDanhMucThuChi(danhMucThuChiList.get(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chondanhmuc = view.findViewById(R.id.chonhangmuc);
        chonVi = view.findViewById(R.id.chonvi);
        chondanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupChonDanhMuc();
            }
        });
        chonVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupChonVi();
            }
        });


        // luu khoan thu chi
        btnluu = view.findViewById(R.id.btnluukhoanthuchi);
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanThuChi.setTen(edtTen.getText().toString());
                khoanThuChi.setGhiChu(edtGhiChu.getText().toString());
                khoanThuChi.setTien(Float.parseFloat(edtSoTien.getText().toString()));
                if (khoanThuChi.getTien() == 0f) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.loinhaptien), Toast.LENGTH_SHORT).show();

                } else if (khoanThuChi.getViTien() == null) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.chuachonloaivi), Toast.LENGTH_SHORT).show();
                } else if (MainActivity.khoanThuChiDao.themKhoanThuChi(khoanThuChi)) {
                    MainActivity mainActivity = (MainActivity) getContext();
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                    FragmentWallet.viTienList = MainActivity.viDao.getAllVi();
                    mainActivity.addFragmentAdd();
                }

            }
        });
    }

    public void showPopupChonDanhMuc() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_list_danh_muc);
        ListView listView = dialog.findViewById(R.id.listdm);
        DanhMucAdapter danhMucAdapter = new DanhMucAdapter(getContext(), R.layout.item_list_danh_muc, danhMucThuChiList);
        listView.setAdapter(danhMucAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgdanhmuc.setImageBitmap(danhMucThuChiList.get(position).getHinhanh());
                txttendanhmuc.setText(danhMucThuChiList.get(position).getTen());
                khoanThuChi.setDanhMucThuChi(danhMucThuChiList.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                khoanThuChi.setThoigian(calendar);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showPopupChonVi() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_list_vi);
        ListView listView = dialog.findViewById(R.id.listvi);
        if (FragmentWallet.viTienList == null) {
            FragmentWallet.viTienList = viDao.getAllVi();
        }
        ViAdapter viAdapter = new ViAdapter(getContext(), R.layout.item_list_vi_2, FragmentWallet.viTienList);
        listView.setAdapter(viAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                khoanThuChi.setViTien(FragmentWallet.viTienList.get(position));
                txttenvi.setText(FragmentWallet.viTienList.get(position).getTen());
                if (FragmentWallet.viTienList.get(position).getLoai() == 2) {
                    imgVi.setImageResource(R.drawable.credit_card);
                } else imgVi.setImageResource(R.drawable.tien_mat);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
