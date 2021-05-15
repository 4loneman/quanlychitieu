package com.app.quanlychitieu.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quanlychitieu.Data.KhoanThuChiDao;
import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.app.quanlychitieu.adapter.KhoanThuChiAdapter;
import com.app.quanlychitieu.model.KhoanThuChi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentReportLstc extends Fragment {
    View view;
    RadioGroup radioGroup;
    RadioButton radTatCa;
    ListView listKhoanTC;
    EditText beginDate, endDate;
    List<KhoanThuChi> khoanThuChiList;
    KhoanThuChiAdapter khoanThuChiAdapter;
    Long begin, end;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    int flag = 2; //0 thu 1 chi 2 tatca

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.fragment_baocao_lichsuthuchi, container, false);
        anXa(view);
        khoanThuChiList = MainActivity.khoanThuChiDao.getbyDate(begin, end);
        khoanThuChiAdapter = new KhoanThuChiAdapter(getContext(), R.layout.item_thuchi, khoanThuChiList);
        listKhoanTC.setAdapter(khoanThuChiAdapter);
        return view;
    }

    public void anXa(View view) {
        beginDate = view.findViewById(R.id.begindate1);
        endDate = view.findViewById(R.id.enddate1);

        // khoi tao ngay thang tim kiem mac dinh
        Calendar calendar = Calendar.getInstance();
        String b = "1/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        String e = "1/" + (calendar.get(Calendar.MONTH) + 2) + "/" + calendar.get(Calendar.YEAR);
        try {
            this.begin = simpleDateFormat.parse(b).getTime();
            this.end = simpleDateFormat.parse(e).getTime();
        } catch (Exception ex) {
        }
        beginDate.setText(b);
        endDate.setText(e);

        // khi bo forcus lay lai danh sach tim kiem
        beginDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {
                        begin = simpleDateFormat.parse(beginDate.getText().toString()).getTime();
                        khoanThuChiList = MainActivity.khoanThuChiDao.getbyDate(begin, end, flag);
                        khoanThuChiAdapter.setKhoanThuChiList(khoanThuChiList);
                        beginDate.clearFocus();
                    }
                } catch (ParseException ex) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.nhapdungtime), Toast.LENGTH_LONG).show();
                }
            }
        });

        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {
                        end = simpleDateFormat.parse(endDate.getText().toString()).getTime();
                        khoanThuChiList = MainActivity.khoanThuChiDao.getbyDate(begin, end, flag);
                        khoanThuChiAdapter.setKhoanThuChiList(khoanThuChiList);
                        endDate.clearFocus();
                    }
                } catch (ParseException ex) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.nhapdungtime), Toast.LENGTH_LONG).show();
                }
            }
        });


        listKhoanTC = view.findViewById(R.id.listlstc);
        radioGroup = view.findViewById(R.id.ragroup);

        // loc theo muc thu chi
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radthu:
                        flag = 0;
                        break;
                    case R.id.radchi:
                        flag = 1;
                        break;
                    case R.id.radtatca:
                        flag = 2;
                        break;
                }
                khoanThuChiList = MainActivity.khoanThuChiDao.getbyDate(begin, end, flag);
                khoanThuChiAdapter.setKhoanThuChiList(khoanThuChiList);


            }
        });

        // xem chi tiet khoan thu chi
        listKhoanTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupChiTiet(khoanThuChiList.get(position));
            }
        });

    }

    public void showPopupChiTiet(final KhoanThuChi khoanThuChi) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_khoanthuchi);
        TextView ten = dialog.findViewById(R.id.popupten);
        ten.setText(khoanThuChi.getTen());
        TextView sotien = (TextView) dialog.findViewById(R.id.popupsotien);
        sotien.setText(khoanThuChi.getTien().toString() + "đ");
        TextView tg = dialog.findViewById(R.id.popupngay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tg.setText(simpleDateFormat.format(khoanThuChi.getThoigian().getTime()).toString());
        TextView dm = dialog.findViewById(R.id.popupdanhmuc);
        dm.setText(khoanThuChi.getDanhMucThuChi().getTen());
        TextView tenvi = dialog.findViewById(R.id.popuptenvi);
        tenvi.setText(khoanThuChi.getViTien().getTen());
        TextView ghichu = dialog.findViewById(R.id.popupghichu);
        ghichu.setText(khoanThuChi.getGhiChu());
        Button button = dialog.findViewById(R.id.popupbtnclose);
        Button delete = dialog.findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.khoanThuChiDao.xoaKhoanThuChi(khoanThuChi.getId());
                khoanThuChiList = MainActivity.khoanThuChiDao.getbyDate(begin, end, flag);
                khoanThuChiAdapter.setKhoanThuChiList(khoanThuChiList);
                Toast.makeText(getContext(), getContext().getString(R.string.xoathanhcong), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
