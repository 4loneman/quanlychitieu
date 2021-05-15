package com.app.quanlychitieu.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.quanlychitieu.Data.ViDao;
import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.app.quanlychitieu.adapter.ViAdapter;
import com.app.quanlychitieu.model.ViTien;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentWallet extends Fragment {
    public static List<ViTien> viTienList;
    ListView listvi;
    ViAdapter viAdapter;
    ImageView themvi;
    View view;
    TextView txtTongtien;
    public static Float tongtien;
    NumberFormat numberFormat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            viAdapter.setViList(viTienList);
            setTongTien();
            return view;
        }
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        viTienList = MainActivity.viDao.getAllVi();
        numberFormat = numberFormat.getNumberInstance();
        anhxa(view);
        viAdapter = new ViAdapter(getContext(), R.layout.item_list_vi, viTienList);
        listvi.setAdapter(viAdapter);
        return view;
    }

    public void anhxa(View view) {
        txtTongtien = view.findViewById(R.id.txttongtien);
        setTongTien();
        themvi = view.findViewById(R.id.themvi);
        themvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupThemVi();
            }
        });
        listvi = (ListView) view.findViewById(R.id.list_vi);
    }

    public void showPopupThemVi() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_them_vi);
        final EditText edttenvi = dialog.findViewById(R.id.edttenvi);
        final EditText sodu = dialog.findViewById(R.id.edtsodu);
        final Spinner spinner = dialog.findViewById(R.id.spnloaivi);
        final ImageView imageLoai = dialog.findViewById(R.id.imgloaivi);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, new String[]{getContext().getString(R.string.loaitienmat), getContext().getString(R.string.loaithe)}));
        Button btnLuu = (Button) dialog.findViewById(R.id.btnluu);
        Button btnhuy = (Button) dialog.findViewById(R.id.btnthoat);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    imageLoai.setImageResource(R.drawable.tien_mat);
                } else imageLoai.setImageResource(R.drawable.credit_card);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edttenvi.getText().toString();
                Float sd = Float.parseFloat(sodu.getText().toString());
                int loai = spinner.getSelectedItemPosition() + 1;
                ViTien viTien = new ViTien(1, ten, sd, loai);
                MainActivity.viDao.themVi(viTien);
                viTienList = MainActivity.viDao.getAllVi();
                viAdapter.setViList(viTienList);
                setTongTien();
                dialog.dismiss();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setTongTien() {
        tongtien = 0f;
        for (ViTien viTien : viTienList) {
            tongtien = tongtien + viTien.getSodu();
        }
        String str = numberFormat.format(tongtien) + "đ";
        txtTongtien.setText(str);
    }
}
