package com.app.quanlychitieu.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.quanlychitieu.Data.KhoanThuChiDao;
import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentReportHm extends Fragment {
    View view;
    PieChart pieChart;
    EditText beginDate, endDate;
    RadioGroup radioGroup;
    List<PieEntry> pieEntries, pieEntrieThu, pieEntriesChi;
    int flag = 0;
    Description description = new Description();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Long begin, end;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_baocao_hangmuc, container, false);
        context = getContext();
        description.setText(context.getString(R.string.thu));
        description.setTextSize(20f);
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {

        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.animateXY(1400, 1400);

        beginDate = view.findViewById(R.id.begindate1);
        endDate = view.findViewById(R.id.enddate1);


        // khi bo forcus lay lai danh sach tim kiem
        beginDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {
                        begin = simpleDateFormat.parse(beginDate.getText().toString()).getTime();
                        pieEntrieThu = MainActivity.khoanThuChiDao.getDataset(begin, end, 0);
                        pieEntriesChi = MainActivity.khoanThuChiDao.getDataset(begin, end, 1);
                        if (flag == 1) pieEntries = pieEntriesChi;
                        else pieEntries = pieEntrieThu;
                        changeDataset();
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
                        pieEntrieThu = MainActivity.khoanThuChiDao.getDataset(begin, end, 0);
                        pieEntriesChi = MainActivity.khoanThuChiDao.getDataset(begin, end, 1);
                        if (flag == 1) pieEntries = pieEntriesChi;
                        else pieEntries = pieEntrieThu;
                        changeDataset();
                        beginDate.clearFocus();
                    }
                } catch (ParseException ex) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.nhapdungtime), Toast.LENGTH_LONG).show();
                }
            }
        });


        // khoi tao ngay thang tim kiem mac dinh
        Calendar calendar = Calendar.getInstance();
        final String b = "1/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        String e = "1/" + (calendar.get(Calendar.MONTH) + 2) + "/" + calendar.get(Calendar.YEAR);

        try {
            this.begin = simpleDateFormat.parse(b).getTime();
            this.end = simpleDateFormat.parse(e).getTime();
        } catch (Exception ex) {
        }

        pieEntriesChi = MainActivity.khoanThuChiDao.getDataset(begin, end, 1);
        pieEntrieThu = MainActivity.khoanThuChiDao.getDataset(begin, end, 0);
        pieEntries = pieEntrieThu;

        beginDate.setText(b);
        endDate.setText(e);
        radioGroup = view.findViewById(R.id.ragroup);

        // loc theo muc thu chi
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radthu:
                        pieEntries = pieEntrieThu;
                        description.setText(context.getString(R.string.thu));
                        pieChart.setDescription(description);
                        flag = 0;
                        break;
                    case R.id.radchi:
                        flag = 1;
                        pieEntries = pieEntriesChi;
                        description.setText(context.getString(R.string.chi));
                        pieChart.setDescription(description);
                        break;
                }
                changeDataset();
            }
        });
        changeDataset();
    }

    public void changeDataset() {
        PieDataSet pieDataSet = new PieDataSet(pieEntries, context.getResources().getString(R.string.khoanmuc));
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.notifyDataSetChanged();
        pieChart.animateXY(1400, 1400);
    }
}
